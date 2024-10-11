package main.java.dao;

import main.java.models.Teacher;
import java.util.List;

public interface TeacherDAO {
    void addTeacher(Teacher teacher);
    Teacher getTeacher(int id);
    List<Teacher> getAllTeachers();
    void updateTeacher(Teacher teacher);
    void deleteTeacher(int id);
}
