package repository;

import model.Loan;
import util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class LoanRepository {

    public void createLoan(int bookId, int borrowerId){

        String sqlInsertLoan = "INSERT INTO loans (book_id, member_id, loan_date, due_date) VALUES (?, ?, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY))";
        String sqlUpdateBook = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0";

        try(Connection conn = DatabaseConnection.connect()){
            conn.setAutoCommit(false);

            try(PreparedStatement stmt1 = conn.prepareStatement(sqlInsertLoan);
            PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateBook)){

                stmt1.setInt(1, bookId);
                stmt1.setInt(2, borrowerId);
                stmt1.executeUpdate();

                stmt2.setInt(1, bookId);
                int rowsAffected = stmt2.executeUpdate();

                if(rowsAffected == 0){
                    throw new SQLException("No copies avaiable to loan.");
                }

                conn.commit();
                System.out.println("Loan created successfully");
            } catch (SQLException e){
                conn.rollback();
                throw e;
            }
        } catch(SQLException e){
            System.out.println("Error creating loan: " + e.getMessage());
        }
    }

    public void returnLoan(int loanId, int bookId) {

        try (Connection conn = DatabaseConnection.connect()){
             String sql1 = "UPDATE loans SET return_date=CURRENT_DATE WHERE id=?";
             String sql2 = "UPDATE books SET available_copies=available_copies+1 WHERE id=?";

             PreparedStatement stmt1 = conn.prepareStatement(sql1);
             stmt1.setInt(1, loanId);

             PreparedStatement stmt2 = conn.prepareStatement(sql2);
             stmt2.setInt(1, bookId);

             int loanRowsAffected = stmt1.executeUpdate();
             int bookRowsAffected = stmt2.executeUpdate();

             System.out.println("Loans updated: " + loanRowsAffected + " Books updated: " + bookRowsAffected);
        } catch (SQLException e) {
            System.out.println("LoanRepository returnLoan Fel: " + e.getMessage());
        }
    }

}
