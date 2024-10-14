package main.java.dao;
import main.java.utils.DatabaseConnection; // Add this import statement
import main.java.models.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl {
    private Connection conn;

    public SubjectDAOImpl() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubject(Subject subject) throws SQLException {
        String query = "INSERT INTO subjects (name, credits) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, subject.getName());
        pstmt.setInt(2, subject.getCredits());
        pstmt.executeUpdate();
    }

    public Subject getSubject(int id) throws SQLException {
        String query = "SELECT * FROM subjects WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return new Subject(rs.getInt("id"), rs.getString("name"), rs.getInt("credits"));
        }
        return null;
    }

    public List<Subject> getAllSubjects() throws SQLException {
        String query = "SELECT * FROM subjects";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Subject> subjects = new ArrayList<>();

        while (rs.next()) {
            subjects.add(new Subject(rs.getInt("id"), rs.getString("name"), rs.getInt("credits")));
        }

        return subjects;
    }

    public void updateSubject(Subject subject) throws SQLException {
        String query = "UPDATE subjects SET name = ?, credits = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, subject.getName());
        pstmt.setInt(2, subject.getCredits());
        pstmt.setInt(3, subject.getId());
        pstmt.executeUpdate();
    }

    public void deleteSubject(int id) throws SQLException {
        String query = "DELETE FROM subjects WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public void assignTeacherToSubject(int subjectId, int teacherId) throws SQLException {
        String query = "UPDATE subjects SET teacher_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, teacherId);
            pstmt.setInt(2, subjectId);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Asignatura no encontrada o no se pudo asignar el profesor.");
            }
        }
    }
    
    
}
