package com.example.uriel.ordertracker.App.Services.Interface;

import com.example.uriel.ordertracker.App.Activities.OrderHistoryActivity;
import com.example.uriel.ordertracker.App.Activities.QRReaderActivity;
import com.example.uriel.ordertracker.App.Activities.ViewMyOrderActivity;
import com.example.uriel.ordertracker.App.Model.Order;

import org.json.JSONException;

/**
 * Created by Uriel on 09-Apr-16.
 */
public interface IOrderService {
    void sendOrder(String username, String token, Order order, ViewMyOrderActivity context) throws JSONException;
    void sendQR(String username, String token, String client_id, String qr, QRReaderActivity context) throws JSONException;
    void getOrderHistory(String username, String token, long desde, long hasta, OrderHistoryActivity context) throws JSONException;
}
