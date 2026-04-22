package repository;

import model.Category;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class CategoryRepository {

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
