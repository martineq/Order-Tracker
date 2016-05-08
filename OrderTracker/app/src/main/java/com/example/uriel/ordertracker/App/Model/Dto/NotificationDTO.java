package com.example.uriel.ordertracker.App.Model.Dto;

import com.example.uriel.ordertracker.App.Model.Notification;

import java.util.ArrayList;

/**
 * Created by Uriel on 05-May-16.
 */
public class NotificationDTO extends BaseDTO{
    private ArrayList<Notification> data;

    public ArrayList<Notification> getData() {
        return data;
    }

    public void setData(ArrayList<Notification> data) {
        this.data = data;
    }
}
