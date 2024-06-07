package view;

import model.Student;

import java.util.List;
import controller.StudentManagement;
public class StudentView {
    public void displayStudents(List<Student> students) {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void displayMenu() {
        System.out.println(StudentManagement.ANSI_CYAN + "\nMenu:");
        System.out.println("1. Filter students with courses");
        System.out.println(StudentManagement.ANSI_YELLOW + "2. List Students");
        System.out.println("3. Add Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println(StudentManagement.ANSI_RED + "6. Exit" + StudentManagement.ANSI_RESET);
        System.out.print(StudentManagement.ANSI_GREEN + "Enter your choice: " + StudentManagement.ANSI_RESET);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
