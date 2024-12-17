package com.library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String isbn;
    private boolean available;

    // Constructeur par défaut
    public Book() {
    }

    public Book(int id, String title, String author, String publisher, int year, String isbn, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.isbn = isbn;
        this.available = available;
    }

    // Constructeur 2
    public Book(String title, String author,int year) {
        this.id = 0;
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = true;
    }
    public Book(int id,String title, String author,boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available= available;
    }


    // Constructeur additionnel si nécessaire
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", isbn='" + isbn + '\'' +
                ", available=" + available +
                '}';
    }
}
