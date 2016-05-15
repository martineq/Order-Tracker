package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 11-May-16.
 */
public class Discount {
    private int id;
    private double percentage;
    private int range_from;
    private int range_upto;

    public Discount(int id, double percentage, int range_from, int range_upto){
        this.id = id;
        this.percentage = percentage;
        this.range_from = range_from;
        this.range_upto = range_upto;
    }

    public Discount(int id, double percentage, int rangeFrom){
        this.id = id;
        this.percentage = percentage;
        this.range_from = rangeFrom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getRangeFrom() {
        return range_from;
    }

    public void setRangeFrom(int rangeFrom) {
        this.range_from = rangeFrom;
    }

    public int getRangeTo() {
        return range_upto;
    }

    public void setRangeTo(int rangeTo) {
        this.range_upto = rangeTo;
    }
}
