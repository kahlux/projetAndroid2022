package com.example.projetandroid2022.entities;

import java.io.Serializable;

public class Actor implements Serializable {
    String nom;
    String url;
    String personnage ;

    public String getNom() {
        return nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "nom='" + nom + '\'' +
                ", url='" + url + '\'' +
                ", personnage='" + personnage + '\'' +
                '}';
    }
}
