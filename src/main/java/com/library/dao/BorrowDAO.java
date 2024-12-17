
package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {
    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("member_id"),
                        rs.getString("member_name")
                );
                Book book = new Book(
                        rs.getString("book_id"),
                        rs.getString("book_title")
                );
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //public void addBorrow(Borrow borrow) {
    //    String query = "INSERT INTO borrows (member, book, borrow_date, return_date) VALUES (?, ?, ?, ?)";
    //    try (Connection connection = DbConnection.getConnection();
    //         PreparedStatement stmt = connection.prepareStatement(query)) {
    //        stmt.setString(1, borrow.getStudent());
    //        stmt.setString(2, borrow.getBook());
    //        stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
    //        stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
    //        stmt.executeUpdate();
    //    } catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    //}
}
