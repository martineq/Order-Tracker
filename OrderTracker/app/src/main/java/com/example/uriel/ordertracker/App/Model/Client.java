package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 25-Mar-16.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String state;

    public Client(int id, String name, String address, String state){
        this.id = id;
        this.name = name;
        this.address = address;
        this.state = state;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
