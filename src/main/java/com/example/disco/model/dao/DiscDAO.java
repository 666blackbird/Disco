package com.example.disco.model.dao;
import java.util.List;
import javax.persistence.*;
import persistence.ConnectionFactory;
import model.bean.*;

public class DiscDAO {
    private static EntityManager em;
    
    public DiscDAO() {
        this.em = new ConnectionFactory().getConnection();
    }
    
    public void create(Disc d) {
        try {
            em.getTransaction().begin();
            em.persist(d);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e.getCause());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public List<Disc> read() {
        List<Disc> discs = null;
        try {
            discs = em.createQuery("from Disc d").getResultList();
        } catch(Exception e) {
            System.err.println(e.getCause());
        } finally {
            em.close();
        }
        return discs;
    }
    
    public void update(Disc d) {
        try {
            em.getTransaction().begin();
            if(d.getId() == null) {
                em.persist(d);
            } else {
                em.merge(d);
            }
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e.getCause());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Long id) {
        Disc d = null;
        try {
            d = em.find(Disc.class, id);
            em.getTransaction().begin();
            em.remove(d);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e.getCause());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Disc findById(int id) {
        Disc disc = null;
        try {
            disc = this.read().stream().filter(e -> e.getId() == id).findAny().get();
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return disc;
    }

    public Disc findByTitle(String title) {
        Disc disc = null;
        try {
            disc = this.read().stream().filter(e -> e.getTitle().equals(title)).findAny().get();
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return disc;
    }

    public List<Disc> findByArtist(String artist) {
        List<Disc> list = null;
        try {
            for(Disc d : this.read()) {
                if(d.getArtist().getName().equals(artist)) {
                    list.add(a);
                }
            }
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Disc> findByGenre(String genre) {
        List<Disc> list = null;
        try {
            for(Disc d : this.read()) {
                if(d.getArtist().getGenre().equals(genre)) {
                    list.add(a);
                }
            }
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return list;
    }
}