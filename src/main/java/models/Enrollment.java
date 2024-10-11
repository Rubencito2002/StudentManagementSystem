package main.java.models;

public class Enrollment {
    private int studentId;
    private int subjectId;

    public Enrollment(int studentId, int subjectId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
    }

    // Getters y Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
}
