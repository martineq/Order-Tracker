package com.example.uriel.ordertracker.App.Services.Interface;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Activities.OrderActivity;
import com.example.uriel.ordertracker.App.Activities.OrderHistoryActivity;
import com.example.uriel.ordertracker.App.Activities.QRReaderActivity;
import com.example.uriel.ordertracker.App.Activities.ReportActivity;
import com.example.uriel.ordertracker.App.Activities.ViewMyOrderActivity;
import com.example.uriel.ordertracker.App.Model.Order;

import org.json.JSONException;

/**
 * Created by Uriel on 29-Mar-16.
 */
public interface IRestService {
    public final static String RESPONSE_CODE = "com.example.uriel.ordertracker.App.Services.RestService.CODE";
    public final static String LOGIN_RESPONSE_ID = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_RESPONSE_ID";
    public final static String LOGIN_RESPONSE_NAME = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_RESPONSE_NAME";
    public final static String LOGIN_TOKEN = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_TOKEN";
    public final static String LOGIN_FULL_NAME = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_FULL_NAME";
    public final static String LOGIN_PASSWORD = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_PASSWORD";
    public final static String LOGIN_LOCATION = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_LOCATION";
    public final static String LOGIN_IMAGE = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_IMAGE";
    public final static String CHAT_RECEPTOR_USERNAME = "com.example.uriel.ordertracker.App.Services.RestService.CHAT_RECEPTOR_USERNAME";
    public final static String CHAT_RECEPTOR_FULLNAME = "com.example.uriel.ordertracker.App.Services.RestService.CHAT_RECEPTOR_FULLNAME";
    public final static String CHAT_RECEPTOR_LOCATION = "com.example.uriel.ordertracker.App.Services.RestService.CHAT_RECEPTOR_LOCATION";
    public final static String CHAT_RECEPTOR_STATE = "com.example.uriel.ordertracker.App.Services.RestService.CHAT_RECEPTOR_STATE";

    void getClients(final String username, final String token, final DiaryActivity act, final AppCompatActivity context);
    void sendOrder(final String username, final String token, final Order order, final ViewMyOrderActivity context) throws JSONException;
    void getProducts(final String username, final String token, final OrderActivity context) throws JSONException;
    void sendQR(final String username, final String token, final String visit_id, final String qr, final QRReaderActivity context) throws JSONException;
    void getOrderHistory(final String username, final String token, final long desde, final long hasta, final OrderHistoryActivity context) throws JSONException;
    void registerGcmToken(final String username, final String token, final String tokengcm, final Context context) throws JSONException;
    void getReport(final String username, final String token, final ReportActivity context) throws JSONException;
}
