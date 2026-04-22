package repository;

import model.Author;
import model.Book;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class BookRepository {
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
            System.out.println("BookRepository findAvaiableBooks Fel: " + e.getMessage());
        }
        return books;
    }
    public ArrayList<Book> searchBooks(String searchTerm) throws SQLException{

        ArrayList<Book> books = new ArrayList<>();

        String sql = """
                SELECT b.title, b.year_published, b.available_copies, a.first_name, a.last_name, bd.summary, bd.language, bd.page_count FROM books b
                JOIN book_descriptions bd ON b.id=bd.book_id
                JOIN book_authors ba ON b.id=ba.book_id
                JOIN authors a ON a.id=ba.author_id
                WHERE title LIKE ? OR first_name LIKE ? OR last_name LIKE ?
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String wildcardSearch = "%" + searchTerm + "%";

            for(int i = 1; i <= 3; i++){
                pstmt.setString(i, wildcardSearch);
            }

            try(ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String title = rs.getString("title");
                    int yearPublished = rs.getInt("year_published");
                    int availableCopies = rs.getInt("available_copies");
                    String summary = rs.getString("summary");
                    String language = rs.getString("language");
                    int pageCount = rs.getInt("page_count");

                    Book book = new Book(title, yearPublished, availableCopies, summary, language, pageCount);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.out.println("BookRepository searchBooks Fel: " + e.getMessage());
        }
        return books;
    }
}
