package main.java;

import main.java.dao.*;
import main.java.models.*;
// import main.java.utils.DatabaseConnection;

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
        // Asegurarse de que la columna teacher_id esté presente en la tabla subjects
        // DatabaseConnection.addTeacherIdColumn();

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
        JMenu listMenu = new JMenu("Listar Datos");

        // Opciones del menú de estudiantes
        JMenuItem addStudent = new JMenuItem("Añadir Estudiante");
        addStudent.addActionListener(e -> showAddStudentDialog());
        studentMenu.add(addStudent);

        JMenuItem deleteStudent = new JMenuItem("Eliminar Estudiante");
        deleteStudent.addActionListener(e -> showDeleteStudentDialog());
        studentMenu.add(deleteStudent);

        JMenuItem updateStudent = new JMenuItem("Actualizar Estudiante");
        updateStudent.addActionListener(e -> showUpdateStudentDialog());
        studentMenu.add(updateStudent);

        // Opciones del menú de asignaturas
        JMenuItem addSubject = new JMenuItem("Añadir Asignatura");
        addSubject.addActionListener(e -> showAddSubjectDialog());
        subjectMenu.add(addSubject);

        JMenuItem deleteSubject = new JMenuItem("Eliminar Asignatura");
        deleteSubject.addActionListener(e -> showDeleteSubjectDialog());
        subjectMenu.add(deleteSubject);

        JMenuItem updateSubject = new JMenuItem("Actualizar Asignatura");
        updateSubject.addActionListener(e -> showUpdateSubjectDialog());
        subjectMenu.add(updateSubject);

        // Opciones del menú de profesores
        JMenuItem addTeacher = new JMenuItem("Añadir Profesor");
        addTeacher.addActionListener(e -> showAddTeacherDialog());
        teacherMenu.add(addTeacher);

        JMenuItem deleteTeacher = new JMenuItem("Eliminar Profesor");
        deleteTeacher.addActionListener(e -> showDeleteTeacherDialog());
        teacherMenu.add(deleteTeacher);

        JMenuItem updateTeacher = new JMenuItem("Actualizar Profesor");
        updateTeacher.addActionListener(e -> showUpdateTeacherDialog());
        teacherMenu.add(updateTeacher);

        JMenuItem assignTeacherToSubject = new JMenuItem("Asignar Profesor a Asignatura");
        assignTeacherToSubject.addActionListener(e -> showAssignTeacherToSubjectDialog());
        teacherMenu.add(assignTeacherToSubject);

        // Opciones del menú de inscripciones
        JMenuItem enrollStudent = new JMenuItem("Inscribir Estudiante en Asignatura");
        enrollStudent.addActionListener(e -> showEnrollStudentDialog());
        enrollmentMenu.add(enrollStudent);

        JMenuItem unenrollStudent = new JMenuItem("Eliminar Inscripción de Estudiante");
        unenrollStudent.addActionListener(e -> showUnenrollStudentDialog());
        enrollmentMenu.add(unenrollStudent);

        JMenuItem updateEnrollment = new JMenuItem("Actualizar Inscripción");
        updateEnrollment.addActionListener(e -> showUpdateEnrollmentDialog());
        enrollmentMenu.add(updateEnrollment);

        // Opciones del menú de listados de datos.
        JMenuItem viewAllStudentsButton = new JMenuItem("Ver Todos los Estudiantes");
        viewAllStudentsButton.addActionListener(e -> viewAllStudents());
        listMenu.add(viewAllStudentsButton);

        JMenuItem viewAllSubjectsButton = new JMenuItem("Ver Todas las Asignaturas");
        viewAllSubjectsButton.addActionListener(e -> viewAllSubjects());
        listMenu.add(viewAllSubjectsButton);

        JMenuItem viewAllTeachersButton = new JMenuItem("Ver Todos los Profesores");
        viewAllTeachersButton.addActionListener(e -> viewAllTeachers());
        listMenu.add(viewAllTeachersButton);

        JMenuItem viewAllEnrollmentsButton = new JMenuItem("Ver Todos las Inscripciones");
        viewAllEnrollmentsButton.addActionListener(e -> showAllEnrollmentsDialog());
        listMenu.add(viewAllEnrollmentsButton);

        // Botón para ver todos los datos (estudiantes, asignaturas, profesores e inscripciones)
        JMenuItem viewAllDataButton = new JMenuItem("Ver Todos los Datos");
        viewAllDataButton.addActionListener(e -> viewAllData());
        listMenu.add(viewAllDataButton);

        // Añadir menús a la barra
        menuBar.add(studentMenu);
        menuBar.add(subjectMenu);
        menuBar.add(teacherMenu);
        menuBar.add(enrollmentMenu);
        menuBar.add(listMenu);
        setJMenuBar(menuBar);

        // Configuración de la tabla para mostrar datos
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);
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
        try {
            // Obtener los detalles de todas las inscripciones
            List<Object[]> enrollments = enrollmentDAO.getAllEnrollmentDetails();
    
            // Crear el modelo de la tabla con las columnas correctas
            String[] columnNames = {"ID Estudiante", "Nombre Estudiante", "ID Asignatura", "Nombre Asignatura", "Profesor"};
            Object[][] data = new Object[enrollments.size()][5];
    
            // Llenar la tabla con los datos obtenidos
            for (int i = 0; i < enrollments.size(); i++) {
                Object[] enrollment = enrollments.get(i);
                data[i][0] = enrollment[0]; // ID del estudiante
                data[i][1] = enrollment[1]; // Nombre del estudiante
                data[i][2] = enrollment[2]; // ID de la asignatura
                data[i][3] = enrollment[3]; // Nombre de la asignatura
                data[i][4] = enrollment[4]; // Nombre del profesor
            }
    
            // Actualizar el modelo de la tabla
            tableModel.setDataVector(data, columnNames);
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener inscripciones: " + e.getMessage());
        }
    }

    private void viewAllData() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            List<Subject> subjects = subjectDAO.getAllSubjects();
            List<Teacher> teachers = teacherDAO.getAllTeachers();
            List<Object[]> enrollments = enrollmentDAO.getAllEnrollmentDetails(); // Detalles de inscripciones
    
            // Crear un JTabbedPane para mostrar varias tablas en pestañas
            JTabbedPane tabbedPane = new JTabbedPane();
    
            // Tab para Estudiantes
            DefaultTableModel studentModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Edad"}, 0);
            for (Student student : students) {
                studentModel.addRow(new Object[]{student.getId(), student.getFirstName(), student.getLastName(), student.getAge()});
            }
            JTable studentTable = new JTable(studentModel);
            tabbedPane.addTab("Estudiantes", new JScrollPane(studentTable));
    
            // Tab para Asignaturas
            DefaultTableModel subjectModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Créditos"}, 0);
            for (Subject subject : subjects) {
                subjectModel.addRow(new Object[]{subject.getId(), subject.getName(), subject.getCredits()});
            }
            JTable subjectTable = new JTable(subjectModel);
            tabbedPane.addTab("Asignaturas", new JScrollPane(subjectTable));
    
            // Tab para Profesores
            DefaultTableModel teacherModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Departamento"}, 0);
            for (Teacher teacher : teachers) {
                teacherModel.addRow(new Object[]{teacher.getId(), teacher.getName(), teacher.getDepartment()});
            }
            JTable teacherTable = new JTable(teacherModel);
            tabbedPane.addTab("Profesores", new JScrollPane(teacherTable));
    
            // Tab para Inscripciones
            DefaultTableModel enrollmentModel = new DefaultTableModel(new String[]{"ID Estudiante", "Nombre Estudiante", "ID Asignatura", "Nombre Asignatura", "Nombre Profesor"}, 0);
            for (Object[] enrollment : enrollments) {
                enrollmentModel.addRow(new Object[]{enrollment[0], enrollment[1], enrollment[2], enrollment[3], enrollment[4]});
            }
            JTable enrollmentTable = new JTable(enrollmentModel);
            tabbedPane.addTab("Inscripciones", new JScrollPane(enrollmentTable));
    
            // Crear y configurar el diálogo
            JDialog dialog = new JDialog(this, "Datos de Estudiantes, Asignaturas, Profesores e Inscripciones", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.add(tabbedPane);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos: " + e.getMessage());
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

    // Diálogo para actualizar estudiante
    private void showUpdateStudentDialog() {
        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField ageField = new JTextField();

        Object[] message = {
            "ID del Estudiante:", idField,
            "Nuevo Nombre:", firstNameField,
            "Nuevo Apellido:", lastNameField,
            "Nueva Edad:", ageField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Actualizar Estudiante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                int age = Integer.parseInt(ageField.getText());

                Student student = new Student(id, firstName, lastName, age);
                studentDAO.updateStudent(student); // Asegúrate de tener este método en StudentDAOImpl
                JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos.");
            }catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar estudiante: " + e.getMessage());
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

    // Diálogo para actualizar asignatura
    private void showUpdateSubjectDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField creditsField = new JTextField();

        Object[] message = {
            "ID de la Asignatura:", idField,
            "Nuevo Nombre:", nameField,
            "Nuevos Créditos:", creditsField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Actualizar Asignatura", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int credits = Integer.parseInt(creditsField.getText());

                Subject subject = new Subject(id, name, credits);
                subjectDAO.updateSubject(subject); // Asegúrate de tener este método en SubjectDAOImpl
                JOptionPane.showMessageDialog(this, "Asignatura actualizada correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar asignatura: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos.");
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

    // Diálogo para actualizar profesor
    private void showUpdateTeacherDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();

        Object[] message = {
            "ID del Profesor:", idField,
            "Nuevo Nombre:", nameField,
            "Nuevo Departamento:", departmentField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Actualizar Profesor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String department = departmentField.getText();

                Teacher teacher = new Teacher(id, name, department);
                teacherDAO.updateTeacher(teacher); // Asegúrate de tener este método en TeacherDAOImpl
                JOptionPane.showMessageDialog(this, "Profesor actualizado correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar profesor: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos.");
            }
        }
    }

    // Diálogo para asignar profesor a asignatura
    private void showAssignTeacherToSubjectDialog() {
        JTextField subjectIdField = new JTextField();
        JTextField teacherIdField = new JTextField();
    
        Object[] message = {
            "ID de la Asignatura:", subjectIdField,
            "ID del Profesor:", teacherIdField
        };
    
        int option = JOptionPane.showConfirmDialog(null, message, "Asignar Profesor a Asignatura", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int subjectId = Integer.parseInt(subjectIdField.getText());
                int teacherId = Integer.parseInt(teacherIdField.getText());
    
                // Llamar al DAO para asignar el profesor a la asignatura
                subjectDAO.assignTeacherToSubject(subjectId, teacherId);
                JOptionPane.showMessageDialog(this, "Profesor asignado correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al asignar profesor: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "IDs inválidos: " + e.getMessage());
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

    // Diálogo para actualizar inscripción
    private void showUpdateEnrollmentDialog() {
        JTextField studentIdField = new JTextField();
        JTextField subjectIdField = new JTextField();

        Object[] message = {
            "ID del Estudiante:", studentIdField,
            "Nuevo ID de la Asignatura:", subjectIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Actualizar Inscripción", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int subjectId = Integer.parseInt(subjectIdField.getText());

                // Utiliza el EnrollmentDAO para actualizar la inscripción
                Enrollment enrollment = new Enrollment(studentId, subjectId);
                enrollmentDAO.updateEnrollment(enrollment); // Asegúrate de tener este método en EnrollmentDAOImpl
                JOptionPane.showMessageDialog(this, "Inscripción actualizada correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar inscripción: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese números válidos.");
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
