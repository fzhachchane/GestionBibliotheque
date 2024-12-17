package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import com.library.util.DbConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class StudentServiceTest {
    private StudentService studentService;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        studentDAO = new StudentDAO();
        studentService = new StudentService(studentDAO);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test teardown: Clearing database...");
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("TRUNCATE TABLE Borrow");
            statement.execute("TRUNCATE TABLE books");
            statement.execute("TRUNCATE TABLE Student");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
            System.out.println("Database cleared successfully!");
        } catch (SQLException e) {
            System.err.println("Error clearing database: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testAddStudent() {
        Student student = new Student(3, "new name");
        studentService.addStudentWithId(student);
        assertEquals(1, studentDAO.getAllStudents().size());
        assertEquals("new name", studentDAO.getStudentById(3).getName());
    }

    @Test
    @Order(2)
    void testUpdateStudent() {
        Student student = new Student(3, "new name");
        Student studentupdated = new Student(3, "new name updated");
        studentService.addStudentWithId(student);
        studentService.updateStudent(studentupdated);
        assertEquals("new name updated", studentDAO.getStudentById(3).getName());
    }

    @Test
    @Order(3)
    void testDeleteStudent() {
        Student student = new Student(3, "new name");
        studentService.addStudentWithId(student);
        studentService.deleteStudent(3);
        assertNull(studentDAO.getStudentById(3));
    }

    @Test
    @Order(4)
    void testGetAllStudents() {
        Student student1 = new Student(2, "Alice");
        Student student2 = new Student(3, "Bob");
        studentService.addStudentWithId(student1);
        studentService.addStudentWithId(student2);
        assertEquals(2, (long) studentDAO.getAllStudents().stream().count());
    }
}