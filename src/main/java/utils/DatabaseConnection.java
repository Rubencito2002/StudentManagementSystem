package main.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://autorack.proxy.rlwy.net:54480/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "qCyLsTZssKTOFFcMpWppXzGvOJqYwwoq";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
