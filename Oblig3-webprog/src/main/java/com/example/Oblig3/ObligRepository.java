package com.example.Oblig3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ObligRepository {
    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(ObligRepository.class);
    public List<Film> filmer() {
        String sql = "SELECT * FROM Film order by filmnavn";
        try {
            List<Film> alleFilmer = db.query(sql,new BeanPropertyRowMapper<>(Film.class));
            return alleFilmer;
        }
        catch (Exception e) {
            logger.error("Feil i hentFilmer : "+e);
            return null;
        }
    }

    public boolean lagreBilett(Bilett enBilett){
        String sql = "INSERT INTO Bilett (filmnavn, antall, etternavn, fornavn, tlf, epost) VALUES (?,?,?,?,?,?)";
        try {
            db.update(sql, enBilett.getFilmnavn(), enBilett.getAntall(), enBilett.getEtternavn(), enBilett.getFornavn(), enBilett.getTlf(), enBilett.getEpost());
            return true;
        }
        catch (Exception e) {
            logger.error("Feil i lagreBilett : "+e);
            return false;
        }
    }


    public List<Bilett> hentBiletter() {
        String sql = "SELECT * FROM Bilett order by etternavn";
        try {
            List<Bilett> alleBiletter = db.query(sql,new BeanPropertyRowMapper<>(Bilett.class));
            return alleBiletter;
        }
        catch (Exception e) {
            logger.error("Feil i hentBiletter : "+e);
            return null;
        }
    }

    public boolean slettBiletter() {
        String sql = "DELETE FROM Bilett";
        try {
            db.update(sql);
            return true;
        } catch (Exception e) {
            logger.error("Feil i slettBiletter : " + e);
            return false;
        }
    }

    public boolean slettEnBilett(int id) {
        String sql = "DELETE FROM Bilett WHERE id=?";
        try {
            db.update(sql,id);
            return true;
        }
        catch (Exception e) {
            logger.error("Feil i slettEnBilett : " + e);
            return false;
        }
    }
    public Bilett hentBilett(int id) {
        //Object[] liste = new Object[1];
        //liste[0] = id;
        String sql = "SELECT * FROM Bilett WHERE id=?";
        try {
            Bilett enBilett = db.queryForObject(sql, BeanPropertyRowMapper.newInstance(Bilett.class),id);
            return enBilett;
        } catch (Exception e) {
            logger.error("Feil i hentEnBilett : " + e);
            return null;
        }
    }
    public boolean lagreEndring(Bilett enBilett) {
        String sql = "UPDATE Bilett SET filmnavn=?, antall=?, etternavn=?, fornavn=?, tlf=?, epost=? WHERE id =?";
        try {
            db.update(sql, enBilett.getFilmnavn(), enBilett.getAntall(), enBilett.getEtternavn(), enBilett.getFornavn(), enBilett.getTlf(), enBilett.getEpost(), enBilett.getId());
            return true;
        }
        catch (Exception e) {
            logger.error("Feil i lagreEndringen : " + e);
            return false;
        }
    }
}
