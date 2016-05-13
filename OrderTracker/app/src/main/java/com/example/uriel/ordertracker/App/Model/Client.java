package com.example.uriel.ordertracker.App.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Client implements Comparable<Client>  {
    private int id;
    private int visit_id;
    private String name;
    private String address;
    private String city;
    private String state;
    private long date;

    public Client(int id, int visit_id, String name, String address, String city, String state, long date){
        this.id = id;
        this.visit_id = visit_id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.date = date;
    }

    //Ordenar los clientes segun a quien tengo que visitar mas temprano
    public int compareTo(Client comparecli) {
        int compareage=((Client)comparecli).getDate().getHours();
        int myHours= getDate().getHours();

        if( compareage!=myHours ) {
            return myHours - compareage;
        }
        else {
            int comparemin=((Client)comparecli).getDate().getMinutes();
            int myMins= getDate().getMinutes();
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

    public Long getLongDate()
    {
        return date;
    }

    public Date getDate()
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.date);
        return calendar.getTime();
    }

    public void setDate(long date) { this.date = date; }

    public int getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(int visit_id) {
        this.visit_id = visit_id;
    }
}
