package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ObligController {
    //public final List<Bilett> biletter = new ArrayList<>();
    //public final List<Film> filmer = new ArrayList<>();

    @Autowired
    private ObligRepository rep;

    @GetMapping("/hentFilmer")
    public List<Film> filmer() {
        return rep.filmer();
    }

    @PostMapping("/lagreBilett")
    public void lagreBilett(Bilett enBilett){
        rep.lagreBilett(enBilett);
    }

    @GetMapping("/hentBiletter")
    public List<Bilett> hentBiletter() {
        return rep.hentBiletter();
    }

    @GetMapping("/slettBiletter")
    public void slettBiletter() {
        rep.slettBiletter();
    }

    @GetMapping("/slettEnBilett")
    public void slettEnBilett(int id) {
        rep.slettEnBilett(id);
    }
}