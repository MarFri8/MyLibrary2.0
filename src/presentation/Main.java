package presentation;

import controller.*;
import model.*;
import repository.UserRepository;

import java.util.*;

public class Main {
    public static User loggedInUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BorrowerController borrowerController = new BorrowerController();
        LibrarianController librarianController = new LibrarianController();
        UserRepository userRepository = new UserRepository();

        while (true) {
            if (loggedInUser == null) {
                System.out.println("1. Login");
                System.out.println("0. Exit Program");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    System.out.println("Enter Your Email:");
                    User user = userRepository.getUserByEmail(scanner.nextLine().trim());
                    System.out.println("Enter Password:");
                    String password = scanner.nextLine().trim();

                    if (user != null && password.equals(user.getPassword())) {
                        loggedInUser = user;
                        System.out.println(loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + " Has Logged in!");
                    } else {
                        System.out.println("Invalid Email or Password.");
                    }
                } else if (choice == 0) {
                    System.out.println("Exiting Program...");
                    System.exit(0);
                }
            } else {
                boolean active = true;
                while (active) {
                    System.out.println("\nWelcome to the Library\n");
                    System.out.println("1. Open Member Menu");
                    if (loggedInUser instanceof Librarian) {
                        System.out.println("2. Open Librarian Menu");
                    }
                    System.out.println("0. Logout");

                    int select = scanner.nextInt();
                    scanner.nextLine();

                    switch (select) {
                        case 1 -> borrowerController.showBorrowerMenu();
                        case 2 -> {
                            if (loggedInUser instanceof Librarian) {
                                librarianController.showLibrarianMenu();
                            } else {
                                System.out.println("Access Denied: Librarian Only");
                            }
                        }
                        case 0 -> {
                            active = false;
                            loggedInUser = null;
                            System.out.println("Logged out successfully.");
                        }
                    }
                }
            }
        }
    }
}