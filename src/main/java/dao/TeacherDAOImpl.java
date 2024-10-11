package main.java.dao;
import main.java.models.Teacher;
import java.sql.*;
import main.java.utils.DatabaseConnection; // Adjust the package path as necessary
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl {
    private Connection conn;

    public TeacherDAOImpl() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTeacher(Teacher teacher) throws SQLException {
        String query = "INSERT INTO teachers (name, department) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, teacher.getName());
        pstmt.setString(2, teacher.getDepartment());
        pstmt.executeUpdate();
    }

    public Teacher getTeacher(int id) throws SQLException {
        String query = "SELECT * FROM teachers WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return new Teacher(rs.getInt("id"), rs.getString("name"), rs.getString("department"));
        }
        return null;
    }

    public List<Teacher> getAllTeachers() throws SQLException {
        String query = "SELECT * FROM teachers";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Teacher> teachers = new ArrayList<>();

        while (rs.next()) {
            teachers.add(new Teacher(rs.getInt("id"), rs.getString("name"), rs.getString("department")));
        }

        return teachers;
    }

    public void updateTeacher(Teacher teacher) throws SQLException {
        String query = "UPDATE teachers SET name = ?, department = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, teacher.getName());
        pstmt.setString(2, teacher.getDepartment());
        pstmt.setInt(3, teacher.getId());
        pstmt.executeUpdate();
    }

    public void deleteTeacher(int id) throws SQLException {
        String query = "DELETE FROM teachers WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
}
