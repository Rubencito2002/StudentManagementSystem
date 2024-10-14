package main.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://autorack.proxy.rlwy.net:54480/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "qCyLsTZssKTOFFcMpWppXzGvOJqYwwoq";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método temporal para agregar la columna teacher_id
    // public static void addTeacherIdColumn() {
    //     String alterTableQuery = "ALTER TABLE subjects ADD COLUMN teacher_id INT";
    //     try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
    //         stmt.executeUpdate(alterTableQuery);
    //         System.out.println("Columna teacher_id añadida a la tabla subjects.");
    //     } catch (SQLException e) {
    //         System.out.println("Error al modificar la tabla subjects: " + e.getMessage());
    //     }
    // }
}
