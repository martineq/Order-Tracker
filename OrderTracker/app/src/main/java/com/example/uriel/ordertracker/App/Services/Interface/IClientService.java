package com.example.uriel.ordertracker.App.Services.Interface;

import com.example.uriel.ordertracker.App.Model.Client;

import java.util.ArrayList;

/**
 * Created by Uriel on 25-Mar-16.
 */
public interface IClientService {
    Client getById(int clientId);
    ArrayList<Client> getBySeller(int sellerId);
}
