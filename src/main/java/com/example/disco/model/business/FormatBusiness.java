package com.example.disco.model.business;
import com.example.disco.model.dao.FormatDAO;
import com.example.disco.model.Format;

public class FormatBusiness {
    private FormatDAO dao;

    public FormatBusiness(FormatDAO dao) {
        this.dao = dao;
    }

    public boolean isValid(Format f) {
        if(f.getType() == null || f.getType().length() < 2) {
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Format f) {
        if(dao.read().stream().map(Format::getType).anyMatch(e -> e.equals(f.getType()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean create(Format f) throws Exception {
        if(this.isValid(f) == false) {
            throw new Exception("You must define a format to create!");
        } else {
            this.dao.create(f);
            return true;
        }
    }

    public List<Format> read() {
        return this.dao.read();
    }

    public boolean update(Format f) throws Exception {
        if(this.isValid(f) == false) {
            throw new Exception("You must define a format to update!");
        } else {
            this.dao.update(f);
            return true;
        }
    }

    public boolean delete(int id) throws Exception {
        Format f = this.dao.findById(id);
        if(f == null) {
            throw new Exception("You must select a format to delete!");
        } else {
            this.dao.create(f.getId());
            return true;
        }
    }
}