package com.example.uriel.ordertracker.App.Services.Interface;

import android.support.v4.app.FragmentActivity;

import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Model.Client;

/**
 * Created by Uriel on 25-Mar-16.
 */
public interface IClientService {
    Client getById(int clientId);
    void getBySeller(String username, String token, DiaryActivity.OffRouteFragment fragment, FragmentActivity context);
}
