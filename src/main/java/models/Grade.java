package main.java.models;

public class Grade {
    private int id;
    private Student student;
    private Subject subject;
    private double score;

    public Grade(int id, Student student, Subject subject, double score) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.score = score;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
}
