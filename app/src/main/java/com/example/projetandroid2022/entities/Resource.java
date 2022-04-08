package com.example.projetandroid2022.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Resource  implements Serializable {
    int id;
    private String name, synopsis, date, backdropURL, posterURL;
    private List<Actor> acteurs;
    private boolean isShow;

    public Resource() {
        acteurs = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", date='" + date + '\'' +
                ", backdropURL='" + backdropURL + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", acteurs=" + acteurs.toString() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
