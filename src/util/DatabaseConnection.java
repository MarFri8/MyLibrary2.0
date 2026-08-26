package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "your database";
    private static final String USER = your sql username;
    private static final String PASS = your sql pass";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
