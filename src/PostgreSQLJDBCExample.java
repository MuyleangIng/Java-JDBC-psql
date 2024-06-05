import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLJDBCExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of the PostgreSQL database
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "080720";

        // Load the PostgreSQL JDBC driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            return;
        }

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the PostgreSQL database.");
            System.out.println("Perform database operations here.");
            // Perform database operations here

        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }
}
