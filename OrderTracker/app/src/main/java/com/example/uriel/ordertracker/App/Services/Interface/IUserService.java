package com.example.uriel.ordertracker.App.Services.Interface;

import com.example.uriel.ordertracker.App.Model.User;

/**
 * Created by Uriel on 23-Mar-16.
 */
public interface IUserService {
    String validateUser(String username, String password);
    User getById(int id);
    User getByUsername(String username);
}
