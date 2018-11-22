package com.example.disco.model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
@Data
@Entity(name = "Artist")
@Table(name = "artist")
public class Artist implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "art_id")
    private int id;
    @Column(name = "art_name")
    private String name;
    @Column(name = "art_genre")
    private String genre;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dis_artist")
    private List<Disc> discs = new ArrayList<>();
    
    public void addDisc(Disc disc) {
        discs.add(disc);
        disc.setArtist(this);
    }

    public void removeDisc(Disc disc) {
        discs.remove(disc);
        disc.setArtist(null);
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Artist otr = (Artist) obj;
        return this.getName().equalsIgnoreCase(otr.getName());
    }

    @Override
    public String toString() {
        return this.getId() + " | " + this.getName() + " - " + this.getGenre();
    }
}