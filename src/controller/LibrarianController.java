package controller;

import dto.ActiveLoanDTO;
import service.*;

import java.util.ArrayList;
import java.util.Scanner;

public class LibrarianController {

    private UserService userService = new UserService();
    private LoanService loanService = new LoanService();
    private BookService bookService = new BookService();
    private AuthorService authorService = new AuthorService();
    private CategoryService categoryService = new CategoryService();
    private Scanner scanner = new Scanner(System.in);

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

    public void deleteBook(){
        System.out.println("--- The Book Dump ---");
        System.out.println("Enter the ID of the Book You Want to Remove: ");
        int bookId = scanner.nextInt();

        System.out.println("Are You Sure You Want to Delete Book ID " + bookId + "? (y/n): ");
        String confirm = scanner.next();

        if(confirm.equalsIgnoreCase("y")){
            bookService.removeBook(bookId);
        }else {
            System.out.println("Removal Process Aborted");
        }
    }

    private void editBook() {
        System.out.println("--- Edit Book ---");
        System.out.print("Enter the ID of the Book to Edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("New Title: "); String title = scanner.nextLine();
        System.out.print("New ISBN: "); String isbn = scanner.nextLine();
        System.out.print("New Year: "); int year = scanner.nextInt();
        System.out.print("New Total Copies: "); int copies = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New Summary: "); String summary = scanner.nextLine();
        System.out.print("New Language: "); String lang = scanner.nextLine();
        System.out.print("New Page Count: "); int pages = scanner.nextInt();

        bookService.editBook(id, title, isbn, year, copies, summary, lang, pages);
    }

    private void addNewAuthor(){
        System.out.println("--- Add New Author ---");
        System.out.println("First Name: "); String fName = scanner.nextLine().trim();
        System.out.println("Last Name: "); String lName = scanner.nextLine().trim();
        System.out.println("Nationality: "); String nationality = scanner.nextLine().trim();
        System.out.println("Birth Date (YYYY-MM-DD): "); String birthDate = scanner.nextLine().trim();

        if(!fName.isEmpty() && !lName.isEmpty() && !nationality.isEmpty() && !birthDate.isEmpty()){
            authorService.createAuthor(fName, lName, nationality, birthDate);
        }else{
            System.out.println("Fill in All The Required Fields");
        }
    }

    private void editAuthor(){
        System.out.println("--- Edit Author ---");
        System.out.print("Enter Author ID to Edit: "); int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("New First Name: "); String firstName = scanner.nextLine().trim();
        System.out.print("New Last Name: "); String lastName = scanner.nextLine().trim();
        System.out.print("New Nationality: "); String nationality = scanner.nextLine().trim();
        System.out.print("New Birth Date (YYYY-MM-DD): "); String birthDate = scanner.nextLine().trim();

        if(!firstName.isEmpty() && !lastName.isEmpty() && !nationality.isEmpty() && !birthDate.isEmpty()){
            authorService.editAuthor(id, firstName, lastName, nationality, birthDate);
        }else{
            System.out.println("Fill in All The Required Fields");
        }
    }

    private void assignCategoryToBook(){
        System.out.println("Enter Book ID: "); int bookId = scanner.nextInt();
        System.out.println("Enter Category ID: "); int catId = scanner.nextInt();
        scanner.nextLine();

        categoryService.addBookToCategory(bookId, catId);
    }

    private void viewOverdueLoans() {
        System.out.println("--- Overdue Loans ---");
        ArrayList<ActiveLoanDTO> overdue = loanService.fetchOverdueLoans();

        if (overdue.isEmpty()) {
            System.out.println("No Overdue Loans Found.");
        } else {
            for (ActiveLoanDTO loan : overdue) {
                System.out.println("LOAN ID: " + loan.getLoanId() +
                        "\nBorrower: " + loan.getFirstName() + " " + loan.getLastName() +
                        "\nBook: " + loan.getTitle() +
                        "\nWas Due: " + loan.getDueDate());
            }
        }
    }

    public void showLibrarianMenu(){

        boolean active = true;

        while(active){
            System.out.println("--- Librarian Menu ---");
            System.out.println("1. View All Active Loans");
            System.out.println("2. View Overdue Loans");
            System.out.println("3. Create New Borrower Account");
            System.out.println("4. Add New Author");
            System.out.println("5. Edit Author");
            System.out.println("6. Add New Book");
            System.out.println("7. Edit Book");
            System.out.println("8. Assign Category To Book");
            System.out.println("9. Delete A Book");
            System.out.println("10. View Popular Book Stats");
            System.out.println("0. Back");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1 -> viewAllLoans();
                case 2 -> viewOverdueLoans();
                case 3 -> createBorrower();
                case 4 -> addNewAuthor();
                case 5 -> editAuthor();
                case 6 -> addNewBook();
                case 7 -> editBook();
                case 8 -> assignCategoryToBook();
                case 9 -> deleteBook();
                case 10 -> bookService.fetchMostLoanedBooks();
                case 0 -> active = false;
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}
