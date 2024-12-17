
package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

import java.util.List;

public class BorrowService {

    private BorrowDAO borrowDAO;

    // Constructeur avec BorrowDAO
    public BorrowService(BorrowDAO borrowDAO) {
        this.borrowDAO = borrowDAO;
    }

    // Méthode pour emprunter un livre
    public void borrowBook(Borrow borrow) {
        // Sauvegarde de l'emprunt dans la base de données
        borrowDAO.save(borrow);
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
