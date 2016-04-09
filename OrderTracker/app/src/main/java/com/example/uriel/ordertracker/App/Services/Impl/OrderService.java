package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Activities.ViewMyOrderActivity;
import com.example.uriel.ordertracker.App.Model.Order;
import com.example.uriel.ordertracker.App.Services.Interface.IOrderService;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;

import org.json.JSONException;

/**
 * Created by Uriel on 09-Apr-16.
 */
public class OrderService implements IOrderService {
    private final IRestService restService = RestService.getInstance();

    public void sendOrder(String username, String token, Order order, ViewMyOrderActivity context) throws JSONException {
        try {
            restService.sendOrder(username, token, order, context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
