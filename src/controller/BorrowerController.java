package controller;

import dto.ActiveLoanDTO;
import dto.BorrowerDTO;
import presentation.Main;
import service.LoanService;
import service.UserService;

import java.util.ArrayList;
import java.util.Scanner;

public class BorrowerController {

    UserService userService = new UserService();
    LoanService loanService = new LoanService();
    Scanner scanner = new Scanner(System.in);

    public void showBorrowerMenu(){

        boolean active = true;
        while(active){
            System.out.println("--- Member Menu ---");
            System.out.println("Welcome to the Member Menu " + Main.loggedInUser.getFirstName());
            System.out.println("1. Show Current Loans");
            System.out.println("2. View Profile");
            System.out.println("3. Update Profile");
            System.out.println("0. Back");
            int select = scanner.nextInt();
            switch(select){
                case 1:
                    System.out.println("--- Current Active Loans ---");
                    ArrayList<ActiveLoanDTO> activeLoans = loanService.getAllActiveLoans();
                    if(activeLoans.isEmpty()){
                        System.out.println("No Active Loans Found");
                    }else{
                        for(ActiveLoanDTO loan : activeLoans){
                            System.out.println(loan.toString());
                        }
                    }
                    break;
                case 2: {
                    System.out.println("--- PROFILE ---");
                    BorrowerDTO profile = userService.getBorrowerProfile(Main.loggedInUser.getId());
                    if (profile != null) {
                        System.out.println(profile.toString());
                    } else {
                        System.out.println("Profile Not Found");
                    }
                    break;
                }
                case 3: {
                    System.out.println("--- Update Profile ---");
                    System.out.println("1. Update First Name");
                    System.out.println("2. Update Last Name");
                    System.out.println("3. Update Email");
                    System.out.println("4. Update Password");
                    System.out.print("Choose an Option: ");
                    String fieldChoice = scanner.next();

                    System.out.print("Enter the new value: ");
                    String newValue = scanner.next();

                    int currentId = presentation.Main.loggedInUser.getId();
                    boolean success = userService.updateProfileField(currentId, fieldChoice, newValue);

                    if (success) {
                        System.out.println("Update successful");
                        if (fieldChoice.equals("1")) Main.loggedInUser.setFirstName(newValue);
                        if (fieldChoice.equals("2")) Main.loggedInUser.setLastName(newValue);
                    } else {
                        System.out.println("Update Failed. Please Check Your Input");
                    }
                    break;
                }
                case 0:
                    active = false;
                    break;
            }
        }
    }
}
