package controller;

import dto.BorrowerBookDTO;
import dto.LibrarianBookDTO;
import service.BookService;

import java.util.*;

public class BookController {

    BookService bookService = new BookService();
    Scanner scanner = new Scanner(System.in);

    public void showBookMenu(){
        boolean active = true;

        while(active){
            System.out.println("------Book menu--------");
            System.out.println("1. Show all available books");
            System.out.println("2. Search for book");
            System.out.println("0. Back");
            int select = scanner.nextInt();
            switch(select){
                case 1:
                    ArrayList<BorrowerBookDTO> books = bookService.findAvailableBooks();
                    for(BorrowerBookDTO b : books){
                        System.out.println(b.toString());
                    }
                    break;
                case 2:
                    System.out.println("Enter serch term (title or author):");
                    scanner.nextLine();
                    String searchTerm = scanner.nextLine();
                    ArrayList<BorrowerBookDTO> searchResults = bookService.searchBooks(searchTerm);
                    if(searchResults.isEmpty()){
                        System.out.println("No books matching '" + searchTerm + "' found.");
                    } else{
                        System.out.println("--- Search Results ---");
                        for(BorrowerBookDTO b : searchResults){
                            System.out.println(b.toString());
                        }
                    }
                case 0:
                    active = false;
                    break;
            }
        }
    }
}
