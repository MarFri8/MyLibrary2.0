package repository;

import dto.ActiveLoanDTO;
import util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class LoanRepository {

    public ArrayList<ActiveLoanDTO> getOverdueLoans() {
        ArrayList<ActiveLoanDTO> overdueLoans = new ArrayList<>();
        String sql = "SELECT l.id, m.first_name, m.last_name, b.title, l.loan_date, l.due_date " +
                "FROM loans l " +
                "JOIN members m ON l.member_id = m.id " +
                "JOIN books b ON l.book_id = b.id " +
                "WHERE l.return_date IS NULL AND l.due_date < CURDATE()";

        try (Connection conn = util.DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                overdueLoans.add(new ActiveLoanDTO(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("title"),
                        rs.getString("loan_date"),
                        rs.getString("due_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("LoanRepository getOverdueLoans Fel: " + e.getMessage());
        }
        return overdueLoans;
    }

    public ArrayList<ActiveLoanDTO> getAllActiveLoans() {
        String sql = """
                SELECT l.id AS l_id, b.id AS b_id, b.title, l.due_date, m.id AS m_id FROM loans l
                JOIN books b ON l.book_id=b.id
                JOIN members m ON l.member_id=m.id
                WHERE l.return_date IS NULL
                """;
        return executeActiveLoanQuery(sql, -1);
    }
    public ArrayList<ActiveLoanDTO> getMyActiveLoans(int borrowerId) {
        String sql = """
                SELECT l.id AS l_id, b.id AS b_id, b.title, l.due_date, m.id AS m_id FROM loans l
                JOIN books b ON l.book_id=b.id
                JOIN members m ON l.member_id=m.id
                WHERE l.member_id = ? AND l.return_date IS NULL
                """;
        return executeActiveLoanQuery(sql, borrowerId);
    }
    private ArrayList<ActiveLoanDTO> executeActiveLoanQuery(String sql, int borrowerId){
        ArrayList<ActiveLoanDTO> list = new ArrayList<>();
        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            if(borrowerId != -1){
                stmt.setInt(1, borrowerId);
            }

        ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ActiveLoanDTO dto = new ActiveLoanDTO();
                dto.setLoanId(rs.getInt("l_id"));
                dto.setUserId(rs.getInt("m_id"));
                dto.setBookId(rs.getInt("b_id"));
                dto.setTitle(rs.getString("title"));
                dto.setDueDate(rs.getString("due_date"));
                list.add(dto);
            }
        }catch(SQLException e){
            System.out.println("LoanRepository executeActiveLoanQuery Fel: " + e.getMessage());
        }
        return list;
    }

    public int loansOverdue(int borrowerId){
        String sql = "SELECT COUNT(*) FROM loans WHERE member_id = ? AND return_date IS NULL AND due_date < CURDATE()";

        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, borrowerId);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        }catch (SQLException e){
            System.out.println("LoanRepository loanOverdue Fel: " + e.getMessage());
        }
        return 0;
    }

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
            System.out.println("LoanRepository createLoan Fel: " + e.getMessage());
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

    public void extendLoan(int loanId){
        String sql = "UPDATE loans SET due_date = DATE_ADD(due_date, INTERVAL 3 Day) WHERE id = ?";

        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, loanId);
            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Loan (ID: " + loanId + ") Extended by 3 Days");
            }else{
                System.out.println("Loan (ID: " + loanId + ") Not Found");
            }
        } catch(SQLException e){
            System.out.println("LoanRepositroy extendLoan Fel: " + e.getMessage());
        }
    }

}
