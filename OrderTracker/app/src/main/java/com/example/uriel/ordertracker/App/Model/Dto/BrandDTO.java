package com.example.uriel.ordertracker.App.Model.Dto;

import com.example.uriel.ordertracker.App.Model.Brand;

import java.util.ArrayList;

/**
 * Created by Uriel on 09-Apr-16.
 */
public class BrandDTO extends BaseDTO {
    private ArrayList<Brand> data;

    public ArrayList<Brand> getData() {
        return data;
    }

    public void setData(ArrayList<Brand> data) {
        this.data = data;
    }
}
