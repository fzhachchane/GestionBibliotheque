package com.library.service;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;
    private List<Student> students;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
        this.students = studentDAO.getAllStudents();
    }
    public void addStudent(Student student) {
        studentDAO.addStudent(student);
    }
    public void addStudentWithId(Student student) {
        studentDAO.addStudentWithId(student);
    }

    public Student getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }
    public Student findStudentByName(String name) {
        return studentDAO.findStudentByName(name);
    }
    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }
    public void deleteStudent(int studentId) {
        studentDAO.deleteStudent(studentId);
    }
    public void getAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Aucun livre disponible.");
        } else {
            for (Student student : students) {
                System.out.println("Nom: " + student.getName());
            }
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
}
