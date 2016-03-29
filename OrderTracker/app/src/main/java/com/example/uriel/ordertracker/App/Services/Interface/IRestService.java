package com.example.uriel.ordertracker.App.Services.Interface;

import android.support.v4.app.FragmentActivity;

import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Activities.LogInActivity;

import org.json.JSONException;

/**
 * Created by Uriel on 29-Mar-16.
 */
public interface IRestService {
    public final static String RESPONSE_CODE = "com.example.uriel.ordertracker.App.Services.RestService.CODE";
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

    void login(final String username, final String password, final LogInActivity context) throws JSONException;
    void getClients(final String username, final String token, final DiaryActivity.OffRouteFragment fragment, final FragmentActivity context);
}
