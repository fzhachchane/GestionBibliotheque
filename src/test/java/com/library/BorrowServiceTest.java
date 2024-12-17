package com.library;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;
import com.library.util.DbConnection;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;


@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class BorrowServiceTest {
    static BorrowService borrowService;
    static BookDAO bookDAO;
    static StudentDAO studentDAO;
    static BorrowDAO borrowDAO;
    static StudentService studentservice;
    static BookService bookservice;

    @BeforeAll
    static void setUp() {
        bookDAO = new BookDAO();
        studentDAO = new StudentDAO();
        borrowDAO = new BorrowDAO(studentDAO,bookDAO);
        borrowService = new BorrowService(borrowDAO,bookDAO,studentDAO);
        studentservice=new StudentService(studentDAO);
        bookservice = new BookService(bookDAO);

        bookDAO.add(new Book(1, "Java Programming", "John Doe", true));
        bookDAO.add(new Book(2, "Advanced Java", "Jane Doe", true));

        studentDAO.addStudent(new Student(1, "Alice"));
        studentDAO.addStudent(new Student(2, "Bob"));

    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Test teardown: Clearing database...");
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            // Disable foreign key checks (if needed)
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");

            // Truncate all tables
            statement.execute("TRUNCATE TABLE Borrow");
            statement.execute("TRUNCATE TABLE Book");
            statement.execute("TRUNCATE TABLE Student");

            // Re-enable foreign key checks
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("Database cleared successfully!");
        } catch (SQLException e) {
            System.err.println("Error clearing database: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void testBorrowBook() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date borrowedDate = new Date();
        Date returnedDate = dateFormat.parse("20/12/2024");
        Student student = studentservice.getStudentById(1);
        Book book = bookservice.findBookById(1);

        Borrow borrow = new Borrow(student,book,borrowedDate,returnedDate);
        String result = borrowService.addBorrow(borrow);
        assertEquals("Livre emprunté avec succès!", result);

        assertFalse(bookDAO.getBookById(1).isAvailable());
    }

    @Test
    @Order(2)
    void testReturnBook() {
        String result = borrowService.returnBook(1, 1);
        assertEquals("Livre retourné avec succès!", result);
        assertTrue(bookDAO.getBookById(1).isAvailable());
    }

    @Test
    @Order(3)
    void testBorrowBookNotAvailable() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date returnedDate = dateFormat.parse("20/12/2024");
        Student student = studentservice.getStudentById(1);
        Book book = bookservice.findBookById(1);
        Borrow borrow = new Borrow(student,book,new Date(),returnedDate);
        borrowService.addBorrow(borrow);
        String result = borrowService.addBorrow(borrow);
        assertEquals("Le livre n'est pas disponible.", result);
        assertFalse(bookDAO.getBookById(1).isAvailable());
    }

    @Test
    @Order(4)
    void testBorrowBookStudentNotFound() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date returnedDate = dateFormat.parse("20/12/2024");
        Borrow borrow = new Borrow(studentservice.getStudentById(5),bookservice.findBookById(1),new Date(),returnedDate);
        String result = borrowService.addBorrow(borrow);
        assertEquals("Étudiant ou livre non trouvé.", result);
    }
}