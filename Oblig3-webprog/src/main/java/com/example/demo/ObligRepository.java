package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ObligRepository {
    @Autowired
    private JdbcTemplate db;

    public List<Film> filmer() {
        String sql = "SELECT * FROM Film order by filmnavn";
        List<Film> alleFilmer = db.query(sql,new BeanPropertyRowMapper<>(Film.class));
        return alleFilmer;
    }

    public void lagreBilett(Bilett enBilett){
        String sql = "INSERT INTO Bilett (filmnavn, antall, etternavn, fornavn, tlf, epost) VALUES (?,?,?,?,?,?)";
        db.update(sql, enBilett.getFilmnavn(), enBilett.getAntall(), enBilett.getEtternavn(), enBilett.getFornavn(), enBilett.getTlf(), enBilett.getEpost());
    }


    public List<Bilett> hentBiletter() {
        String sql = "SELECT * FROM Bilett order by etternavn";
        List<Bilett> alleBiletter = db.query(sql,new BeanPropertyRowMapper<>(Bilett.class));
        return alleBiletter;
    }

    public void slettBiletter() {
        String sql = "DELETE FROM Bilett";
        db.update(sql);
    }

    public void slettEnBilett(int id) {
        String sql = "DELETE FROM Bilett WHERE id=?";
        db.update(sql,id);
    }
    public Bilett hentBilett(int id) {
        Object[] liste = new Object[1];
        liste[0] = id;
        String sql = "SELECT * FROM Bilett WHERE id=?";
        Bilett enBilett = db.queryForObject(sql, liste, BeanPropertyRowMapper.newInstance(Bilett.class));
        return enBilett;
    }
}
