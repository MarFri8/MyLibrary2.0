package service;

import dto.ActiveLoanDTO;
import repository.LoanRepository;

import java.util.ArrayList;

public class LoanService {
    LoanRepository loanRepository = new LoanRepository();

    public ArrayList<ActiveLoanDTO> fetchAllActiveLoans(){
        return loanRepository.getAllActiveLoans();
    }

    public ArrayList<ActiveLoanDTO> fetchMyActiveLoans(int borrowerId){
        return loanRepository.getMyActiveLoans(borrowerId);
    }

    public ArrayList<ActiveLoanDTO> fetchOverdueLoans() {
        return loanRepository.getOverdueLoans();
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

    public void checkUserNotifications(int borrowerId){
        int overdueCount = loanRepository.loansOverdue(borrowerId);
        if(overdueCount > 0){
            System.out.println("\n*** NOTIFICATION: You Have " + overdueCount + " book(s) past their due date\nReturn Them to Avoid Fines ***");
        }
    }
}
