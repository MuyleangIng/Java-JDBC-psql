package controller;

import model.Student;
import model.StudentDAO;
import view.StudentView;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentController {
    private StudentDAO studentDAO;
    private StudentView studentView;
    private Scanner scanner;

    public StudentController(StudentDAO studentDAO, StudentView studentView, Scanner scanner) {
        this.studentDAO = studentDAO;
        this.studentView = studentView;
        this.scanner = scanner;
    }

    public void run() {
        boolean exit = false;

        while (!exit) {
            studentView.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            try {
                switch (choice) {
                    case 1:
                        listStudentsWithCourses();
                        break;
                    case 2:
                        listStudents();
                        break;
                    case 3:
                        addStudent();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        studentView.displayMessage(StudentManagement.ANSI_RED + "Invalid choice. Please try again." + StudentManagement.ANSI_RESET);
                }
            } catch (SQLException e) {
                studentView.displayMessage(StudentManagement.ANSI_RED + "Error executing SQL query." + StudentManagement.ANSI_RESET);
                e.printStackTrace();
            }
        }
    }

    private void listStudents() throws SQLException {
        List<Student> students = studentDAO.getAllStudents();
        studentView.displayStudents(students);
    }

    private void listStudentsWithCourses() throws SQLException {
        List<Student> studentsWithCourses = studentDAO.getStudentsWithCourses();
        studentView.displayStudents(studentsWithCourses);
    }

    private void deleteStudent() throws SQLException {
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Student ID to delete: " + StudentManagement.ANSI_RESET);
        int studentId = scanner.nextInt();
        scanner.nextLine();

        studentDAO.deleteStudent(studentId);
        studentView.displayMessage(StudentManagement.ANSI_GREEN + "Student deleted successfully." + StudentManagement.ANSI_RESET);
    }

    private void addStudent() throws SQLException {
        System.out.print(StudentManagement.ANSI_GREEN + "Enter First Name: " + StudentManagement.ANSI_RESET);
        String firstName = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Last Name: " + StudentManagement.ANSI_RESET);
        String lastName = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Date of Birth (YYYY-MM-DD): " + StudentManagement.ANSI_RESET);
        String dob = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Gender: " + StudentManagement.ANSI_RESET);
        String gender = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Major: " + StudentManagement.ANSI_RESET);
        String major = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Enrollment Year: " + StudentManagement.ANSI_RESET);
        int enrollmentYear = scanner.nextInt();
        scanner.nextLine();  // Consume newline character
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Email: " + StudentManagement.ANSI_RESET);
        String email = scanner.nextLine();

        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setDateOfBirth(Date.valueOf(dob));
        student.setGender(gender);
        student.setMajor(major);
        student.setEnrollmentYear(enrollmentYear);
        student.setEmail(email);

        studentDAO.addStudent(student);
        studentView.displayMessage(StudentManagement.ANSI_YELLOW + "Student added successfully." + StudentManagement.ANSI_RESET);
    }

    private void updateStudent() throws SQLException {
        System.out.print(StudentManagement.ANSI_GREEN + "Enter Student ID to update: " + StudentManagement.ANSI_RESET);
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter New First Name: " + StudentManagement.ANSI_RESET);
        String newFirstName = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter New Last Name: " + StudentManagement.ANSI_RESET);
        String newLastName = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter New Date of Birth (YYYY-MM-DD): " + StudentManagement.ANSI_RESET);
        String newDob = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter New Gender: " + StudentManagement.ANSI_RESET);
        String newGender = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter New Major: " + StudentManagement.ANSI_RESET);
        String newMajor = scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter New Enrollment Year: " + StudentManagement.ANSI_RESET);
        int newEnrollmentYear = scanner.nextInt();
        scanner.nextLine();
        System.out.print(StudentManagement.ANSI_GREEN + "Enter New Email: " + StudentManagement.ANSI_RESET);
        String newEmail = scanner.nextLine();

        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(newFirstName);
        student.setLastName(newLastName);
        student.setDateOfBirth(Date.valueOf(newDob));
        student.setGender(newGender);
        student.setMajor(newMajor);
        student.setEnrollmentYear(newEnrollmentYear);
        student.setEmail(newEmail);

        studentDAO.updateStudent(student);
        studentView.displayMessage(StudentManagement.ANSI_YELLOW + "Student updated successfully." + StudentManagement.ANSI_RESET);
    }
}