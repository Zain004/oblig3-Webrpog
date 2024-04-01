package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ObligController {
    public final List<Bilett> biletter = new ArrayList<>();
    public final List<Film> filmer = new ArrayList<>();
    public ObligController() {
        Film film1 = new Film("Jumanji");
        filmer.add(film1);
        Film film2 = new Film("Petter Kanin");
        filmer.add(film2);
        Film film3 = new Film("Marvel Avengers");
        filmer.add(film3);
    }
    @GetMapping("/hentFilmer")
    public List<Film> filmer() {
        return filmer;
    }

    @PostMapping("/lagreBilett")
    public void lagreBilett(Bilett enBilett){
        biletter.add(enBilett);
    }

    @GetMapping("/hentBiletter")
    public List<Bilett> hentBiletter() {
        return biletter;
    }

    @GetMapping("/slettBiletter")
    public void slettBiletter() {
        biletter.clear();
    }
}
