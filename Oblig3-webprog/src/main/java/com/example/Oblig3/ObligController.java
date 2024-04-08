package com.example.Oblig3;


import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private ObligRepository rep;
    private Logger logger = LoggerFactory.getLogger(ObligController.class);
    public boolean validering(Bilett bil) {
        String regexFornavn = "[a-zA-ZæøåÆØÅ. \\-]{2,30}";
        String regexEtternavn = "[a-zA-ZæøåÆØÅ. \\-]{2,30}";
        String regexTelefonnummer = "[0-9]{8}";
        String regexEpost = "[^\\s@]+@[^\\s@]+\\.[^\\s@]+";
        String regexFilmnavn = "[0-9a-zA-ZæøåÆØÅ., \\-]{2,30}";
        boolean fornavnOK = bil.getFornavn().matches(regexFornavn);
        boolean etternavnOK = bil.getEtternavn().matches(regexEtternavn);
        boolean tlfOK = bil.getTlf().matches(regexTelefonnummer);
        boolean epostOK = bil.getEpost().matches(regexEpost);
        boolean filmnavnOK = bil.getFilmnavn().matches(regexFilmnavn);
        if(fornavnOK && etternavnOK && tlfOK && epostOK && filmnavnOK) {
            return true;
        }
        logger.error("invalideringsfeil");
        return false;
    }
    @PostMapping("/lagreEndringen")
    public void lagreEndring(Bilett enbilett, HttpServletResponse response) throws IOException {
        if(!validering(enbilett)) {
            response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
        }
        else {
            if(!rep.lagreEndring(enbilett)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
            }
        }
    }
    @PostMapping("/lagreBilett")
    public void lagreBilett(Bilett enBilett, HttpServletResponse response) throws IOException {
        if(!validering(enBilett)){
            response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
        }
        else {
            if(!rep.lagreBilett(enBilett)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
            }
        }
    }
    @GetMapping("/hentFilmer")
    public List<Film> filmer(HttpServletResponse response) throws IOException {
        List<Film> alleFilmer = rep.filmer();
        if(alleFilmer == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return alleFilmer;
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
}
