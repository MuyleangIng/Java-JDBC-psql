import java.sql.*;
import java.util.Scanner;

public class StudentManagement {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "080720";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            boolean exit = false;

            while (!exit) {
                System.out.println(ANSI_CYAN + "\nMenu:");
                System.out.println("1. filter students with courses");
                System.out.println(ANSI_YELLOW + "2. List Students");
                System.out.println("3. Add Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println(ANSI_RED + "6. Exit" + ANSI_RESET);
                System.out.print(ANSI_GREEN + "Enter your choice: " + ANSI_RESET);
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline character

                switch (choice) {
                    case 1:
                        studentWithCourse(connection);
                        break;
                    case 2:
                        listStudents(connection);
                        break;
                    case 3:
                        addStudent(connection, scanner);
                        break;
                    case 4:
                        updateStudent(connection, scanner);
                        break;
                    case 5:
                        deleteStudent(connection, scanner);
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid choice. Please try again." + ANSI_RESET);
                }
            }
        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error executing SQL query." + ANSI_RESET);
            e.printStackTrace();
        }
    }

    private static void studentWithCourse(Connection connection) throws SQLException {
        String sqlQuery = "SELECT students.student_id, " +
                "students.first_name, " +
                "students.last_name, " +
                "courses.course_id, " +
                "courses.course_name, " +
                "courses.instructor " +
                "FROM students " +
                "JOIN student_course_relationship ON students.student_id = student_course_relationship.student_id " +
                "JOIN courses ON student_course_relationship.course_id = courses.course_id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            System.out.println(ANSI_CYAN + "\nList of Students with Courses:" + ANSI_RESET);

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                String instructor = resultSet.getString("instructor");
                System.out.println(ANSI_YELLOW + "Student ID: " + studentId +
                        ", Name: " + firstName + " " + lastName +
                        ", Course ID: " + courseId +
                        ", Course Name: " + courseName +
                        ", Instructor: " + instructor + ANSI_RESET);

            }
        }
    }

    private static void listStudents(Connection connection) throws SQLException {
        String sqlQuery = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            System.out.println(ANSI_CYAN + "\nList of Students:" + ANSI_RESET);

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date dob = resultSet.getDate("date_of_birth");
                String gender = resultSet.getString("gender");
                String major = resultSet.getString("major");
                int enrollmentYear = resultSet.getInt("enrollment_year");
                String email = resultSet.getString("email");

                System.out.println(ANSI_YELLOW + "Student ID: " + studentId +
                        ", Name: " + firstName + " " + lastName +
                        ", DOB: " + dob +
                        ", Gender: " + gender +
                        ", Major: " + major +
                        ", Enrollment Year: " + enrollmentYear +
                        ", Email: " + email + ANSI_RESET);
            }
        }
    }

    private static void addStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print(ANSI_GREEN + "Enter First Name: " + ANSI_RESET);
        String firstName = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter Last Name: " + ANSI_RESET);
        String lastName = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter Date of Birth (YYYY-MM-DD): " + ANSI_RESET);
        String dob = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter Gender: " + ANSI_RESET);
        String gender = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter Major: " + ANSI_RESET);
        String major = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter Enrollment Year: " + ANSI_RESET);
        int enrollmentYear = scanner.nextInt();
        scanner.nextLine();  // Consume newline character
        System.out.print(ANSI_GREEN + "Enter Email: " + ANSI_RESET);
        String email = scanner.nextLine();

        String sqlInsert = "INSERT INTO students (first_name, last_name, date_of_birth, gender, major, enrollment_year, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, Date.valueOf(dob));
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, major);
            preparedStatement.setInt(6, enrollmentYear);
            preparedStatement.setString(7, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(ANSI_YELLOW + "Student added successfully." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Failed to add student." + ANSI_RESET);
            }
        }
    }

    private static void updateStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print(ANSI_GREEN + "Enter Student ID to update: " + ANSI_RESET);
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter New First Name: " + ANSI_RESET);
        String newFirstName = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter New Last Name: " + ANSI_RESET);
        String newLastName = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter New Date of Birth (YYYY-MM-DD): " + ANSI_RESET);
        String newDob = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter New Gender: " + ANSI_RESET);
        String newGender = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter New Major: " + ANSI_RESET);
        String newMajor = scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter New Enrollment Year: " + ANSI_RESET);
        int newEnrollmentYear = scanner.nextInt();
        scanner.nextLine();
        System.out.print(ANSI_GREEN + "Enter New Email: " + ANSI_RESET);
        String newEmail = scanner.nextLine();

        String sqlUpdate = "UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, major = ?, enrollment_year = ?, email = ? WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, newFirstName);
            preparedStatement.setString(2, newLastName);
            preparedStatement.setDate(3, Date.valueOf(newDob));
            preparedStatement.setString(4, newGender);
            preparedStatement.setString(5, newMajor);
            preparedStatement.setInt(6, newEnrollmentYear);
            preparedStatement.setString(7, newEmail);
            preparedStatement.setInt(8, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(ANSI_GREEN + "Student updated successfully." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Failed to update student." + ANSI_RESET);
            }
        }
    }

    private static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print(ANSI_GREEN + "Enter Student ID to delete: " + ANSI_RESET);
        int studentId = scanner.nextInt();
        scanner.nextLine();

        String sqlDelete = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, studentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(ANSI_GREEN + "Student deleted successfully." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Failed to delete student." + ANSI_RESET);
            }
        }
    }
}
