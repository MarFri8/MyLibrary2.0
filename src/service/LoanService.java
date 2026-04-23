package service;

import dto.ActiveLoanDTO;
import repository.LoanRepository;

import java.util.ArrayList;

public class LoanService {
    LoanRepository loanRepository = new LoanRepository();

    public ArrayList<ActiveLoanDTO> getAllActiveLoans(){
        return loanRepository.getActiveLoans();
    }

    public void startNewLoan(int bookId, int borrowerId){
        loanRepository.createLoan(bookId, borrowerId);
    }

    public void returnLoan(int loanId, int bookId) {
        loanRepository.returnLoan(loanId, bookId);
    }

    public void extendLoanDate(int loanId){
        loanRepository.extendLoan((loanId));
    }
}
