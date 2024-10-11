package main.java.dao;
import main.java.utils.DatabaseConnection;
import main.java.models.Enrollment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAOImpl {
    private Connection conn;

    public EnrollmentDAOImpl() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEnrollment(Enrollment enrollment) throws SQLException {
        String query = "INSERT INTO enrollments (student_id, subject_id) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, enrollment.getStudentId());
        pstmt.setInt(2, enrollment.getSubjectId());
        pstmt.executeUpdate();
    }

    public void deleteEnrollment(int studentId, int subjectId) throws SQLException {
        String query = "DELETE FROM enrollments WHERE student_id = ? AND subject_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, studentId);
        pstmt.setInt(2, subjectId);
        pstmt.executeUpdate();
    }

    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        String query = "SELECT * FROM enrollments";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int subjectId = rs.getInt("subject_id");
                enrollments.add(new Enrollment(studentId, subjectId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
}
