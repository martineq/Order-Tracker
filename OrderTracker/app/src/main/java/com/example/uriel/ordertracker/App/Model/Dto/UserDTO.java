package com.example.uriel.ordertracker.App.Model.Dto;

import com.example.uriel.ordertracker.App.Model.User;

/**
 * Created by Uriel on 29-Mar-16.
 */
public class UserDTO extends BaseDTO{
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
