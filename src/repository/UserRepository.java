package repository;

import model.Borrower;
import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {

    public User getUserByEmail(String email){
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE email=?")){
                 stmt.setString(1, email);

                 ResultSet rs = stmt.executeQuery();

                 if(rs.next()){
                     return new Borrower(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getString("membership_type"));
                 }
        } catch (SQLException e){
            System.out.println("UserRepository getUserByEmail Fel: " + e.getMessage());
        }
        return null;
    }

    public Borrower getBorrowerById(int id){
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE id=?")){
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new Borrower(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getString("membership_type"));
            }
        } catch (SQLException e){
            System.out.println("UserRepository getBorrowerById Fel: " + e.getMessage());
        }
        return null;
    }

    public boolean updateSingleField(int borrowerId, String column, String newValue){
        String sql = "UPDATE members SET " + column + " = ? WHERE id = ?";

        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, newValue);
            stmt.setInt(2, borrowerId);

            return stmt.executeUpdate() > 0;
        }catch(SQLException e){
            System.out.println("UserRepository updateSingleField Fel: " + e.getMessage());
            return false;
        }
    }

}
