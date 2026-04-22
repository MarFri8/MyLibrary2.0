package controller;

import presentation.Main;
import service.UserService;

import java.util.Scanner;

public class BorrowerController {

    UserService userService = new UserService();
    Scanner scanner = new Scanner(System.in);

    public void showBorrowerMenu(){
        System.out.println("Welcome to the Member Menu " + Main.loggedInUser.getFirstName());
        boolean active = true;
        while(active){
            System.out.println("1. Show All Active Loans");
            String choice = scanner.nextLine().trim();
            switch(choice){
                case "1":{
//                    ArrayList<LoanDTO> loansDtos = memberService.getLoansByMember(presentation.Main.loggedInUser.getId());
//                    for(LoanDTO loanDto : loansDtos){
//                        System.out.println(loanDto.toString());                 }
                }
            }
        }
    }
}
