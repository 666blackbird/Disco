package com.example.disco.model.business;
import com.example.disco.model.Artist;

public class ArtistBusiness {
    private ArtistDAO dao;

    public ArtistBusiness(ArtistDAO dao) {
        this.dao = dao;
    }

    public boolean discCascade(Artist a, List<Disc> discs) {
        DiscDAO dao = new DiscDAO();
        discs = dao.findByArtist(a.getName());
        if(discs == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValid(Artist a) {
        if(a.getName() == null || a.getName().length() > 2) {
            return false;
        } else if(a.getGenre() == null || a.getGenre().length() > 2) {
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Artist a) {
        if(dao.read().stream().map(Artist::getName).anyMatch(e -> e.equals(a.getName()))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean create(Artist a) throws Exception {
        if(this.isValid(a) == false) {
            throw new Exception("You must define an artist to create!");
        } else if(this.equals(a) == true) {
            throw new Exception("You must define another name to the artist!");
        } else {
            this.dao.create(a);
            return true;
        }
    }

    public List<Artist> read() {
        return this.dao.read();
    }

    public boolean update(Artist a) throws Exception {
        if(this.isValid(a) == false) {
            throw new Exception("You must define an artist to update!");
        } else if(this.equals(a) == true) {
            throw new Exception("You must define another name to the artist!");
        } else {
            this.dao.update(a);
            return true;
        }
    }

    public boolean delete(int id) throws Exception {
        Artist a = this.dao.findById(id);
        if(a == null) {
            throw new Exception("You must select an artist to delete!");
        } else {
            if(this.discCascade(a, a.getDiscs()) == true) {
                DiscDAO dao = new DiscDAO();
                for(Disc d : dao.findByArtist(a.getName())) {
                    dao.delete(d.getId());
                }
                this.delete(a.getId());
            } else {
                this.dao.delete(a.getId());
                return true;
            }
        }
    }
}