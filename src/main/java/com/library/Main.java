package com.library;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.service.BorrowService;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.dao.BorrowDAO;  // Importer BorrowDAO
import com.library.util.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException, SQLException {
        Scanner scanner = new Scanner(System.in);

        // Connexion à la base de données
        Connection connection = DbConnection.getConnection();

        BookDAO bookDAO = new BookDAO();
        StudentDAO studentDAO = new StudentDAO();
        BookService bookService = new BookService(bookDAO);
        StudentService studentService = new StudentService(studentDAO);
        BorrowDAO borrowDAO = new BorrowDAO(studentDAO,bookDAO);
        BorrowService borrowService = new BorrowService(borrowDAO,bookDAO,studentDAO);


        boolean running = true;

        while (running) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Afficher les livres");
            System.out.println("3. Ajouter un étudiant");
            System.out.println("4. Afficher les étudiants");
            System.out.println("5. Emprunter un livre");
            System.out.println("6. Afficher les emprunts");
            System.out.println("7. Quitter");

            System.out.print("Choisir une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consommer la ligne restante après l'entier

            switch (choice) {
                case 1:
                    System.out.print("Entrez le titre du livre: ");
                    String title = scanner.nextLine();
                    System.out.print("Entrez l'auteur du livre: ");
                    String author = scanner.nextLine();
                    System.out.print("Entrez l'année de publication: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    Book newBook = new Book(title, author, year);
                    bookService.addBook(newBook);
                    break;

                case 2:
                    bookService.getAllBooks();
                    break;

                case 3:
                    System.out.print("Entrez le nom de l'étudiant: ");
                    String studentName = scanner.nextLine();
                    Student newStudent = new Student(studentName);
                    studentService.addStudent(newStudent);
                    break;

                case 4:
                    System.out.println("Liste des étudiants:");
                    studentService.getAllStudents();
                    break;

                case 5:
                    System.out.print("Entrez le nom de l'étudiant: ");
                    String studentNameForBorrow = scanner.nextLine();
                    System.out.print("Entrez le titre du livre: ");
                    String bookTitleForBorrow = scanner.nextLine();

                    System.out.print("Entrez la date de retour (jj/mm/aaaa): ");
                    String returnDateStr = scanner.nextLine();
                    scanner.nextLine();

                    Student studentForBorrow = studentService.findStudentByName(studentNameForBorrow);
                    Book bookForBorrow = bookService.findBookByTitle(bookTitleForBorrow);

                    Date returnDate = null;

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    returnDate = sdf.parse(returnDateStr);

                    if (studentForBorrow != null && bookForBorrow != null) {
                        Borrow borrow1 = new Borrow(studentForBorrow, bookForBorrow, new Date(), returnDate);
                        borrowService.addBorrow(borrow1);

                    } else {
                        System.out.println("Étudiant ou livre introuvable.");
                    }
                    break;

                case 6:
                    borrowService.displayBorrows();
                    break;

                case 7:
                    running = false;
                    System.out.println("Au revoir!");
                    break;

                default:
                    System.out.println("Option invalide.");
            }
        }

        scanner.close();
    }
}
