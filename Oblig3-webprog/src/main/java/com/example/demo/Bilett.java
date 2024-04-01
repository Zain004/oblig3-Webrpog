package com.example.demo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor //autogenerer en tom konstruktør
@AllArgsConstructor //autogenerer en konctruktør
@Data //generer auto get og set metoder
public class Bilett {
    private int id;
    private String filmnavn;
    private int antall;
    private String etternavn;
    private String fornavn;
    private String tlf;
    private String epost;
}

