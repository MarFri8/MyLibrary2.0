package dto;

public class ActiveLoanDTO {

    private int loanId;
    private int bookId;
    private String title;
    private String dueDate;
    private String loanDate;
    private int userId;
    private String firstName;
    private String lastName;

    public ActiveLoanDTO(){
    }

    /*public static BorrowerDTO toDTO(Book book, Loan loan, Borrower borrower){
        BorrowerDTO borrowerDTO = new BorrowerDTO();
        borrowerDTO.setLoanId(loan.getLoanId());
        borrowerDTO.setBookId(book.getId());
        borrowerDTO.setTitle(book.getTitle());
        borrowerDTO.setFirstName(borrower.getFirstName());
        borrowerDTO.setLastName(borrower.getLastName());
        borrowerDTO.setEmail(borrower.getEmail());
        borrowerDTO.setDueDate(loan.getDueDate());
        borrowerDTO.setPassword(borrower.getPassword());
        return borrowerDTO;
    }*/

    public ActiveLoanDTO(int loanId, String firstName, String lastName, String title, String loanDate, String dueDate){
        this.loanId = loanId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) { this.loanId = loanId; }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLoanDate() { return loanDate; }

    public void setLoanDate(String loanDate) { this.loanDate = loanDate; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String toString() {
        return "Loan ID: " + loanId +
                "\nUser ID: " + userId +
                "\nBook ID: " + bookId +
                "\nBook: " + title +
                "\nDue: " + dueDate +
                "\n____________________________________\n";

    }
}
