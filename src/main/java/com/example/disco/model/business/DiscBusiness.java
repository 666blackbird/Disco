package com.example.disco.model.business;
import com.example.disco.model.bean.*;
import com.example.disco.model.dao.*;
// implementar funções para excluir em cascata, excluir todos os produtos de um determinado disco
// ou proudtos de um determinado formato, ou produtos de um determiando disco de um determinado artista
// que está sendo excluido também
public class DiscBusiness {
    private DiscDAO dao;

    public DiscBusiness(DiscDAO dao) {
        this.dao = dao;
    }

    public boolean validArtist(Disc d) {
        ArtistDAO dao = new ArtistDAO();
        if(dao.read().stream().map(Artist::getName).anyMatch(e -> e.equals(d.getArtist().getName()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean prodCascade(Disc d, List<Product> prods) {
        ProductDAO dao = new ProdutoDAO();
        prods = dao.findByTitle(d.getTitle());
        if(prods == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValid(Disc d) {
        if(d.getTitle() == null || d.getTitle().length() > 3) {
            return false;
        } else if(this.validArtist(d) == false) {
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Disc d) {
        if(this.dao.read().stream().map(Disc::getTitle).anyMatch(e -> e.equals(a.getTitle()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean create(Disc d) throws Exception {
        if(this.isValid(d) == false) {
            throw new Exception("You must define a disc to create!");
        } else if(this.equals(d) == true) {
            throw new Exception("You must define another name to the disc!");
        } else {
            this.dao.create(d);
            return true;
        }
    }

    public List<Disc> read() {
        return this.dao.read();
    }

    public boolean update(Disc d) throws Exception {
        if(this.isValid(d) == false) {
            throw new Exception("You must define a disc to update!");
        } else if(this.equals(d) == true) {
            throw new Exception("You must define another name to the disc!");
        } else {
            this.dao.update(d);
            return true;
        }
    }

    public boolean delete(int id) throws Exception {
        Disc d = this.dao.findById(id);
        if(d == null) {
            throw new Exception("You must select a disc to delete!");
        } else {
            if(this.prodCascade(d, d.getProducts()) == true) {
                ProductDAO dao = new ProductDAO();
                for(Product p : dao.findByTitle(d.getTitle())) {
                    dao.delete(p.getId());
                }
                this.delete(d.getId());
            } else {
                this.dao.delete(d.getId());
                return true;
            }
        } 
    }
}