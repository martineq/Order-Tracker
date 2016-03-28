package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Model.Brand;
import com.example.uriel.ordertracker.App.Services.Interface.IBrandService;

import java.util.ArrayList;

/**
 * Created by Uriel on 27-Mar-16.
 */
public class BrandService implements IBrandService {
    public ArrayList<Brand> getAll(){
        ArrayList<Brand> brands = new ArrayList<>();

        Brand brand1 = new Brand(1, "Marca 1");
        Brand brand2 = new Brand(2, "Marca 2");

        brands.add(brand1);
        brands.add(brand2);

        return brands;
    }
}
