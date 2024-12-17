
package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {
    private final StudentDAO studentDAO;
    private final BookDAO bookDAO;

    public BorrowDAO(StudentDAO studentDAO, BookDAO bookDAO) {
        this.studentDAO = studentDAO;
        this.bookDAO = bookDAO;
    }

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM Borrow";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                int bookId = rs.getInt("book_id");

                Student student = studentDAO.getStudentById(studentId);
                Book book = bookDAO.getBookById(bookId);

                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        student,
                        book,
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date")
                );
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    public void save(Borrow borrow) {
        String query =
                "INSERT INTO borrows (member_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
            System.out.println("Livre emprunté avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBorrow(Borrow borrow) {
        String borrowQuery = "INSERT INTO Borrow (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        String updateBookQuery = "UPDATE Book SET available = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement borrowStmt = connection.prepareStatement(borrowQuery);
             PreparedStatement updateBookStmt = connection.prepareStatement(updateBookQuery)) {

            // Insert into Borrow table
            borrowStmt.setInt(1, borrow.getStudent().getId());
            borrowStmt.setInt(2, borrow.getBook().getId());
            borrowStmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            borrowStmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            borrowStmt.executeUpdate();

            // Update Book availability to false
            updateBookStmt.setBoolean(1, false);
            updateBookStmt.setInt(2, borrow.getBook().getId());
            updateBookStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int studentId, int bookId) {
        String query = "DELETE FROM Borrow WHERE student_id = ? AND book_id = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
