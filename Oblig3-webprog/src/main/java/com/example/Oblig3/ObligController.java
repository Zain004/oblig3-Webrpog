package com.example.Oblig3;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ObligController {
    //public final List<Bilett> biletter = new ArrayList<>();
    //public final List<Film> filmer = new ArrayList<>();

    @Autowired
    private ObligRepository rep;

    @GetMapping("/hentFilmer")
    public List<Film> filmer(HttpServletResponse response) throws IOException {
        List<Film> alleFilmer = rep.filmer();
        if(alleFilmer == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return alleFilmer;
    }

    @PostMapping("/lagreBilett")
    public void lagreBilett(Bilett enBilett, HttpServletResponse response) throws IOException {
        if(!rep.lagreBilett(enBilett)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/hentBiletter")
    public List<Bilett> hentBiletter(HttpServletResponse response) throws IOException {
        List<Bilett> alleBiletter = rep.hentBiletter();
        if(alleBiletter == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return alleBiletter;
    }

    @GetMapping("/slettBiletter")
    public void slettBiletter(HttpServletResponse response) throws IOException {
        if(!rep.slettBiletter()) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/slettEnBilett")
    public void slettEnBilett(int id, HttpServletResponse response) throws IOException {
        if(!rep.slettEnBilett(id)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/hentEnBilett")
    public Bilett hentBilett(int id,HttpServletResponse response) throws IOException {
        Bilett enBilett = rep.hentBilett(id);
        if (enBilett == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return enBilett;
    }

    @PostMapping("/lagreEndringen")
    public void lagreEndring(Bilett enbilett, HttpServletResponse response) throws IOException {
        if(!rep.lagreEndring(enbilett)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }
}

