package model;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sqlQuery = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setDateOfBirth(resultSet.getDate("date_of_birth"));
                student.setGender(resultSet.getString("gender"));
                student.setMajor(resultSet.getString("major"));
                student.setEnrollmentYear(resultSet.getInt("enrollment_year"));
                student.setEmail(resultSet.getString("email"));
                students.add(student);
            }
        }
        return students;
    }

    public void addStudent(Student student) throws SQLException {
        String sqlInsert = "INSERT INTO students (first_name, last_name, date_of_birth, gender, major, enrollment_year, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, student.getDateOfBirth());
            preparedStatement.setString(4, student.getGender());
            preparedStatement.setString(5, student.getMajor());
            preparedStatement.setInt(6, student.getEnrollmentYear());
            preparedStatement.setString(7, student.getEmail());
            preparedStatement.executeUpdate();
        }
    }

    public void updateStudent(Student student) throws SQLException {
        String sqlUpdate = "UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, major = ?, enrollment_year = ?, email = ? WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, student.getDateOfBirth());
            preparedStatement.setString(4, student.getGender());
            preparedStatement.setString(5, student.getMajor());
            preparedStatement.setInt(6, student.getEnrollmentYear());
            preparedStatement.setString(7, student.getEmail());
            preparedStatement.setInt(8, student.getStudentId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteStudent(int studentId) throws SQLException {
        String sqlDelete = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        }
    }

    public List<Student> getStudentsWithCourses() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sqlQuery = "SELECT students.student_id, students.first_name, students.last_name, courses.course_id, courses.course_name, courses.instructor FROM students JOIN student_course_relationship ON students.student_id = student_course_relationship.student_id JOIN courses ON student_course_relationship.course_id = courses.course_id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                students.add(student);
            }
        }
        return students;
    }
}
