package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 23-Mar-16.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String token;

    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
