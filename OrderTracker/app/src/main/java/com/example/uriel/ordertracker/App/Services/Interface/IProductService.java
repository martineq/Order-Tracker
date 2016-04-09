package com.example.uriel.ordertracker.App.Services.Interface;

import com.example.uriel.ordertracker.App.Activities.OrderActivity;
import com.example.uriel.ordertracker.App.Model.Product;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Uriel on 27-Mar-16.
 */
public interface IProductService {
    ArrayList<Product> getAll(String username, String token, OrderActivity context) throws JSONException;
    ArrayList<Product> getByBrand(ArrayList<Product> allProducts, String brand);
}
