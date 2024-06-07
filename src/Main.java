import controller.StudentController;
import model.StudentDAO;
import view.StudentView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static controller.StudentManagement.ANSI_RED;
import static controller.StudentManagement.ANSI_RESET;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "080720";

        if (url == null || user == null || password == null) {
            System.out.println("Please set the environment variables DB_URL, DB_USER, and DB_PASSWORD.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            StudentDAO studentDAO = new StudentDAO(connection);
            StudentView studentView = new StudentView();
            StudentController studentController = new StudentController(studentDAO, studentView, scanner);

            studentController.run();

        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error connecting to the database." + ANSI_RESET);
            e.printStackTrace();
        }
    }
}
