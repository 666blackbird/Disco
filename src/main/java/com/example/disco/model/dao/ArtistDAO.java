package com.example.disco.model.dao;
import java.util.List;
import com.example.disco.model.bean.*;
import javax.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import persistence.ConnectionFactory;

@Component
public class ArtistDAO {
    private static EntityManager em;

    public ArtistDAO() {
        this.em = new ConnectionFactory().getConnection();
    }

    public void create(Artist a) {
        try {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    @Autowired
    public List<Artist> read() {
        List<Artist> artists = null;
        try {
            artists = em.createQuery("from Artist a").getResultList();
        } catch(Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return artists;
    }
    
    public void update(Artist a) {
        try {
            em.getTransaction().begin();
            if(a.getId() == null) {
                em.persist(a);
            } else {
                em.merge(a);
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
        Artist a = null;
        try {
            a = em.find(Artist.class, id);
            em.getTransaction().begin();
            em.remove(a);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Artist findById(int id) {
        Artist artist = null;
        try {
            artist = this.read().stream().filter(e -> e.getId() == id).findAny().get();
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return artist;
    }

    public Artist findByName(String name) {
        Artist artist = null;
        try {
            artist = this.read().stream().filter(e -> e.getName().equals(name)).findAny().get();
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return artist;
    }

    public List<Artist> findByGenre(String genre) {
        List<Artist> list = null;
        try {
            for(Artist a : this.read()) {
                if(a.getGenre().equals(genre)) {
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