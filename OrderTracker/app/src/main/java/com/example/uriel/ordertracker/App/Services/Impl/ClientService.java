package com.example.uriel.ordertracker.App.Services.Impl;

import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;

import java.util.ArrayList;

/**
 * Created by Uriel on 25-Mar-16.
 */
public class ClientService implements IClientService {

    public Client getById(int clientId){
        if(clientId == 1){
            return new Client(1, "Cliente 1", "Av Paseo colon 850, CABA", Constants.VISITADO);
        }else {
            return new Client(2, "Cliente 2", "Av Gral Las Heras 2214, CABA", Constants.NO_VISITADO);
        }
    }

    public ArrayList<Client> getBySeller(int sellerId){
        ArrayList<Client> clients = new ArrayList<>();

        Client client1 = new Client(1, "Cliente 1", "Av Paseo colon 850, CABA", Constants.VISITADO);
        Client client2 = new Client(2, "Cliente 2", "Av Gral Las Heras 2214, CABA", Constants.NO_VISITADO);

        clients.add(client1);
        clients.add(client2);

        return clients;
    }
}
