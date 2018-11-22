package com.example.disco.model.dao;
import java.util.List;
import javax.persistence.*;
import com.example.disco.model.bean.*;
import persistence.ConnectionFactory;

public class ProductDAO {
    private static EntityManager em;
    
    public ProductDAO() {
        this.em = new ConnectionFactory().getConnection();
    }
    
    public void create(Product p) {
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e.getCause());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public List<Product> read() {
        List<Product> products = null;
        try {
            products = em.createQuery("from Product p").getResultList();
        } catch(Exception e) {
            System.err.println(e.getCause());
        } finally {
            em.close();
        }
        return products;
    }
    
    public void update(Product p) {
        try {
            em.getTransaction().begin();
            if(p.getId() == null) {
                em.persist(p);
            } else {
                em.merge(p);
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
        Product p = null;
        try {
            p = em.find(Product.class, id);
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        } catch(Exception e) {
            System.err.println(e.getCause());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Product findById(int id) {
        Product product = null;
        try {
            product = this.read().stream().filter(e -> e.getId() == id).findAny().get();
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return product;
    }

    public List<Product> findByTitle(String title) {
        List<Product> list = null;
        try {
            for(Product p : this.read()) {
                if(p.getDisc().getTitle().equals(title)) {
                    list.add(p);
                }
            }
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Product> findByArtist(String artist) {
        List<Product> list = null;
        try {
            for(Product p : this.read()) {
                if(p.getDisc().getArtist().getName().equals(artist)) {
                    list.add(p);
                }
            }
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Product> findByGenre(String genre) {
        List<Product> list = null;
        try {
            for(Product p : this.read()) {
                if(p.getDisc().getArtist().getGenre().equals(genre)) {
                    list.add(p);
                }
            }
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return list;
    }

    public List<Product> findByFormat(String format) {
        List<Product> list = null;
        try {
            for(Product p : this.read()) {
                if(p.getForm().getType().equals(format)) {
                    list.add(p);
                }
            }
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.err.println(e.getMessage());
        }
        return list;
    }
}