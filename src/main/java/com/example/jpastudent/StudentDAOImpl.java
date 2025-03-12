package com.example.jpastudent;

import jakarta.persistence.*;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Student");

    @Override
    public void insertStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void updateStudent(Student student) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteStudent(Long studentId) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Student student = em.find(Student.class, studentId);
            em.remove(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

    @Override
    public Student getStudent(Long studentId) {
        EntityManager em = emf.createEntityManager();
        Student student = em.find(Student.class, studentId);
        em.close();
        return student;
    }

    @Override
    public List<Student> getAllStudents(int pageNumber, int pageSize) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT s FROM Student s", Student.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }
}
