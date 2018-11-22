package model.bean;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import java.util.*;
@Data
@Entity(name = "Format")
@Table(name = "format")
public class Format implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "for_id")
    private Long id;
    @Column(name = "for_type")
    private String type;
    @ManyToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pro_form")
    private List<Product> products = new ArrayList<>();
    
    @Override
    public int hashCode() {
        return this.getType().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        Format otr = (Format) obj;
        return this.getType().equalsIgnoreCase(otr.getType());
    }

    @Override
    public String toString() {
        return this.getId() + " | " + this.getType();
    }
}
