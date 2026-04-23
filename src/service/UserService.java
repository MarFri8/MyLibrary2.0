package service;

import dto.BorrowerDTO;
import model.Borrower;
import repository.UserRepository;

public class UserService {

    UserRepository userRepository = new UserRepository();

    public BorrowerDTO getBorrowerProfile(int id){
        Borrower borrower = userRepository.getBorrowerById(id);
        if(borrower != null){
            return new BorrowerDTO(
                    borrower.getId(),
                    borrower.getFirstName(),
                    borrower.getLastName(),
                    borrower.getEmail(),
                    borrower.getPassword()
            );
        }
        return null;
    }

    public boolean updateProfileField(int id, String field, String value){
        String column = switch(field){
            case "1" -> "first_name";
            case "2" -> "last_name";
            case "3" -> "email";
            case "4" -> "password";
            default -> null;
        };
        if(column != null){
            return userRepository.updateSingleField(id, column, value);
        }
        return false;
    }

    public void registerNewBorrower(String fName, String lName, String email, String password) {
        Borrower newBorrower = new Borrower(0, fName, lName, email, password);
        userRepository.createBorrower(newBorrower);
    }
}
