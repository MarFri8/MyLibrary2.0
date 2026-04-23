package repository;

import model.Author;
import util.DatabaseConnection;

import java.util.*;
import java.sql.*;

public class AuthorRepository {

    public void addAuthor(String firstName, String lastName, String nationality, String birthDate){
        String sql = "INSERT INTO authors (first_name, last_name, nationality, birth_date) VALUES (?, ?, ?, ?)";

        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, nationality);
            stmt.setString(4, birthDate);

            stmt.executeUpdate();
            System.out.println("Author Added to Database Successfully");
        }catch(SQLException e){
            System.out.println("AuthorRepository addAuthor Fel: " + e.getMessage());
        }
    }

    public void updateAuthor(int authorId, String firstName, String lastName, String nationality, String birthDate){
        String sql = "UPDATE authors SET first_name = ?, last_name = ?, nationality = ?, birth_date = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, nationality);
            stmt.setString(4, birthDate);
            stmt.setInt(5, authorId);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Author ID " + authorId + " Updated Successfully");
            }else{
                System.out.println("No Author Found With ID " + authorId);
            }
        }catch(SQLException e){
            System.out.println("AuthorRepository updateAuthor Fel: " + e.getMessage());
        }
    }

    public ArrayList<Author> getAuthorsByBookId(int bookId){
    ArrayList<Author> authors = new ArrayList<>();

    try (Connection conn = DatabaseConnection.connect();

         PreparedStatement stmt = conn.prepareStatement("""
            SELECT * from authors a
            JOIN book_authors ba ON ba.author_id=a.id
            JOIN author_descriptions ad ON ad.author_id=a.id
            WHERE ba.book_id=?
            """);) {

        stmt.setInt(1, bookId);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            authors.add(new Author(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("nationality"),
                    rs.getDate("birth_date").toLocalDate(),
                    rs.getString("biography"),
                    rs.getString("website")
            ));
        }
        return authors;

    } catch (SQLException e) {
        System.out.println("Fel: " + e.getMessage());
    }
        return null;
    }

}
