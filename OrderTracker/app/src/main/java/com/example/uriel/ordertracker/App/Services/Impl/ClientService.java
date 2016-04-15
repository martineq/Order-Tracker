package com.example.uriel.ordertracker.App.Services.Impl;

import android.support.v4.app.FragmentActivity;

import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;

/**
 * Created by Uriel on 25-Mar-16.
 */
public class ClientService implements IClientService {

    private final IRestService restService = RestService.getInstance();

    public void getBySeller(String username, String token, final DiaryActivity.OffRouteFragment fragment, FragmentActivity context){
        restService.getClients(username, token, fragment, context);
    }
}
