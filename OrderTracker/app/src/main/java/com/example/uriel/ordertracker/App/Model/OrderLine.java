package com.example.uriel.ordertracker.App.Model;

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
}
