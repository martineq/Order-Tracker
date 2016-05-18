package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 05-May-16.
 */
public class Notification {
    private int id;
    private String title;
    private String body;
    private int type;

    public Notification(int id, String title, String body, int type){
        this.id = id;
        this.title = title;
        this.body = body;
        this.type = type;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
