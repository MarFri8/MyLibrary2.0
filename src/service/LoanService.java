package service;

import model.Loan;
import repository.LoanRepository;

public class LoanService {
    LoanRepository loanRepository = new LoanRepository();

    public void startNewLoan(int bookId, int borrowerId){
        loanRepository.createLoan(bookId, borrowerId);
    }

    public void returnLoan(int loanId, int bookId) {
        loanRepository.returnLoan(loanId, bookId);
    }
}
