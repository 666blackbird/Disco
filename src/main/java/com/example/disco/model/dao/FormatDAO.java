package com.example.disco.model.dao;
import java.util.List;
import persistence.ConnectionFactory;
import javax.persistence.*;
import model.bean.Format;

public class FormatDAO {
    private static EntityManager em;

    public FormatDAO() {
        this.em = new ConnectionFactory().getConnection();
    }

    public Format create(Format f) {
        try {
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public List<Format> read() {
        List<Format> formats = null;
        try {
            formats = em.createQuery("from Format f").getResultList();
        } catch(Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return formats;
    }
    
    public void update(Format f) {
        try {
            em.getTransaction().begin();
            if(f.getId() == null) {
                em.persist(f);
            } else {
                em.merge(f);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(int id) {
        Format f = null;
        try {
            f = em.find(Format.class, id);
            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Format findById(int id) {
        Format format = null;
        try {
            format = this.read().stream().filter(e -> e.getId() == id).findAny().get();
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return format;
    }

    public Format findByType(String type) {
        Format format = null;
        try {
            format = this.read().stream().filter(e -> e.getType().equals(type)).findAny().get();
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return format;
    }
}
