package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Model.Brand;
import com.example.uriel.ordertracker.App.Services.Interface.IBrandService;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;

import java.util.ArrayList;

/**
 * Created by Uriel on 27-Mar-16.
 */
public class BrandService implements IBrandService {

    private final IRestService restService = RestService.getInstance();

    public ArrayList<Brand> getAll(){
        ArrayList<Brand> brands = new ArrayList<>();

        Brand brand1 = new Brand(1, "BestPlates");
        Brand brand2 = new Brand(2, "SuperBrand");
        Brand brand3 = new Brand(3, "KitchenMaster");

        brands.add(brand1);
        brands.add(brand2);
        brands.add(brand3);

        return brands;
    }
}
