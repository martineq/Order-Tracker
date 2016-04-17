package com.example.uriel.ordertracker.App.Model;

import java.util.Date;

public class Client implements Comparable<Client>  {
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private Date visitDate;

    public Client(int id, String name, String address, String city, String state, Date visitDate){
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.visitDate = visitDate;
    }

    //Ordenar los clientes segun a quien tengo que visitar mas temprano
    public int compareTo(Client comparecli) {
        int compareage=((Client)comparecli).getVisitDate().getHours();
        int myHours=getVisitDate().getHours();

        if( compareage!=myHours ) {
            return myHours - compareage;
        }
        else {
            int comparemin=((Client)comparecli).getVisitDate().getMinutes();
            int myMins=getVisitDate().getMinutes();
            return myMins - comparemin;
        }
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

}
