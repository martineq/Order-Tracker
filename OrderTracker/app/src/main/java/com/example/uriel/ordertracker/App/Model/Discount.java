package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 11-May-16.
 */
public class Discount {
    private int id;
    private int percentage;
    private int rangeFrom;
    private int rangeTo;

    public Discount(int id, int percentage, int rangeFrom, int rangeTo){
        this.id = id;
        this.percentage = percentage;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public Discount(int id, int percentage, int rangeFrom){
        this.id = id;
        this.percentage = percentage;
        this.rangeFrom = rangeFrom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(int rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public int getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(int rangeTo) {
        this.rangeTo = rangeTo;
    }
}
