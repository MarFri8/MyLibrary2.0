import controller.BookController;
import model.Book;
import model.User;
import repository.UserRepository;

import java.util.*;

public class Main {

    public static User loggedInUser = null;

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        BookController bookController = new BookController();
        UserRepository userRepository = new UserRepository();

      /*  System.out.println("Enter your email:");
        User user = userRepository.getUserByEmail(scanner.nextLine().trim());

        System.out.println("Enter password:");
        String password = scanner.nextLine().trim();

        if(password.equals(user.getPassword())){
            loggedInUser = user;
        }

        System.out.println(loggedInUser.getFirstName() + " " +  loggedInUser.getLastName() + " has logged in!");
*/
        boolean active = true;

        while(active) {
            System.out.println("Welcome to the library");
            System.out.println("1. Book Menu");
            System.out.println("0. Exit");
            int select = scanner.nextInt();
            switch(select){
                case 1:
                bookController.showBookMenu();
                break;
                case 0:
                    active = false;
                    break;
            }
        }
    }
}