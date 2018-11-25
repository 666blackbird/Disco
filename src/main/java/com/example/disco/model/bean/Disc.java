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
    private int id;
    @Column(name = "dis_title")
    private String title;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "art_id")
    @Column(name = "dis_artist")
    private Artist artist;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pro_disc")
    private List<Product> products;
    
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