package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.util.DbConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class BookServiceTest {
    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        bookService = new BookService(bookDAO);
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
        } catch ( SQLException e) {
            System.err.println("Error clearing database: " + e.getMessage());
        }
    }


    @Test
    @Order(1)
    void testAddBook() {
        Book book = new Book(1, "Java Programming", "John Doe", true);
        bookService.addBook(book);
        assertEquals(1, bookDAO.getAllBooks().size());
        assertEquals("Java Programming", bookDAO.getBookById(1).getTitle());
    }
    @Test
    @Order(2)
    void testUpdateBook() {
        Book book = new Book(1, "Advanced Java", "John Doe", true);
        bookService.updateBook(book);
        assertEquals("Advanced Java", bookDAO.getBookById(1).getTitle());
    }
    @Test
    @Order(3)
    void testDeleteBook() {
        bookService.deleteBook("1");
        assertNull(bookDAO.getBookById(1));
    }
}
