package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Activities.LogInActivity;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;
import com.example.uriel.ordertracker.App.Services.Interface.IUserService;

import org.json.JSONException;

/**
 * Created by Uriel on 23-Mar-16.
 */
public class UserService implements IUserService {

    private final IRestService restService = RestService.getInstance();

    public void validateUser(String username, String password, final LogInActivity context){
        try {
            restService.login(username, password, context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User getById(int id){
        User user = new User(1, "Usuario 1", "1234");

        return user;
    }

    public User getByUsername(String username){
        User user = new User(1, "Usuario 1", "1234");

        return user;
    }
}
