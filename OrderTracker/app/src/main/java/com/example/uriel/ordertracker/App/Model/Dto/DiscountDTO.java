package com.example.uriel.ordertracker.App.Model.Dto;

import com.example.uriel.ordertracker.App.Model.Discount;

import java.util.ArrayList;

/**
 * Created by Uriel on 11-May-16.
 */
public class DiscountDTO extends BaseDTO {
    private ArrayList<Discount> data;

    public ArrayList<Discount> getData() {
        return data;
    }

    public void setData(ArrayList<Discount> data) {
        this.data = data;
    }
}
