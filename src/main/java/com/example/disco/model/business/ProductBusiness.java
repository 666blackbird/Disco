package com.example.disco.model.business;
import com.example.disco.model.bean.*;
import com.example.disco.model.dao.*;

public class ProductBusiness {
    private ProductDAO dao;

    public ProductBusiness(ProductDAO dao) {
        this.dao = dao;
    }

    public boolean validDisc(Product p) {
        DiscDAO dao = new DiscDAO();
        if(dao.read().stream().map(Disc::getTitle).anyMatch(e -> e.equals(p.getDisc().getTitle()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validForm(Product p) {
        FormatDAO dao = new FormatDAO();
        if(dao.read().stream().map(Format::getType).anyMatch(e -> e.equals(p.getForm().getType()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValid(Product p) {
        if(this.validDisc(p) == false) {
            return false;
        } else if(this.validForm(p) == false) {
            return false;
        } else if(p.getPrice() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Product p) {
        if(this.dao.read().stream().map(Product::getDisc).anyMatch(e -> e.equals(p.getDisc()))) {
            if(this.dao.read().stream().map(Product::getForm).anyMatch(e -> e.equals(p.getForm()))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean create(Product p) throws Exception {
        if(this.isValid(p) == false) {
            throw new Exception("You must define a product to create!");
        } else if(this.equals(p) == true) {
            throw new Exception("You must define another disc/format to the product!");
        } else {
            this.dao.create(p);
            return true;
        }
    }

    public boolean update(Product p) throws Exception {
        if(this.isValid(p) == false) {
            throw new Exception("You must define a product to update!");
        } else if(this.equals(p) == true) {
            throw new Exception("You must define another disc/format to the product!");
        } else {
            this.dao.update(p);
            return true;
        }
    }

    public boolean delete(int id) throws Exception {
        Product p = this.dao.findById(id);
        if(p == null) {
            throw new Exception("You must select a product to delete!");
        } else {
            this.dao.delete(p.getId());
            return true;
        }
    }
}