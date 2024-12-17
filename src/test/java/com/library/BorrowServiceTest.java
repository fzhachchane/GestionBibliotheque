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

        bookDAO.addWithId(new Book(1, "Java Programming", "John Doe", true));
        bookDAO.addWithId(new Book(2, "Advanced Java", "Jane Doe", true));

        studentDAO.addStudentWithId(new Student(1, "Alice"));
        studentDAO.addStudentWithId(new Student(2, "Bob"));

    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test teardown: Clearing database...");
        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            // Disable foreign key checks (if needed)
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");

            // Truncate all tables
            statement.execute("TRUNCATE TABLE Borrow");
            statement.execute("TRUNCATE TABLE books");
            statement.execute("TRUNCATE TABLE Student");

            // Re-enable foreign key checks
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("Database cleared successfully!");
        } catch (SQLException e) {
            System.err.println("Error clearing database: " + e.getMessage());
        }
    }

    @Test
    void testBorrowBook() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date borrowedDate = new Date();
        Date returnedDate = dateFormat.parse("20/12/2024");

        Student student = studentservice.getStudentById(1);
        Book book = bookservice.findBookById(1);

        Borrow borrow = new Borrow(student,book,borrowedDate,returnedDate);
        String result = borrowService.addBorrow(borrow);

        BookService bookservice2 = new BookService(bookDAO);
        Book updatedBook = bookservice2.findBookById(1);
        //assertEquals("Livre emprunté avec succès!", result);
        assertFalse(bookservice2.findBookById(1).isAvailable());
    }

    @Test
    void testReturnBook() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date borrowedDate = new Date();
        Date returnedDate = dateFormat.parse("20/12/2024");

        Student student = studentservice.getStudentById(1);
        Book book = bookservice.findBookById(1);

        Borrow borrow = new Borrow(student,book,borrowedDate,returnedDate);
        String result = borrowService.addBorrow(borrow);
        Book updatedBook = bookservice.findBookById(1);
        System.out.println(updatedBook);
        assertFalse(updatedBook.isAvailable());

        BorrowService borrowService2 = new BorrowService(borrowDAO, bookDAO, studentDAO);
        String result2 = borrowService2.returnBook(1, 1);
        Book updatedBook1 = bookservice.findBookById(1);
        System.out.println("Freshly queried book availability: " + updatedBook1.isAvailable());

        assertTrue(updatedBook1.isAvailable());
    }

    @Test
    void testBorrowBookNotAvailable() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date returnedDate = dateFormat.parse("20/12/2024");

        Student student = studentservice.getStudentById(1);
        Book book = bookservice.findBookById(1);

        Borrow borrow = new Borrow(student,book,new Date(),returnedDate);
        String result = borrowService.addBorrow(borrow);
        Book updatedBook = bookDAO.getBookById(1);
        System.out.println(updatedBook);
        //assertEquals("Le livre n'est pas disponible.", result);
      assertFalse(updatedBook.isAvailable());
    }

    @Test
    void testBorrowBookStudentNotFound() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date returnedDate = dateFormat.parse("20/12/2024");
        Borrow borrow = new Borrow(studentservice.getStudentById(5),bookservice.findBookById(1),new Date(),returnedDate);
        String result = borrowService.addBorrow(borrow);
        assertEquals("Étudiant ou livre non trouvé.", result);
    }
}