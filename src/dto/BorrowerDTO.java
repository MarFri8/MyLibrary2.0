package dto;

public class BorrowerDTO {

    private int borrowerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public BorrowerDTO(int borrowerId, String firstName, String lastName, String email, String password){
        this.borrowerId = borrowerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public BorrowerDTO(){

    }

    public int getBorrowerId() { return borrowerId; }

    public void setBorrowerId(int borrowerId) { this.borrowerId = borrowerId; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Member ID: " + borrowerId +
                "\nName: " + firstName + " " + lastName +
                "\nEmail: " + email +
                "\nPassword: " + password +"\n";

    }
}
