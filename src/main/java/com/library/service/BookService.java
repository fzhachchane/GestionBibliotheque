package com.library.service;
import com.library.dao.BookDAO; // Importation de BookDAO
import com.library.model.Book;   // Importation de Book
import java.util.List;


public class BookService {
    private BookDAO bookDAO;  // Utilisation de DAO pour la gestion des livres
    private List<Book> books;

    // Constructeur qui initialise l'objet BookDAO
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
        this.books= bookDAO.getAllBooks();
    }

    // Ajouter un livre
    public void addBook(Book book) {
        bookDAO.addWithId(book);
    }

    // Afficher tous les livres
    public void getAllBooks() {
        if (books.isEmpty()) {
            System.out.println("Aucun livre disponible.");
        } else {
            for (Book book : books) {
                System.out.println("Titre: " + book.getTitle() + ", Auteur: " + book.getAuthor());
            }
        }
    }

    // Trouver un livre par ID
    public Book findBookById(int id) {
        return bookDAO.getBookById(id);
    }
    // Trouver un livre par isbn
    public Book findBookByIsbn(String isbn) {
        return bookDAO.getBookByIsbn(isbn);
    }
    // Trouver un livre par titre
    public Book findBookByTitle(String title) {
        return bookDAO.getBookByTitle(title);
    }

    // Supprimer un livre par isbn
    public void deleteBook(String isbn) {
        bookDAO.delete(isbn);
    }

    // Mise à jour des informations d'un livre
    public void updateBook(Book book) {
        bookDAO.update(book);
    }
}
