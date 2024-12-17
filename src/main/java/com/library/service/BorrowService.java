
package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

import java.util.List;

public class BorrowService {
    private final BorrowDAO borrowDAO;
    private  BookDAO bookDAO;
    private StudentDAO studentDAO;

    public BorrowService(BorrowDAO borrowDAO, BookDAO bookDAO, StudentDAO studentDAO) {
        this.borrowDAO = borrowDAO;
        this.bookDAO=bookDAO;
        this.studentDAO=studentDAO;
    }

    // Méthode pour emprunter un livre
    public String addBorrow(Borrow borrow) {
        if (borrow.getStudent() == null || borrow.getBook() == null) {
            return "Étudiant ou livre non trouvé.";
        }

        Book book = bookDAO.getBookById(borrow.getBook().getId());
        if (book == null) {
            return "Livre introuvable.";
        }

        try {
            if (!book.isAvailable()) {
                return "Le livre n'est pas disponible.";
            }
            // Ajout de l'emprunt
            borrowDAO.addBorrow(borrow);
            bookDAO.updateBookAvailability(book.getId(), false);
            return "Livre emprunté avec succès!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Une erreur s'est produite lors de l'emprunt du livre.";  // Ajout d'un retour de message en cas d'exception
        }
    }
    public String returnBook(int studentId, int bookId) {
        bookDAO.updateBookAvailability(bookId, true);
        borrowDAO.returnBook(studentId, bookId);
        return "Livre retourné avec succès!";
    }

    public List<Borrow> getAllBorrows() {
        return borrowDAO.getAllBorrows();
    }

    // Afficher les emprunts (méthode fictive, à adapter)
    public void displayBorrows() {
        List<Borrow> borrows = borrowDAO.getAllBorrows();
        System.out.println("List of Borrows:");
        for (Borrow borrow : borrows) {
            System.out.println("ID: " + borrow.getId() +
                    ", Student: " + borrow.getStudent().getName() +
                    ", Book: " + borrow.getBook().getTitle() +
                    ", Borrow Date: " + borrow.getBorrowDate() +
                    ", Return Date: " + borrow.getReturnDate());
        }
    }
}
