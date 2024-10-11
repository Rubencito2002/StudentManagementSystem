package main.java.dao;

import main.java.models.Subject;
import java.util.List;

public interface SubjectDAO {
    void addSubject(Subject subject);
    Subject getSubject(int id);
    List<Subject> getAllSubjects();
    void updateSubject(Subject subject);
    void deleteSubject(int id);
}
