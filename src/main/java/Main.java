package main.java;

import main.java.dao.StudentDAOImpl;
import main.java.dao.SubjectDAOImpl;
import main.java.dao.TeacherDAOImpl;
import main.java.dao.EnrollmentDAOImpl;
import main.java.models.Student;
import main.java.models.Subject;
import main.java.models.Teacher;
import main.java.models.Enrollment;
import main.java.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            // Conexión a la base de datos
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Conexión exitosa a la base de datos.");

            // Verificar y crear tablas si no existen
            checkAndCreateTables(conn);

            // Iniciar los DAOs y el menú
            StudentDAOImpl studentDAO = new StudentDAOImpl();
            SubjectDAOImpl subjectDAO = new SubjectDAOImpl();
            TeacherDAOImpl teacherDAO = new TeacherDAOImpl();
            EnrollmentDAOImpl enrollmentDAO = new EnrollmentDAOImpl();
            
            Scanner scanner = new Scanner(System.in);

            // Menú principal
            while (true) {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Añadir estudiante");
                System.out.println("2. Ver estudiante");
                System.out.println("3. Ver todos los estudiantes");
                System.out.println("4. Actualizar estudiante");
                System.out.println("5. Eliminar estudiante");
                System.out.println("6. Añadir asignatura");
                System.out.println("7. Ver todas las asignaturas");
                System.out.println("8. Añadir profesor");
                System.out.println("9. Ver todos los profesores");
                System.out.println("10. Añadir inscripción de estudiante a asignatura");
                System.out.println("11. Ver todas las inscripciones");
                System.out.println("12. Salir");
                System.out.print("Intruduce tu eleccion:");
                int choice = scanner.nextInt();
                
                scanner.nextLine(); // Consumir salto de línea

                switch (choice) {
                    case 1:
                        System.out.println("Nombre:");
                        String firstName = scanner.nextLine();
                        System.out.println("Apellido:");
                        String lastName = scanner.nextLine();
                        System.out.println("Edad:");
                        int age = scanner.nextInt();
                        Student student = new Student(0, firstName, lastName, age);
                        studentDAO.addStudent(student);
                        break;
                    case 2:
                        System.out.println("ID del estudiante:");
                        int id = scanner.nextInt();
                        Student s = studentDAO.getStudent(id);
                        if (s != null) {
                            System.out.println("Estudiante: " + s.getFirstName() + " " + s.getLastName() + ", Edad: " + s.getAge());
                        } else {
                            System.out.println("Estudiante no encontrado.");
                        }
                        break;
                    case 3:
                        studentDAO.getAllStudents().forEach(st ->
                            System.out.println("ID: " + st.getId() + ", Nombre: " + st.getFirstName() + " " + st.getLastName() + ", Edad: " + st.getAge()));
                        break;
                    case 4:
                        System.out.println("ID del estudiante a actualizar:");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();  // Consumir salto de línea
                        Student studentToUpdate = studentDAO.getStudent(updateId);
                        if (studentToUpdate != null) {
                            System.out.println("Nuevo nombre (actual: " + studentToUpdate.getFirstName() + "):");
                            studentToUpdate.setFirstName(scanner.nextLine());
                            System.out.println("Nuevo apellido (actual: " + studentToUpdate.getLastName() + "):");
                            studentToUpdate.setLastName(scanner.nextLine());
                            System.out.println("Nueva edad (actual: " + studentToUpdate.getAge() + "):");
                            studentToUpdate.setAge(scanner.nextInt());
                            studentDAO.updateStudent(studentToUpdate);
                        } else {
                            System.out.println("Estudiante no encontrado.");
                        }
                        break;
                    case 5:
                        System.out.println("ID del estudiante a eliminar:");
                        int deleteId = scanner.nextInt();
                        studentDAO.deleteStudent(deleteId);
                        break;
                    case 6:
                        System.out.println("Nombre de la asignatura:");
                        String subjectName = scanner.nextLine();
                        System.out.println("Créditos:");
                        int credits = scanner.nextInt();
                        Subject subject = new Subject(0, subjectName, credits);
                        subjectDAO.addSubject(subject);
                        break;
                    case 7:
                        subjectDAO.getAllSubjects().forEach(sub ->
                            System.out.println("ID: " + sub.getId() + ", Nombre: " + sub.getName() + ", Créditos: " + sub.getCredits()));
                        break;
                    case 8:
                        System.out.println("Nombre del profesor:");
                        String teacherName = scanner.nextLine();
                        System.out.println("Departamento:");
                        String department = scanner.nextLine();
                        Teacher teacher = new Teacher(0, teacherName, department);
                        teacherDAO.addTeacher(teacher);
                        break;
                    case 9:
                        teacherDAO.getAllTeachers().forEach(teach ->
                            System.out.println("ID: " + teach.getId() + ", Nombre: " + teach.getName() + ", Departamento: " + teach.getDepartment()));
                        break;
                    case 10:
                        try {
                            System.out.println("ID del estudiante para la inscripción:");
                            int enrollStudentId = scanner.nextInt();
                            System.out.println("ID de la asignatura para la inscripción:");
                            int enrollSubjectId = scanner.nextInt();
                    
                            // Verificar si el estudiante y la asignatura existen antes de la inscripción
                            Student studentToEnroll = studentDAO.getStudent(enrollStudentId);
                            Subject subjectToEnroll = subjectDAO.getSubject(enrollSubjectId);
                    
                            if (studentToEnroll == null) {
                                System.out.println("Error: El estudiante con ID " + enrollStudentId + " no existe.");
                                break;
                            }
                    
                            if (subjectToEnroll == null) {
                                System.out.println("Error: La asignatura con ID " + enrollSubjectId + " no existe.");
                                break;
                            }
                    
                            Enrollment enrollment = new Enrollment(enrollStudentId, enrollSubjectId);
                            enrollmentDAO.addEnrollment(enrollment);
                            System.out.println("Inscripción añadida con éxito.");
                            
                        } catch (SQLException e) {
                            System.err.println("Error en la consulta SQL:");
                            System.err.println("Mensaje de error: " + e.getMessage());
                            System.err.println("Código SQLState: " + e.getSQLState());
                            System.err.println("Código de error: " + e.getErrorCode());
                        }
                        break;
                    case 11:
                        enrollmentDAO.getAllEnrollments().forEach(enr ->
                            System.out.println("Estudiante ID: " + enr.getStudentId() + ", Asignatura ID: " + enr.getSubjectId()));
                        break;
                    case 12:
                        System.out.println("Saliendo...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
        }
    }

    // Método para verificar y crear las tablas si no existen
    private static void checkAndCreateTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        // Verificar la tabla 'students'
        if (!tableExists(conn, "students")) {
            String createStudentsTable = "CREATE TABLE students (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "firstName VARCHAR(100), " +
                    "lastName VARCHAR(100), " +
                    "age INT)";
            stmt.executeUpdate(createStudentsTable);
            // System.out.println("Tabla 'students' creada.");
        } 
        // else {
        //     System.out.println("Tabla 'students' ya existe.");
        // }

        // Verificar la tabla 'subjects'
        if (!tableExists(conn, "subjects")) {
            String createSubjectsTable = "CREATE TABLE subjects (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100), " +
                    "credits INT)";
            stmt.executeUpdate(createSubjectsTable);
            // System.out.println("Tabla 'subjects' creada.");
        } 
        // else {
        //     System.out.println("Tabla 'subjects' ya existe.");
        // }

        // Verificar la tabla 'teachers'
        if (!tableExists(conn, "teachers")) {
            String createTeachersTable = "CREATE TABLE teachers (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100), " +
                    "department VARCHAR(100))";
            stmt.executeUpdate(createTeachersTable);
            // System.out.println("Tabla 'teachers' creada.");
        } 
        // else {
        //     System.out.println("Tabla 'teachers' ya existe.");
        // }

        // Verificar la tabla 'enrollments'
        if (!tableExists(conn, "enrollments")) {
            String createEnrollmentsTable = "CREATE TABLE enrollments (" +
                    "student_id INT, " +
                    "subject_id INT, " +
                    "PRIMARY KEY (student_id, subject_id), " +
                    "FOREIGN KEY (student_id) REFERENCES students(id), " +
                    "FOREIGN KEY (subject_id) REFERENCES subjects(id))";
            stmt.executeUpdate(createEnrollmentsTable);
            // System.out.println("Tabla 'enrollments' creada.");
        }        
        // else {
        //     System.out.println("Tabla 'enrollments' ya existe.");
        // }
    }

    // Método para verificar si una tabla existe
    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        String query = "SHOW TABLES LIKE '" + tableName + "'";
        ResultSet rs = conn.createStatement().executeQuery(query);
        return rs.next();
    }
}
