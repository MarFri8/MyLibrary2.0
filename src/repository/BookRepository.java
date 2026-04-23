package repository;

import model.Author;
import model.Book;
import model.Category;
import util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class BookRepository {

    public LinkedHashMap<String, Integer> getMostLoanedBooks(){
        LinkedHashMap<String, Integer> stats = new LinkedHashMap<>();
        String sql = """
                     SELECT b.title, COUNT(l.book_id) AS loan_count FROM books b
                     JOIN loans l ON b.id=l.book_id
                     GROUP BY b.id
                     ORDER BY loan_count DESC
                     LIMIT 5
                     """;
        try(Connection conn = util.DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                stats.put(rs.getString("title"), rs.getInt("loan_count"));
            }
        }catch(SQLException e){
            System.out.println("BookRepository getMostLoanedBooks Fel: " + e.getMessage());
        }
        return stats;
    }

    public void addBook(String title, String isbn, int year, int totalCopies, String summary, String language, int pages, int authorId, int categoryId){
        String insertBook = "INSERT INTO books (title, isbn, year_published, total_copies, available_copies) VALUES (?, ?, ?, ?, ?)";
        String insertDesc = """
                            INSERT INTO book_descriptions (book_id, summary, language, page_count)
                            SELECT ?, ?, ?, ? FROM DUAL
                            WHERE NOT EXISTS (SELECT 1 FROM book_descriptions WHERE book_id = ?)
                            """;
        String insertAuthor = "INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)";
        String insertCategory = "INSERT INTO book_categories (book_id, category_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.connect()) {
            conn.setAutoCommit(false);

            try(PreparedStatement psBook = conn.prepareStatement(insertBook, Statement.RETURN_GENERATED_KEYS)){
                psBook.setString(1, title);
                psBook.setString(2, isbn);
                psBook.setInt(3, year);
                psBook.setInt(4, totalCopies);
                psBook.setInt(5, totalCopies);
                psBook.executeUpdate();

                ResultSet rs = psBook.getGeneratedKeys();
                if(rs.next()){
                    int newBookId = rs.getInt(1);

                    try (PreparedStatement psDesc = conn.prepareStatement(insertDesc)) {
                        psDesc.setInt(1, newBookId);
                        psDesc.setString(2, summary);
                        psDesc.setString(3, language);
                        psDesc.setInt(4, pages);
                        psDesc.setInt(5, newBookId);

                        int rowsAffected = psDesc.executeUpdate();

                        if (rowsAffected == 0){
                            System.out.println("Description Already Existed For Book ID " + newBookId);
                        }else{
                            System.out.println("Description Added Successfully");
                        }
                    }

                    try (PreparedStatement psAuth = conn.prepareStatement(insertAuthor)) {
                        psAuth.setInt(1, newBookId);
                        psAuth.setInt(2, authorId);
                        psAuth.executeUpdate();
                    }

                    try (PreparedStatement psCat = conn.prepareStatement(insertCategory)) {
                        psCat.setInt(1, newBookId);
                        psCat.setInt(2, categoryId);
                        psCat.executeUpdate();
                    }
                }
                conn.commit();
                System.out.println("Book and All Related Data Added Successfully");
            }catch(SQLException e){
                conn.rollback();
                throw e;
            }
        }catch(SQLException e){
            System.out.println("BookRepository addBook Fel: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId){

        String deleteDesc = "DELETE FROM book_descriptions WHERE book_id = ?";
        String deleteAuthors = "DELETE FROM book_authors WHERE book_id = ?";
        String deleteCats = "DELETE FROM book_categories WHERE book_id = ?";
        String deleteBook = "DELETE FROM books WHERE id = ?";

        try (Connection conn = util.DatabaseConnection.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psDesc = conn.prepareStatement(deleteDesc);
                 PreparedStatement psAuth = conn.prepareStatement(deleteAuthors);
                 PreparedStatement psCat = conn.prepareStatement(deleteCats);
                 PreparedStatement psBook = conn.prepareStatement(deleteBook)) {

                psDesc.setInt(1, bookId); psDesc.executeUpdate();
                psAuth.setInt(1, bookId); psAuth.executeUpdate();
                psCat.setInt(1, bookId); psCat.executeUpdate();
                psBook.setInt(1, bookId); psBook.executeUpdate();

                conn.commit();
                System.out.println("Book " + bookId + " and All Its Data Has Been Deleted");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("BookRepository deleteBook Fel: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("BookRepository deleteBook DB Fel: " + e.getMessage());
        }
    }

    public void updateBook(int bookId, String title, String isbn, int year, int copies, String summary, String lang, int pages) {
        String updateBook = "UPDATE books SET title = ?, isbn = ?, year_published = ?, total_copies = ? WHERE id = ?";
        String updateDesc = "INSERT INTO book_descriptions (book_id, summary, language, page_count) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE summary = VALUES(summary), language = VALUES(language), page_count = VALUES(page_count)";

        try (Connection conn = util.DatabaseConnection.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psBook = conn.prepareStatement(updateBook);
                 PreparedStatement psDesc = conn.prepareStatement(updateDesc)) {

                psBook.setString(1, title);
                psBook.setString(2, isbn);
                psBook.setInt(3, year);
                psBook.setInt(4, copies);
                psBook.setInt(5, bookId);
                psBook.executeUpdate();

                psDesc.setInt(1, bookId);
                psDesc.setString(2, summary);
                psDesc.setString(3, lang);
                psDesc.setInt(4, pages);
                psDesc.executeUpdate();

                conn.commit();
                System.out.println("Book ID " + bookId + " Updated Successfully");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("BookRepository updateBookDetails Fel: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("BookRepository updateBookDetails DB Fel: " + e.getMessage());
        }
    }

    public ArrayList<Book> findAvailableBooks(){
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, bd.* FROM books b JOIN book_descriptions bd ON b.id=bd.book_id WHERE available_copies > 0";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                String isbn = rs.getString("isbn");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                int totalCopies = rs.getInt("total_copies");
                String summary = rs.getString("summary");
                String language = rs.getString("language");
                int pageCount = rs.getInt("page_count");
                Book book = new Book(bookId, title, isbn, yearPublished, availableCopies, totalCopies, summary, language, pageCount);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("BookRepository findAvailableBooks Fel: " + e.getMessage());
        }
        return books;
    }
    public ArrayList<Book> searchBooks(String searchTerm) throws SQLException{

        Map<Integer, Book> bookMap = new LinkedHashMap<>();

        String sql = """
                SELECT b.id AS b_id, b.title, b.year_published, b.available_copies, a.first_name, a.last_name, bd.summary, bd.language, bd.page_count, c.id AS category_id, c.name FROM books b
                JOIN book_categories bc ON b.id=bc.book_id
                JOIN categories c ON c.id=bc.category_id
                JOIN book_descriptions bd ON b.id=bd.book_id
                JOIN book_authors ba ON b.id=ba.book_id
                JOIN authors a ON a.id=ba.author_id
                WHERE title LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR name LIKE ?
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String wildcardSearch = "%" + searchTerm + "%";

            for(int i = 1; i <= 4; i++){
                stmt.setString(i, wildcardSearch);
            }

            try(ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int bookId = rs.getInt("b_id");
                    Book book = bookMap.get(bookId);
                    if(book == null) {
                        String title = rs.getString("title");
                        int yearPublished = rs.getInt("year_published");
                        int availableCopies = rs.getInt("available_copies");
                        String summary = rs.getString("summary");
                        String language = rs.getString("language");
                        int pageCount = rs.getInt("page_count");


                        book = new Book(title, yearPublished, availableCopies, summary, language, pageCount, new ArrayList<>());
                        book.setId(bookId);

                        bookMap.put(bookId, book);
                    }
                    book.getCategories().add(new Category(rs.getInt("category_id"), rs.getString("name"), ""));
                }
            }
        } catch (SQLException e) {
            System.out.println("BookRepository searchBooks Fel: " + e.getMessage());
        }
        return new ArrayList<>(bookMap.values());
    }
}
