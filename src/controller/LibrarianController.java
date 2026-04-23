package controller;

import dto.ActiveLoanDTO;
import service.BookService;
import service.LoanService;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class LibrarianController {

    private UserService userService = new UserService();
    private LoanService loanService = new LoanService();
    private BookService bookService = new BookService();
    private  Scanner scanner = new Scanner(System.in);

    private void createBorrower() {
        System.out.println("--- Register New Member ---");
        System.out.print("First Name: "); String fName = scanner.next();
        System.out.print("Last Name: "); String lName = scanner.next();
        System.out.print("Email: "); String email = scanner.next();
        System.out.print("Password: "); String pwd = scanner.next();

        userService.registerNewBorrower(fName, lName, email, pwd);
    }

    private void viewAllLoans(){
        ArrayList<ActiveLoanDTO> loans = loanService.fetchAllActiveLoans();
        if(loans.isEmpty()){
            System.out.println("There Are No Active Loans");
        }
        else{
            loans.forEach(System.out::println);
        }
    }

    private void addNewBook() {
        System.out.println("--- Add New Book ---");
        System.out.print("Title: "); String title = scanner.next();
        System.out.print("ISBN: "); String isbn = scanner.next();
        System.out.print("Year: "); int year = scanner.nextInt();
        System.out.print("Total Copies: "); int copies = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Summary: ");String summary = scanner.nextLine();
        System.out.print("Language: "); String lang = scanner.next();
        System.out.print("Page Count: "); int pages = scanner.nextInt();
        System.out.print("Author ID: "); int authId = scanner.nextInt();
        System.out.print("Category ID: "); int catId = scanner.nextInt();

        bookService.createNewBook(title, isbn, year, copies, summary, lang, pages, authId, catId);
    }

    public void showLibrarianMenu(){

        boolean active = true;

        while(active){
            System.out.println("--- Librarian Menu ---");
            System.out.println("1. Create New Borrower Account");
            System.out.println("2. View All Active Loans");
            System.out.println("3. Add New Book");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            switch(choice){
                case 1 -> createBorrower();
                case 2 -> viewAllLoans();
                case 3 -> addNewBook();
                case 0 -> active = false;
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}
