package com.library;

import com.library.dao.StudentDAO;
import com.library.service.BorrowService;
import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.dao.BorrowDAO;  // Importer BorrowDAO

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Student student = new Student(1, "John Doe");
        Book book = new Book("Effective Java", "Joshua Bloch", "123456", 2017);
        Borrow borrow = new Borrow(1, student, book, new Date(), new Date());

        // Connexion à la base de données
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "password");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            System.exit(1);
        }

        // Création des services
        BookService bookService = new BookService();
        StudentDAO studentDAO = new StudentDAO(connection);
        StudentService studentService = new StudentService(studentDAO);
        BorrowDAO borrowDAO = new BorrowDAO();  // Création de BorrowDAO
        BorrowService borrowService = new BorrowService(borrowDAO);  // Passer BorrowDAO au constructeur de BorrowService

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
                    Book book1 = new Book(title, author);
                    bookService.addBook(book1);
                    break;

                case 2:
                    bookService.displayBooks();
                    break;

                case 3:
                    System.out.print("Entrez l'ID de l'étudiant: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Entrez le nom de l'étudiant: ");
                    String studentName = scanner.nextLine();
                    Student student1 = new Student(studentId, studentName);
                    studentService.addStudent(student1);
                    break;

                case 4:
                    System.out.println("Liste des étudiants:");
                    studentService.displayStudents();
                    break;

                case 5:
                    System.out.print("Entrez l'ID de l'étudiant: ");
                    int borrowStudentId = scanner.nextInt();
                    System.out.print("Entrez l'ID du livre: ");
                    int borrowBookId = scanner.nextInt();
                    Student studentForBorrow = studentService.findStudentById(borrowStudentId);
                    Book bookForBorrow = bookService.findBookById(borrowBookId);
                    if (studentForBorrow != null && bookForBorrow != null) {
                        // Créer un objet Borrow avec les informations nécessaires
                        Borrow borrow1 = new Borrow(
                                0,
                                studentForBorrow,
                                bookForBorrow,
                                new Date(),  // Date d'emprunt actuelle
                                null         // Date de retour pas encore définie
                        );
                        borrowService.borrowBook(borrow1);  // Appel de la méthode avec l'objet Borrow
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
