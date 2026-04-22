package controller;
import model.Loan;
import service.BookService;
import service.LoanService;
import dto.BorrowerBookDTO;

import java.util.ArrayList;
import java.util.Scanner;

public class LoanController {

    LoanService loanService = new LoanService();
    BookService bookService = new BookService();
    Scanner scanner = new Scanner(System.in);

    public void showLoanMenu(){


        boolean active = true;

        while(active){
            System.out.println("------Loan Menu--------");
            System.out.println("1. Return Loan");
            System.out.println("2. Loan a book");
            System.out.println("0. Back");
            int choice = scanner.nextInt();
            switch(choice){
                case 1: {
                    System.out.println("Enter the Loan ID:");
                    int loanId = scanner.nextInt();
                    System.out.println("Enter the Book ID:");
                    int bookId = scanner.nextInt();
                    loanService.returnLoan(loanId, bookId);
                    System.out.println("Return Processed Successfully.");
                    break;
                }
                case 2: {
                    System.out.println("--- Loan a Book ---");
                    System.out.println("Enter Book ID:");
                    int bookId = scanner.nextInt();
                    BorrowerBookDTO selectedBook = bookService.findAvailableBooks().stream()
                            .filter(b -> b.getBookId() == bookId)
                            .findFirst()
                            .orElse(null);
                    if(selectedBook != null){
                        System.out.println("You Have Selected: " + selectedBook.getTitle());
                    } else{
                        System.out.println("Book with ID " + bookId + " not found.");
                    }
                    System.out.println("Enter Borrower (Member) ID:");
                    int borrowerId = scanner.nextInt();
                    loanService.startNewLoan(bookId, borrowerId);
                    break;
                }
                case 0:
                    active = false;
                    break;
            }
        }

    }

}