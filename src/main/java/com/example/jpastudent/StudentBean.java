package com.example.jpastudent;

import jakarta.annotation.*;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;


@SessionScoped
public class StudentBean implements Serializable {
    
    private Student student;
    private List<Student> students;
    private StudentDAO studentDAO;
    private static final int PAGE_SIZE = 10;
    private int currentPage = 1;
    
    @PostConstruct
    public void init() {
        student = new Student();
        studentDAO = new StudentDAOImpl();
        loadStudents();
    }

    public StudentBean() {
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public List<Student> getStudents() {
        return students;
    }
    
    public String addStudent() {
        try {
            studentDAO.insertStudent(student);
            System.out.println(student);
            loadStudents();
            return "listStudents?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String updateStudent() {
        try {
            studentDAO.updateStudent(student);
            student = new Student(); // Reset
            loadStudents();
            return "listStudents?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String deleteStudent(long id) {
        try {
            studentDAO.deleteStudent(id);
            loadStudents();
            return "listStudents?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void loadStudents() {
        students = studentDAO.getAllStudents(currentPage, PAGE_SIZE);
    }
    
    // Pagination methods
    public void nextPage() {
        currentPage++;
        loadStudents();
    }
    
    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            loadStudents();
        }
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
}
