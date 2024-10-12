package main.java;

import main.java.dao.StudentDAOImpl;
import main.java.dao.SubjectDAOImpl;
import main.java.dao.TeacherDAOImpl;
import main.java.dao.EnrollmentDAOImpl;
import main.java.models.Student;
import main.java.models.Subject;
import main.java.models.Teacher;
import main.java.utils.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainGUI extends JFrame {
    private final StudentDAOImpl studentDAO;
    private final SubjectDAOImpl subjectDAO;
    private final TeacherDAOImpl teacherDAO;
    private final EnrollmentDAOImpl enrollmentDAO;

    private final JTextArea outputArea;
    private final JTextField studentNameField;
    private final JTextField studentLastNameField;
    private final JTextField studentAgeField;
    private final JTextField subjectNameField;
    private final JTextField subjectCreditsField;
    private final JTextField teacherNameField;
    private final JTextField teacherDepartmentField;
    private final JTextField deleteStudentIdField;
    private final JTextField deleteSubjectIdField;
    private final JTextField deleteTeacherIdField;

    public MainGUI() throws SQLException {
        studentDAO = new StudentDAOImpl();
        subjectDAO = new SubjectDAOImpl();
        teacherDAO = new TeacherDAOImpl();
        enrollmentDAO = new EnrollmentDAOImpl();

        // Configuración de la ventana
        setTitle("Sistema de Gestión Académica");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de salida
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Panel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));

        // Campos para añadir estudiantes
        inputPanel.add(new JLabel("Nombre del Estudiante:"));
        studentNameField = new JTextField();
        inputPanel.add(studentNameField);

        inputPanel.add(new JLabel("Apellido del Estudiante:"));
        studentLastNameField = new JTextField();
        inputPanel.add(studentLastNameField);

        inputPanel.add(new JLabel("Edad del Estudiante:"));
        studentAgeField = new JTextField();
        inputPanel.add(studentAgeField);

        // Botón para añadir estudiante
        JButton addStudentButton = new JButton("Añadir Estudiante");
        addStudentButton.addActionListener(e -> addStudent());
        inputPanel.add(addStudentButton);

        // Botón para eliminar estudiante
        inputPanel.add(new JLabel("ID del Estudiante a Eliminar:"));
        deleteStudentIdField = new JTextField();
        inputPanel.add(deleteStudentIdField);

        JButton deleteStudentButton = new JButton("Eliminar Estudiante");
        deleteStudentButton.addActionListener(e -> deleteStudent());
        inputPanel.add(deleteStudentButton);

        // Campos para añadir asignaturas
        inputPanel.add(new JLabel("Nombre de la Asignatura:"));
        subjectNameField = new JTextField();
        inputPanel.add(subjectNameField);

        inputPanel.add(new JLabel("Créditos:"));
        subjectCreditsField = new JTextField();
        inputPanel.add(subjectCreditsField);

        // Botón para añadir asignatura
        JButton addSubjectButton = new JButton("Añadir Asignatura");
        addSubjectButton.addActionListener(e -> addSubject());
        inputPanel.add(addSubjectButton);

        // Botón para eliminar asignatura
        inputPanel.add(new JLabel("ID de la Asignatura a Eliminar:"));
        deleteSubjectIdField = new JTextField();
        inputPanel.add(deleteSubjectIdField);

        JButton deleteSubjectButton = new JButton("Eliminar Asignatura");
        deleteSubjectButton.addActionListener(e -> deleteSubject());
        inputPanel.add(deleteSubjectButton);

        // Campos para añadir profesores
        inputPanel.add(new JLabel("Nombre del Profesor:"));
        teacherNameField = new JTextField();
        inputPanel.add(teacherNameField);

        inputPanel.add(new JLabel("Departamento:"));
        teacherDepartmentField = new JTextField();
        inputPanel.add(teacherDepartmentField);

        // Botón para añadir profesor
        JButton addTeacherButton = new JButton("Añadir Profesor");
        addTeacherButton.addActionListener(e -> addTeacher());
        inputPanel.add(addTeacherButton);

        // Botón para eliminar profesor
        inputPanel.add(new JLabel("ID del Profesor a Eliminar:"));
        deleteTeacherIdField = new JTextField();
        inputPanel.add(deleteTeacherIdField);

        JButton deleteTeacherButton = new JButton("Eliminar Profesor");
        deleteTeacherButton.addActionListener(e -> deleteTeacher());
        inputPanel.add(deleteTeacherButton);

        add(inputPanel, BorderLayout.NORTH);

        // Panel de botones para mostrar información
        JPanel showInfoPanel = new JPanel();
        showInfoPanel.setLayout(new GridLayout(0, 1));

        // Botón para ver todos los estudiantes
        JButton viewStudentsButton = new JButton("Ver Todos los Estudiantes");
        viewStudentsButton.addActionListener(e -> viewAllStudents());
        showInfoPanel.add(viewStudentsButton);

        // Botón para ver todas las asignaturas
        JButton viewSubjectsButton = new JButton("Ver Todas las Asignaturas");
        viewSubjectsButton.addActionListener(e -> viewAllSubjects());
        showInfoPanel.add(viewSubjectsButton);

        // Botón para ver todos los profesores
        JButton viewTeachersButton = new JButton("Ver Todos los Profesores");
        viewTeachersButton.addActionListener(e -> viewAllTeachers());
        showInfoPanel.add(viewTeachersButton);

        // Botón para ver todas las inscripciones
        JButton viewEnrollmentsButton = new JButton("Ver Todas las Inscripciones");
        viewEnrollmentsButton.addActionListener(e -> viewAllEnrollments());
        showInfoPanel.add(viewEnrollmentsButton);

        add(showInfoPanel, BorderLayout.SOUTH);
    }

    // Métodos de funcionalidad
    private void addStudent() {
        try {
            String firstName = studentNameField.getText();
            String lastName = studentLastNameField.getText();
            int age = Integer.parseInt(studentAgeField.getText());

            Student student = new Student(0, firstName, lastName, age);
            studentDAO.addStudent(student);
            outputArea.append("Estudiante añadido: " + firstName + " " + lastName + "\n");
        } catch (Exception e) {
            outputArea.append("Error añadiendo estudiante: " + e.getMessage() + "\n");
        }
    }

    private void deleteStudent() {
        try {
            int studentId = Integer.parseInt(deleteStudentIdField.getText());
            studentDAO.deleteStudent(studentId);
            outputArea.append("Estudiante con ID " + studentId + " eliminado.\n");
        } catch (Exception e) {
            outputArea.append("Error eliminando estudiante: " + e.getMessage() + "\n");
        }
    }

    private void addSubject() {
        try {
            String name = subjectNameField.getText();
            int credits = Integer.parseInt(subjectCreditsField.getText());

            Subject subject = new Subject(0, name, credits);
            subjectDAO.addSubject(subject);
            outputArea.append("Asignatura añadida: " + name + "\n");
        } catch (SQLException | NumberFormatException e) {
            outputArea.append("Error añadiendo asignatura: " + e.getMessage() + "\n");
        }
    }

    private void deleteSubject() {
        try {
            int subjectId = Integer.parseInt(deleteSubjectIdField.getText());
            subjectDAO.deleteSubject(subjectId);
            outputArea.append("Asignatura con ID " + subjectId + " eliminada.\n");
        } catch (SQLException | NumberFormatException e) {
            outputArea.append("Error eliminando asignatura: " + e.getMessage() + "\n");
        }
    }

    private void addTeacher() {
        try {
            String name = teacherNameField.getText();
            String department = teacherDepartmentField.getText();

            Teacher teacher = new Teacher(0, name, department);
            teacherDAO.addTeacher(teacher);
            outputArea.append("Profesor añadido: " + name + "\n");
        } catch (SQLException e) {
            outputArea.append("Error añadiendo profesor: " + e.getMessage() + "\n");
        }
    }

    private void deleteTeacher() {
        try {
            int teacherId = Integer.parseInt(deleteTeacherIdField.getText());
            teacherDAO.deleteTeacher(teacherId);
            outputArea.append("Profesor con ID " + teacherId + " eliminado.\n");
        } catch (SQLException | NumberFormatException e) {
            outputArea.append("Error eliminando profesor: " + e.getMessage() + "\n");
        }
    }

    private void viewAllStudents() {
        try {
            StringBuilder students = new StringBuilder();
            studentDAO.getAllStudents().forEach(st -> 
                students.append("ID: ").append(st.getId())
                        .append(", Nombre: ").append(st.getFirstName())
                        .append(" ").append(st.getLastName())
                        .append(", Edad: ").append(st.getAge()).append("\n"));
            outputArea.setText(students.toString());
        } catch (Exception e) {
            outputArea.append("Error al obtener estudiantes: " + e.getMessage() + "\n");
        }
    }

    private void viewAllSubjects() {
        try {
            StringBuilder subjects = new StringBuilder();
            subjectDAO.getAllSubjects().forEach(sub -> 
                subjects.append("ID: ").append(sub.getId())
                        .append(", Nombre: ").append(sub.getName())
                        .append(", Créditos: ").append(sub.getCredits()).append("\n"));
            outputArea.setText(subjects.toString());
        } catch (Exception e) {
            outputArea.append("Error al obtener asignaturas: " + e.getMessage() + "\n");
        }
    }

    private void viewAllTeachers() {
        try {
            StringBuilder teachers = new StringBuilder();
            teacherDAO.getAllTeachers().forEach(teacher -> 
                teachers.append("ID: ").append(teacher.getId())
                        .append(", Nombre: ").append(teacher.getName())
                        .append(", Departamento: ").append(teacher.getDepartment()).append("\n"));
            outputArea.setText(teachers.toString());
        } catch (SQLException e) {
            outputArea.append("Error al obtener profesores: " + e.getMessage() + "\n");
        }
    }

    private void viewAllEnrollments() {
        try {
            StringBuilder enrollments = new StringBuilder();
            enrollmentDAO.getAllEnrollments().forEach(enr -> 
                enrollments.append("Estudiante ID: ").append(enr.getStudentId())
                           .append(", Asignatura ID: ").append(enr.getSubjectId()).append("\n"));
            outputArea.setText(enrollments.toString());
        } catch (Exception e) {
            outputArea.append("Error al obtener inscripciones: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
