import java.sql.*;
import java.text.SimpleDateFormat;

public class RetrieveDataExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=prod";
        String user = "postgres";
        String password = "080720";

        String sqlQuery = "SELECT * FROM students";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String studentName = resultSet.getString("first_name");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                String email = resultSet.getString("email");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDateOfBirth = dateFormat.format(dateOfBirth);                // Display the formatted output in console with labels and style
                System.out.println("Student Information");
                System.out.println("------------------");
                System.out.println("Student ID:      " + studentId);
                System.out.println("Student Name:    " + studentName);
                System.out.println("Date of Birth:   " + formattedDateOfBirth);
                System.out.println("Email:           " + email);
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query.");
        }
    }
}
