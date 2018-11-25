package com.example.disco.model.bean;
import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity(name = "Product")
@Table(name = "product")
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pro_id")
    private int id;
    @ManyToMany(fetch = FetchType.LAZY) @JoinColumn(name = "dis_id")
    @Column(name = "pro_disc")
    private Disc disc;
    @ManyToMany(fetch = FetchType.LAZY) @JoinColumn(name = "for_id")
    @Column(name = "pro_form")
    private Format form;
    @Column(name = "pro_price")
    private double price;
    
    @Override
    public int hashCode() {
        return this.getDisc().getTitle().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        Product otr = (Product) obj;
        if(this.getDisc()==otr.getDisc() && this.getForm()==otr.getForm()) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return this.getId() + " | " + this.getDisc().getTitle() + " - "
                + this.getDisc().getArtist().getName() + ", "
                + this.getForm().getType();
    }
}