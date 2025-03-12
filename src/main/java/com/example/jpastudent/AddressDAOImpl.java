package com.example.jpastudent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class AddressDAOImpl implements AddressDAO {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Student");

    @Override
    public void insertAddress(Address address){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    @Override
    public void updateAddress(Address address) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(address);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    @Override
    public void deleteAddress(Long addressId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Address address = em.find(Address.class, addressId);
            em.remove(address);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    @Override
    public Address getAddress(Long addressId) {
        EntityManager em = emf.createEntityManager();
        Address address = em.find(Address.class, addressId);
        em.close();
        return address;
    }
    
    @Override
    public List<Address> getAllAddresses(int pageNumber, int pageSize) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Address a", Address.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }
}
