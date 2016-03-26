package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;

import java.util.ArrayList;

/**
 * Created by Uriel on 25-Mar-16.
 */
public class ClientService implements IClientService {

    public ArrayList<Client> getBySeller(int sellerId){
        Client client1 = new Client();
        client1.setName("Cliente 1");
        client1.setAddress("Av Paseo colon 850, CABA");
        client1.setState(Constants.VISITADO);

        Client client2 = new Client();
        client2.setName("Cliente 2");
        client2.setAddress("Av Gral Las Heras 2214, CABA");
        client2.setState(Constants.NO_VISITADO);

        ArrayList<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        return clients;
    }
}
