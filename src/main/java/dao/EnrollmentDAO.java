package main.java.dao;

import main.java.models.Enrollment;
import java.util.List;

public interface EnrollmentDAO {
    void addEnrollment(Enrollment enrollment);
    void deleteEnrollment(int studentId, int subjectId);
    List<Enrollment> getAllEnrollments();  // Agregar método para obtener todas las inscripciones
}
