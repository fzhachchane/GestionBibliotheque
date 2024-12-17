package com.library.dao;

import com.library.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.library.util.DbConnection;

public class StudentDAO {
    public void addStudent(Student student) {
        String sql = "INSERT INTO Student (name) VALUES (?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.executeUpdate();
            System.out.println("Student ajouté avec succes!");
        } catch (SQLException e) {
            System.err.println("Erreur dans l'ajout de student: " + e.getMessage());
        }
    }
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Student WHERE id = ?";
        Student student = null;
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Student by ID: " + e.getMessage());
        }
        return student;
    }
    public List<Student> getAllStudents() {
        List<Student> Student = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
                Student.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving Student: " + e.getMessage());
        }
        return Student;
    }
    public Student findStudentByName(String name) {
        Student student = null;
        String query = "SELECT * FROM Student WHERE name = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String studentName = resultSet.getString("name");
                student = new Student(id, studentName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
    public void updateStudent(Student student) {
        String sql = "UPDATE Student SET name = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM Student WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
    public boolean isStudentExists(int studentId) {
        String query = "SELECT COUNT(*) FROM Student WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
