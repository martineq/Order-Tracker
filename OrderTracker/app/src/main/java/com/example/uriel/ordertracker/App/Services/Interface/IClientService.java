package com.example.uriel.ordertracker.App.Services.Interface;

import android.support.v4.app.FragmentActivity;

import com.example.uriel.ordertracker.App.Activities.DiaryActivity;

/**
 * Created by Uriel on 25-Mar-16.
 */
public interface IClientService {
    void getBySeller(String username, String token, DiaryActivity.OffRouteFragment fragment, FragmentActivity context);
}
