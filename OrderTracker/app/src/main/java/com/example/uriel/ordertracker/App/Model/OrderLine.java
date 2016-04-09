package com.example.uriel.ordertracker.App.Model;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Uriel on 31-Mar-16.
 */
public class OrderLine {
    private int id;
    private Order order;
    private Product product;
    private int quantity;
    private double price;

    public OrderLine(int id, Order order, Product product, int quantity, double price){
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return Double.valueOf(String.format("%.2f", price));
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public JSONObject toJSONObject(){
        HashMap<String,Object> params = new HashMap<String,Object>();
        if (this.order != null) params.put("order",String.valueOf(this.order.getId()));
        if (this.product != null) params.put("product", String.valueOf(this.product.getId()));
        if (this.quantity > 0) params.put("quantity", String.valueOf(this.quantity));
        if (this.price > 0) params.put("price", String.valueOf(price));
        return new JSONObject(params);
    }
}
