package com.example.projetandroid2022.entities;

import java.sql.Date;

public class WatchListEntry {

    private int id, resourceId, rating;
    private Date dateOfViewing;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isShow() { return isShow; }

    public void setShow(boolean show) {
        isShow = show;
    }

    public Date getDateOfViewing() {
        return dateOfViewing;
    }

    public void setDateOfViewing(Date dateOfViewing) {
        this.dateOfViewing = dateOfViewing;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
