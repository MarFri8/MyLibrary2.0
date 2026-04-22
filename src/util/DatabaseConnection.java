package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASS = "Mm050472?";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
