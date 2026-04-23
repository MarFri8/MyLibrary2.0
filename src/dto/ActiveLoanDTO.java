package dto;

public class ActiveLoanDTO {

    private int loanId;
    private int bookId;
    private String title;
    private String dueDate;

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

    public ActiveLoanDTO(int loanId, int bookId, String title, String firstName, String lastName, String email, String dueDate, String password){
        this.loanId = loanId;
        this.bookId = bookId;
        this.title = title;
        this.dueDate = dueDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) { this.loanId = loanId; }

    public int getBookId() { return bookId; }

    public void setBookId(int bookId) { this.bookId = bookId; }

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

    @Override
    public String toString() {
        return "Loan ID: " + loanId +
                "\nBook ID: " + bookId +
                "\nBook: " + title +
                "\nDue: " + dueDate +
                "\n____________________________________\n";

    }
}
