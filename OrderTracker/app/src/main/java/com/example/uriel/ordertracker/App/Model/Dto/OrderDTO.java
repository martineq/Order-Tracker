package com.example.uriel.ordertracker.App.Model.Dto;

import java.util.ArrayList;

/**
 * Created by Uriel on 01-May-16.
 */
public class OrderDTO extends BaseDTO {
    private ArrayList<PlainOrder> data;

    public ArrayList<PlainOrder> getData() {
        return data;
    }

    public void setData(ArrayList<PlainOrder> data) {
        this.data = data;
    }
}
