package main.java;

import main.java.dao.StudentDAOImpl;
import main.java.models.Student;
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

            // Iniciar el DAO y el menú
            StudentDAOImpl studentDAO = new StudentDAOImpl();
            Scanner scanner = new Scanner(System.in);

            // Menú simple
            while (true) {
                System.out.println("1. Añadir estudiante");
                System.out.println("2. Ver estudiante");
                System.out.println("3. Ver todos los estudiantes");
                System.out.println("4. Actualizar estudiante");
                System.out.println("5. Eliminar estudiante");
                System.out.println("6. Salir");
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

    // Método para verificar y crear la tabla 'students' si no existe
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
            System.out.println("Tabla 'students' creada.");
        } else {
            System.out.println("Tabla 'students' ya existe.");
        }
    }

    // Método para verificar si una tabla existe
    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        String query = "SHOW TABLES LIKE '" + tableName + "'";
        ResultSet rs = conn.createStatement().executeQuery(query);
        return rs.next();
    }
}
