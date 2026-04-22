package service;

import dto.BorrowerBookDTO;
import dto.LibrarianBookDTO;
import model.Book;
import repository.AuthorRepository;
import repository.BookRepository;

import java.util.ArrayList;

public class BookService {

    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();

    public ArrayList<BorrowerBookDTO> findAvailableBooks(){
        ArrayList<Book> books = bookRepository.findAvailableBooks();
        ArrayList<BorrowerBookDTO> dtos = new ArrayList<>();
        for(Book book : books){
            book.setAuthors(authorRepository.getAuthorsByBookId(book.getId()));
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
            dtos.add(BorrowerBookDTO.toDTO(book));
        }
        return dtos;
    }
}
