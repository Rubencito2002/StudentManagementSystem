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

    public List<Object[]> getAllEnrollmentDetails() {
        List<Object[]> enrollmentDetailsList = new ArrayList<>();
        String query = "SELECT e.student_id, s.firstName AS student_name, e.subject_id, sub.name AS subject_name, t.name AS teacher_name " +
                       "FROM enrollments e " +
                       "JOIN students s ON e.student_id = s.id " +
                       "JOIN subjects sub ON e.subject_id = sub.id " +
                       "LEFT JOIN teachers t ON sub.teacher_id = t.id";  // Asegúrate de que hay profesores asignados
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                int subjectId = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                String teacherName = rs.getString("teacher_name");
    
                enrollmentDetailsList.add(new Object[] {studentId, studentName, subjectId, subjectName, teacherName});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollmentDetailsList;
    }
    
    public void updateEnrollment(Enrollment enrollment) throws SQLException {
        String query = "UPDATE enrollments SET subject_id = ? WHERE student_id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, enrollment.getSubjectId());
            statement.setInt(2, enrollment.getStudentId());
            statement.executeUpdate();
        }
    }
    
}
