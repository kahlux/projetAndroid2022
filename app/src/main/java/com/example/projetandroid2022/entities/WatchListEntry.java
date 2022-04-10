package com.example.projetandroid2022.entities;

import java.sql.Date;

public class WatchListEntry {

    private int id, resourceId;
    private float rating;
    private String dateOfViewing;
    private boolean isShow;
    private Resource resource;

    public WatchListEntry() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isShow() { return isShow; }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getDateOfViewing() {
        return dateOfViewing;
    }

    public void setDateOfViewing(String dateOfViewing) {
        this.dateOfViewing = dateOfViewing;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
