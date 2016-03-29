package com.example.uriel.ordertracker.App.Model.Dto;

import com.example.uriel.ordertracker.App.Model.Client;

import java.util.ArrayList;

/**
 * Created by Uriel on 29-Mar-16.
 */
public class ClientsDTO extends BaseDTO{
    private ArrayList<Client> data;

    public ArrayList<Client> getData() {
        return data;
    }

    public void setData(ArrayList<Client> data) {
        this.data = data;
    }
}
