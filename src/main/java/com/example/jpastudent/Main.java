package com.example.jpastudent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        StudentDAO studentDAO = new StudentDAOImpl();
        AddressDAO addressDAO = new AddressDAOImpl();
        
        try {
            emf = Persistence.createEntityManagerFactory("Student");
            em = emf.createEntityManager();
            
            System.out.println("La connexion est établie!");

            Student student = new Student();
            student.setName("Ishaq Haj");
            student.setEmail("ishaq.haj2@gmail.com");
            student.setAge(23);
            

            Address address = new Address();
            address.setStreet("Sidi Abbad");
            address.setCity("Marrakech");
            address.setZipCode("40000");
            address.setCountry("Maroc");

            student.setAddress(address);
            address.setStudent(student);

            addressDAO.insertAddress(address);

            System.out.println("L'ajout avec succès!");
            

            
            Long studentId = student.getId();
            Student stud = em.find(Student.class, studentId);
            System.out.println("Les infos d'etudiant: " + stud);
            
            if (stud.getAddress() != null) {
                Address add = stud.getAddress();
                System.out.println("Adresse à partir de l'étudiant: " +
                    add.getStreet() + ", " +
                    add.getCity() + ", " +
                    add.getZipCode() + ", " +
                    add.getCountry());
                System.out.println("ID d'adresse dans la table students: " + add.getId());
            } else {
                System.out.println("L'étudiant n'a pas d'adresse!");
            }
            
            // Test 2: Find address directly and verify its student
            Long addressId = address.getId();
            Address addresse = em.find(Address.class, addressId);
            System.out.println("Adresse trouvée avec ID: " + addressId);
            
            if (addresse.getStudent() != null) {
                Student stud_add = addresse.getStudent();
                System.out.println("Étudiant à partir de l'adresse: " + 
                    stud_add.getName() + ", " +
                    stud_add.getEmail() + ", " +
                    stud_add.getAge());
                System.out.println("ID d'étudiant dans la table addresses: " + stud_add.getId());
            } else {
                System.out.println("L'adresse n'est liée à aucun étudiant!");
            }
            
            // Test DB foreign keys
            System.out.println("\nTest des clés étrangères:");
            System.out.println("Student ID: " + studentId + ", Address ID: " + addressId);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            // Close resources
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
