package com.example.uriel.ordertracker.App.Services.Interface;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.uriel.ordertracker.App.Activities.DiaryActivity;

/**
 * Created by Uriel on 25-Mar-16.
 */
public interface IClientService {
    void getBySeller(String username, String token,  final DiaryActivity act, final AppCompatActivity context);
}
