package com.example.uriel.ordertracker.App.Model.Dto;

import com.example.uriel.ordertracker.App.Model.Product;

import java.util.ArrayList;

/**
 * Created by Uriel on 07-Apr-16.
 */
public class ProductDTO extends BaseDTO {
    private ArrayList<Product> data;

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product> data) {
        this.data = data;
    }
}
