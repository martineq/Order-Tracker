package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 27-Mar-16.
 */
public class Brand {
    private int id;
    private String description;

    public Brand(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
