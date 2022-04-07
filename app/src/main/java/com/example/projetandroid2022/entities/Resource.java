package com.example.projetandroid2022.entities;

import android.media.Image;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Resource {
    private String name, synopsis, realisateur, date, origine, backdropURL, posterURL;
    private List<Actor> acteurs;

    public Resource() {
        acteurs = new ArrayList<>();
        backdropURL = "@drawable/default_backdrop";
        posterURL = "@drawable/default_poster";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getBackdropURL() {
        return backdropURL;
    }

    public void setBackdropURL(String backdropURL) {
        this.backdropURL = backdropURL;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public List<Actor> getActeurs() {
        return acteurs;
    }

    public void setActeurs(List<Actor> acteurs) {
        this.acteurs = acteurs;
    }
}
