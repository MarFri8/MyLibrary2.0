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
            System.out.println("------Book Menu--------");
            System.out.println("1. Show All Available Books");
            System.out.println("2. Search for Book");
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
                    System.out.println("Enter Search Term (Title, Author or Category):");
                    scanner.nextLine();
                    String searchTerm = scanner.nextLine();
                    ArrayList<BorrowerBookDTO> searchResults = bookService.searchBooks(searchTerm);
                    if(searchResults.isEmpty()){
                        System.out.println("No Books Matching '" + searchTerm + "' Found.");
                    } else{
                        System.out.println("--- Search Results ---");
                        for(BorrowerBookDTO b : searchResults){
                            System.out.println(b.toString());
                        }
                    }
                    break;
                case 0:
                    active = false;
                    break;
            }
        }
    }
}
