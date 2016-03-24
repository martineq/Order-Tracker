package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Services.Interface.IUserService;

/**
 * Created by Uriel on 23-Mar-16.
 */
public class UserService implements IUserService {

    public String ValidateUser(String username, String password){
        //checkear contra el server
        return Constants.PASSWORD_INVALID;
    }
}
