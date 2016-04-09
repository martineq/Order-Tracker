package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Activities.OrderActivity;
import com.example.uriel.ordertracker.App.Model.Brand;
import com.example.uriel.ordertracker.App.Model.Product;
import com.example.uriel.ordertracker.App.Services.Interface.IProductService;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Uriel on 27-Mar-16.
 */
public class ProductService implements IProductService {

    private final IRestService restService = RestService.getInstance();

    public ArrayList<Product> getAll(String username, String token, OrderActivity context) throws JSONException{
        try {
            restService.getProducts(username, token, context);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products = new ArrayList<>();

        Product product1 = new Product(1, "Bandeja redonda", 50.25, new Brand(1, "BestPlates"),"");
        Product product2 = new Product(2, "Bandeja para masas", 99.99, new Brand(1, "BestPlates"),"");
        Product product3 = new Product(3, "Especiero de metal", 40, new Brand(2, "SuperBrand"),"");
        Product product4 = new Product(4, "Espejo regulable", 75.75, new Brand(2, "SuperBrand"),"");
        Product product5 = new Product(5, "Esponja", 15.50, new Brand(3, "KitchenMaster"),"");
        Product product6 = new Product(6, "Hielera", 80, new Brand(2, "SuperBrand"),"");
        Product product7 = new Product(7, "Jarra metalica", 65, new Brand(2, "SuperBrand"),"");
        Product product8 = new Product(8, "Pinche para choclo", 25, new Brand(3, "KitchenMaster"),"");
        Product product9 = new Product(9, "6 Pinches", 70, new Brand(2, "SuperBrand"),"");
        Product product10 = new Product(10, "Panquequera", 120.50, new Brand(3, "KitchenMaster"),"");

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);

        return products;
    }

    public ArrayList<Product> getByBrand(ArrayList<Product> allProducts ,String brand){
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : allProducts){
            if(product.getBrand().getDescription().equals(brand)){
                products.add(product);
            }
        }

        return products;
    }
}
