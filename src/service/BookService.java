package service;

import dto.BorrowerBookDTO;
import dto.LibrarianBookDTO;
import model.Book;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.CategoryRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BookService {

    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    CategoryRepository categoryRepository = new CategoryRepository();

    public void fetchMostLoanedBooks() {
        LinkedHashMap<String, Integer> stats = bookRepository.getMostLoanedBooks();
        if (stats.isEmpty()) {
            System.out.println("No Loan Data Available Yet");
            return;
        }
        System.out.println("--- Most Popular Books ---");
        stats.forEach((title, count) ->
                System.out.println(count + " Loans: " + title));
    }

    public void editBook(int id, String title, String isbn, int year, int copies, String summary, String lang, int pages) {
        bookRepository.updateBook(id, title, isbn, year, copies, summary, lang, pages);
    }

    public void removeBook(int bookId){
        bookRepository.deleteBook(bookId);
    }

    public void createNewBook(String title, String isbn, int year, int copies, String summary, String lang, int pages, int authorId, int catId) {
        bookRepository.addBook(title, isbn, year, copies, summary, lang, pages, authorId, catId);
    }

    public ArrayList<BorrowerBookDTO> findAvailableBooks(){
        ArrayList<Book> books = bookRepository.findAvailableBooks();
        ArrayList<BorrowerBookDTO> dtos = new ArrayList<>();
        for(Book book : books){
            book.setAuthors(authorRepository.getAuthorsByBookId(book.getId()));
            book.setCategories(categoryRepository.getCategoriesByBookId(book.getId()));
            dtos.add(BorrowerBookDTO.toDTO(book));
        }
        return dtos;
    }
    public ArrayList<BorrowerBookDTO> searchBooks(String searchTerm){
        ArrayList<Book> books = new ArrayList<>();

        try{
            books = bookRepository.searchBooks(searchTerm);
        } catch(Exception e){
            System.out.println("BookService Fel: " +e.getMessage());
        }
        ArrayList<BorrowerBookDTO> dtos = new ArrayList<>();

        for(Book book : books){
            book.setAuthors(authorRepository.getAuthorsByBookId(book.getId()));
            book.setCategories(categoryRepository.getCategoriesByBookId(book.getId()));
            dtos.add(BorrowerBookDTO.toDTO(book));
        }
        return dtos;
    }
}
