package com.example.jpastudent;

import java.util.List;

public interface StudentDAO {

    void insertStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Long studentId);
    Student getStudent(Long studentId);
    List<Student> getAllStudents(int pageNumber, int pageSize);
}
