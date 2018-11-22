package com.example.disco.model.bean;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;
import java.util.*;
@Data
@Entity(name = "Disc")
@Table(name = "disc")
public class Disc implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dis_id")
    private Long id;
    @Column(name = "dis_title")
    private String title;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "art_id") 
    @Column(name = "dis_artist") 
    private Artist artist;
    @ManyToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pro_disc")
    private List<Product> products = new ArrayList<>();
    
    public void addProd(Product prod) {
        this.products.add(prod);
        prod.setDisc(this);
    }

    public void removeProd(Product prod) {
        this.products.remove(prod);
        prod.setDisc(null);
    }

    @Override
    public int hashCode() {
        return this.getTitle().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        Disc otr = (Disc) obj;
        return this.getTitle().equalsIgnoreCase(otr.getTitle());
    }

    @Override
    public String toString() {
        return this.getId() + " | " + this.getTitle() + " - " + this.getArtist().getName();
    }
}