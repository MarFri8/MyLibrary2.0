package repository;

import model.Category;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class CategoryRepository {

    public void linkBookToCategory(int bookId, int categoryId){
        String sql = "INSERT INTO book_categories (book_id, category_id) VALUES (?, ?)";

        try(Connection conn = DatabaseConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, bookId);
            stmt.setInt(2, categoryId);

            stmt.executeUpdate();
            System.out.println("Book Successfully Added to Category");
        }catch(SQLException e){
            System.out.println("CategoryRepository linkBookToCategory Fel: " + e.getMessage());
        }
    }

    public ArrayList<Category> getCategoriesByBookId(int bookId) {

        ArrayList<Category> categories = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("""
            SELECT * from categories c
            JOIN book_categories bc ON bc.category_id=c.id
            WHERE bc.book_id=?
            """);) {

            stmt.setInt(1, bookId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
            return categories;

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

}
