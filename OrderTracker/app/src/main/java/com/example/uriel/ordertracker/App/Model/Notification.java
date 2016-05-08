package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 05-May-16.
 */
public class Notification {
    private int id;
    private String title;
    private String body;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
