package com.example.disco.controller;
import com.example.disco.model.bean.*;
import com.example.disco.model.dao.*;
import com.example.disco.model.business.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class ArtistController {
    private ArtistBusiness ab;

    @Autowired
    public ArtistController(ArtistBusiness ab) {
        this.ab = ab;
    }

    @GetMapping("/artists/")
    public String listArtists(Model model) {
        model.addAttributte("artistList", this.ab.read());
        return "artists";
    }

    @PostMapping("/artists/new")
    public String newArtist(Artist a) {
        try {
            Artist artist = new Artist();
            artist.setName(a.getName());
            artist.setGenre(a.getGenre());
            this.ab.create(artist);
        } catch(Exception e) {
            // log.error(e.getCause());
        }
        return "redirect:/artists";
    }

    @PutMapping("/artists/edit")
    public String editArtist(Artist a) {
        try {
            Artist artist = new Artist();
            artist.setName(a.getName());
            artist.setGenre(a.getGenre());
            this.ab.update(artist);
        } catch(Exception e) {
            // log.error(e.getCause());
        }
        return "redirect:/artists";
    }

    @GetMapping("/artists/delete")
    public String removeArtist(@RequestParam int id) {
        this.ab.delete(id);
        return "redirect:/artists";
    }
}