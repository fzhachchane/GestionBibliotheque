package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    //public StudentService() {
    //    this.studentDAO = new StudentDAO();
    //}

    // Constructeur
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    // Ajouter un étudiant
    public void addStudent(Student student) {
        try {
            studentDAO.addStudent(student);
            System.out.println("Étudiant ajouté avec succès: " + student.getName());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'étudiant: " + e.getMessage());
        }
    }

    // Afficher tous les étudiants
    public void displayStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            if (students.isEmpty()) {
                System.out.println("Aucun étudiant trouvé.");
            } else {
                System.out.println("Liste des étudiants:");
                for (Student student : students) {
                    System.out.println("ID: " + student.getId() + " | Nom: " + student.getName());
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'affichage des étudiants: " + e.getMessage());
        }
    }

    // Trouver un étudiant par ID
    public Student findStudentById(int id) {
        try {
            Student student = studentDAO.getStudentById(id);
            if (student != null) {
                System.out.println("Étudiant trouvé: ID=" + student.getId() + ", Nom=" + student.getName());
                return student;
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche de l'étudiant: " + e.getMessage());
        }
        return null;
    }
}
