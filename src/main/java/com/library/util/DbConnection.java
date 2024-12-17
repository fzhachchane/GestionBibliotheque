package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:mysql://mysql-322ebb87-project22-2002.a.aivencloud.com:28624/defaultdb";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_6vNVi-FHxsnq8b3bcIP";

    public static Connection getConnection() throws SQLException {
        try {
            // Charger le driver MySQL
            Class.forName("java.sql.Driver");
            // Retourner la connexion avec les paramètres de connexion
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // connection.setAutoCommit(false);
            connection.setAutoCommit(true);
            return connection;
        } catch (ClassNotFoundException e) {
            // Si le driver est introuvable, cela lance une exception spécifique
            throw new SQLException("MySQL JDBC driver not found.", e);
        } catch (SQLException e) {
            // Si une erreur SQL se produit, l'exception est lancée
            throw new SQLException("Database connection error: " + e.getMessage(), e);
        }
    }
}
