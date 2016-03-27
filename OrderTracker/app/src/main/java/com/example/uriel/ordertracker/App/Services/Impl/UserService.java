package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Interface.IUserService;

/**
 * Created by Uriel on 23-Mar-16.
 */
public class UserService implements IUserService {

    public String validateUser(String username, String password){
        //checkear contra el server
        return Constants.USER_OK;
    }

    public User getById(int id){
        User user = new User();
        user.setId(1);
        user.setUsername("prueba");
        user.setPassword("1234");

        return user;
    }

    public User getByUsername(String username){
        User user = new User();
        user.setId(1);
        user.setUsername("prueba");
        user.setPassword("1234");

        return user;
    }
}
