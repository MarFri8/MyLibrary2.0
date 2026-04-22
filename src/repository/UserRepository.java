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
                     return new Borrower(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"));
                 }
        } catch (SQLException e){
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }
}
