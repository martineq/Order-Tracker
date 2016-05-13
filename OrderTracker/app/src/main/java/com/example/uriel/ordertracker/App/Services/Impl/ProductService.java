package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Activities.OrderActivity;
import com.example.uriel.ordertracker.App.Model.Discount;
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

    public void getAll(String username, String token, OrderActivity context) throws JSONException{
        try {
            restService.getProducts(username, token, context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getByBrand(ArrayList<Product> allProducts ,String brand){
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : allProducts){
            String prod = product.getBrand().toLowerCase();
            String search = brand.toLowerCase();
            if(prod.contains(search)){
                products.add(product);
            }
        }

        return products;
    }

    public double calculateDiscount(ArrayList<Product> allProducts, int productId, int quantity){
        Product myProduct = null;
        for (Product product: allProducts) {
            if(product.getId() == productId){
                myProduct = product;
                break;
            }
        }

        for (Discount discount: myProduct.getDiscounts()) {
            if(quantity >= discount.getRangeFrom() && (discount.getRangeTo() == 0 || quantity <= discount.getRangeTo())){
                return (myProduct.getPrice() * quantity) * discount.getPercentage() / 100;
            }
        }
        return 0;
    }

}
