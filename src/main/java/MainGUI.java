package main.java;

import main.java.dao.*;
import main.java.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MainGUI extends JFrame {
    private final StudentDAOImpl studentDAO;
    private final SubjectDAOImpl subjectDAO;
    private final TeacherDAOImpl teacherDAO;
    private final EnrollmentDAOImpl enrollmentDAO;

    private JTable dataTable;
    private DefaultTableModel tableModel;

    public MainGUI() throws SQLException {
        studentDAO = new StudentDAOImpl();
        subjectDAO = new SubjectDAOImpl();
        teacherDAO = new TeacherDAOImpl();
        enrollmentDAO = new EnrollmentDAOImpl();

        // Configuración de la ventana
        setTitle("Sistema de Gestión Académica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menú
        JMenuBar menuBar = new JMenuBar();
        JMenu studentMenu = new JMenu("Estudiantes");
        JMenu subjectMenu = new JMenu("Asignaturas");
        JMenu teacherMenu = new JMenu("Profesores");
        JMenu enrollmentMenu = new JMenu("Inscripciones");

        // Opciones del menú de estudiantes
        JMenuItem addStudent = new JMenuItem("Añadir Estudiante");
        addStudent.addActionListener(e -> showAddStudentDialog());
        studentMenu.add(addStudent);

        JMenuItem deleteStudent = new JMenuItem("Eliminar Estudiante");
        deleteStudent.addActionListener(e -> showDeleteStudentDialog());
        studentMenu.add(deleteStudent);

        // Opciones del menú de asignaturas
        JMenuItem addSubject = new JMenuItem("Añadir Asignatura");
        addSubject.addActionListener(e -> showAddSubjectDialog());
        subjectMenu.add(addSubject);

        JMenuItem deleteSubject = new JMenuItem("Eliminar Asignatura");
        deleteSubject.addActionListener(e -> showDeleteSubjectDialog());
        subjectMenu.add(deleteSubject);

        // Opciones del menú de profesores
        JMenuItem addTeacher = new JMenuItem("Añadir Profesor");
        addTeacher.addActionListener(e -> showAddTeacherDialog());
        teacherMenu.add(addTeacher);

        JMenuItem deleteTeacher = new JMenuItem("Eliminar Profesor");
        deleteTeacher.addActionListener(e -> showDeleteTeacherDialog());
        teacherMenu.add(deleteTeacher);

        // Opciones del menú de inscripciones
        JMenuItem enrollStudent = new JMenuItem("Inscribir Estudiante en Asignatura");
        enrollStudent.addActionListener(e -> showEnrollStudentDialog());
        enrollmentMenu.add(enrollStudent);

        JMenuItem unenrollStudent = new JMenuItem("Eliminar Inscripción de Estudiante");
        unenrollStudent.addActionListener(e -> showUnenrollStudentDialog());
        enrollmentMenu.add(unenrollStudent);

        // Añadir menús a la barra
        menuBar.add(studentMenu);
        menuBar.add(subjectMenu);
        menuBar.add(teacherMenu);
        menuBar.add(enrollmentMenu);
        setJMenuBar(menuBar);

        // Configuración de la tabla para mostrar datos
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);

        // Panel de botones para mostrar información
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        JButton viewAllStudentsButton = new JButton("Ver Todos los Estudiantes");
        viewAllStudentsButton.addActionListener(e -> viewAllStudents());
        buttonPanel.add(viewAllStudentsButton);

        JButton viewAllSubjectsButton = new JButton("Ver Todas las Asignaturas");
        viewAllSubjectsButton.addActionListener(e -> viewAllSubjects());
        buttonPanel.add(viewAllSubjectsButton);

        JButton viewAllTeachersButton = new JButton("Ver Todos los Profesores");
        viewAllTeachersButton.addActionListener(e -> viewAllTeachers());
        buttonPanel.add(viewAllTeachersButton);

        JButton viewAllEnrollmentsButton = new JButton("Ver Todos las Inscripciones");
        viewAllEnrollmentsButton.addActionListener(e -> showAllEnrollmentsDialog());
        buttonPanel.add(viewAllEnrollmentsButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Métodos para mostrar datos en formato tabla
    private void viewAllStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            String[] columns = {"ID", "Nombre", "Apellido", "Edad"};
            Object[][] data = new Object[students.size()][4];

            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                data[i][0] = student.getId();
                data[i][1] = student.getFirstName();
                data[i][2] = student.getLastName();
                data[i][3] = student.getAge();
            }

            tableModel.setDataVector(data, columns);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener estudiantes: " + e.getMessage());
        }
    }

    private void viewAllSubjects() {
        try {
            List<Subject> subjects = subjectDAO.getAllSubjects();
            String[] columns = {"ID", "Nombre", "Créditos"};
            Object[][] data = new Object[subjects.size()][3];

            for (int i = 0; i < subjects.size(); i++) {
                Subject subject = subjects.get(i);
                data[i][0] = subject.getId();
                data[i][1] = subject.getName();
                data[i][2] = subject.getCredits();
            }

            tableModel.setDataVector(data, columns);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener asignaturas: " + e.getMessage());
        }
    }

    private void viewAllTeachers() {
        try {
            List<Teacher> teachers = teacherDAO.getAllTeachers();
            String[] columns = {"ID", "Nombre", "Departamento"};
            Object[][] data = new Object[teachers.size()][3];

            for (int i = 0; i < teachers.size(); i++) {
                Teacher teacher = teachers.get(i);
                data[i][0] = teacher.getId();
                data[i][1] = teacher.getName();
                data[i][2] = teacher.getDepartment();
            }

            tableModel.setDataVector(data, columns);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener profesores: " + e.getMessage());
        }
    }

    private void showAllEnrollmentsDialog() {
        try{
            // Obtener todas las inscripciones
            List<Object[]> enrollments = enrollmentDAO.getAllEnrollmentDetails();
        
            // Crear el modelo de la tabla
            String[] columnNames = {"ID Estudiante", "Nombre Estudiante", "ID Asignatura", "Nombre Asignatura"};
            Object[][] data = new Object[enrollments.size()][4];
        
            // Llenar la tabla con los datos obtenidos
            for (int i = 0; i < enrollments.size(); i++) {
                Object[] enrollment = enrollments.get(i);
                data[i][0] = enrollment[0]; // ID del estudiante
                data[i][1] = enrollment[1]; // Nombre del estudiante
                data[i][2] = enrollment[2]; // ID de la asignatura
                data[i][3] = enrollment[3]; // Nombre de la asignatura
            }
            
            // Actualizar el modelo de la tabla
            tableModel.setDataVector(data, columnNames);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener inscripciones: " + e.getMessage());
        }
    }
    

    // Métodos para añadir, eliminar e inscribir estudiantes, asignaturas y profesores (anteriormente implementados)
    // Diálogo para añadir estudiante
    private void showAddStudentDialog() {
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField ageField = new JTextField();
    
        Object[] message = {
            "Nombre:", firstNameField,
            "Apellido:", lastNameField,
            "Edad:", ageField
        };
    
        int option = JOptionPane.showConfirmDialog(null, message, "Añadir Estudiante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                
                Student student = new Student(0, firstName, lastName, age);
                studentDAO.addStudent(student);
                JOptionPane.showMessageDialog(this, "Estudiante añadido correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al añadir estudiante: " + e.getMessage());
            }
        }
    }

    // Diálogo para eliminar estudiante
    private void showDeleteStudentDialog() {
        JTextField idField = new JTextField();
        Object[] message = {"ID del Estudiante:", idField};
    
        int option = JOptionPane.showConfirmDialog(null, message, "Eliminar Estudiante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(idField.getText());
                studentDAO.deleteStudent(studentId);
                JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar estudiante: " + e.getMessage());
            }
        }
    }

    // Diálogo para añadir asignatura
    private void showAddSubjectDialog() {
        JTextField nameField = new JTextField();
        JTextField creditsField = new JTextField();
    
        Object[] message = {
            "Nombre de la Asignatura:", nameField,
            "Créditos:", creditsField
        };
    
        int option = JOptionPane.showConfirmDialog(null, message, "Añadir Asignatura", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int credits = Integer.parseInt(creditsField.getText());
                
                Subject subject = new Subject(0, name, credits);
                subjectDAO.addSubject(subject);
                JOptionPane.showMessageDialog(this, "Asignatura añadida correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al añadir asignatura: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido de créditos.");
            }
        }
    }
    
    // Diálogo para eliminar asignatura
    private void showDeleteSubjectDialog() {
        JTextField idField = new JTextField();
        Object[] message = {"ID de la Asignatura:", idField};
    
        int option = JOptionPane.showConfirmDialog(null, message, "Eliminar Asignatura", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int subjectId = Integer.parseInt(idField.getText());
                subjectDAO.deleteSubject(subjectId);
                JOptionPane.showMessageDialog(this, "Asignatura eliminada correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar asignatura: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.");
            }
        }
    }

    // Diálogo para añadir profesor
    private void showAddTeacherDialog() {
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
    
        Object[] message = {
            "Nombre:", nameField,
            "Departamento:", departmentField
        };
    
        int option = JOptionPane.showConfirmDialog(null, message, "Añadir Profesor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String department = departmentField.getText();
                
                Teacher teacher = new Teacher(0, name, department);
                teacherDAO.addTeacher(teacher);
                JOptionPane.showMessageDialog(this, "Profesor añadido correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al añadir profesor: " + e.getMessage());
            }
        }
    }
    
    // Diálogo para eliminar profesor
    private void showDeleteTeacherDialog() {
        JTextField idField = new JTextField();
        Object[] message = {"ID del Profesor:", idField};
    
        int option = JOptionPane.showConfirmDialog(null, message, "Eliminar Profesor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int teacherId = Integer.parseInt(idField.getText());
                teacherDAO.deleteTeacher(teacherId);
                JOptionPane.showMessageDialog(this, "Profesor eliminado correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar profesor: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.");
            }
        }
    }    

    // Diálogo para inscribir estudiante en asignatura
    private void showEnrollStudentDialog() {
        JTextField studentIdField = new JTextField();
        JTextField subjectIdField = new JTextField();

        Object[] message = {
            "ID del Estudiante:", studentIdField,
            "ID de la Asignatura:", subjectIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Inscribir Estudiante en Asignatura", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int subjectId = Integer.parseInt(subjectIdField.getText());
                
                // Utilizar el EnrollmentDAO para inscribir al estudiante
                Enrollment enrollment = new Enrollment(studentId, subjectId);
                enrollmentDAO.addEnrollment(enrollment);  // Aquí llamas al método correcto
                JOptionPane.showMessageDialog(this, "Estudiante inscrito correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al inscribir estudiante: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese números válidos.");
            }
        }
    }

    // Diálogo para eliminar la inscripción de un estudiante
    private void showUnenrollStudentDialog() {
        JTextField studentIdField = new JTextField();
        JTextField subjectIdField = new JTextField();

        Object[] message = {
            "ID del Estudiante:", studentIdField,
            "ID de la Asignatura:", subjectIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Eliminar Inscripción de Estudiante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int subjectId = Integer.parseInt(subjectIdField.getText());
                
                // Aquí usamos el DAO correcto para las inscripciones
                enrollmentDAO.deleteEnrollment(studentId, subjectId);  // Usamos el método deleteEnrollment de EnrollmentDAOImpl
                JOptionPane.showMessageDialog(this, "Inscripción eliminada correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar inscripción: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
