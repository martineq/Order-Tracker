package com.example.uriel.ordertracker.App.Services.Impl;

import android.support.v4.app.FragmentActivity;

import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;

/**
 * Created by Uriel on 25-Mar-16.
 */
public class ClientService implements IClientService {

    private final IRestService restService = RestService.getInstance();

    public Client getById(int clientId){
        switch (clientId){
            case 1:
                return new Client(1, "Aldo Proieto", "Av Paseo colon 850, CABA", Constants.VISITADO);
            case 2:
                return new Client(2, "Federico Bulos", "Av Gral Las Heras 2214, CABA", Constants.NO_VISITADO);
            case 3:
                return new Client(3, "Sebestian Vignolo", "Lavalleja 173, CABA", Constants.VISITADO);
            case 4:
                return new Client(4, "Pablo Ladaga", "Av Scalabrini Ortiz 2417, CABA", Constants.PENDIENTE);
            case 5:
                return new Client(5, "Juan Carlos Pellegrini", "Av Angel Gallardo 265, CABA", Constants.VISITADO);
            case 6:
                return new Client(6, "Emiliano Raggi", "Av Cnel Apolinario Figueroa 1043, CABA", Constants.PENDIENTE);
            case 7:
                return new Client(7, "Roberto Leto", "Paysandu 1459, CABA", Constants.NO_VISITADO);
            case 8:
                return new Client(8, "Jose Amado", "Av Cordoba 1450, CABA", Constants.NO_VISITADO);
            case 9:
                return new Client(9, "Daniel Retamozo", "Av Corrientes 850, CABA", Constants.VISITADO);
            case 10:
                return new Client(10, "Juan Fernandez", "Araoz 657, CABA", Constants.NO_VISITADO);
            case 11:
                return new Client(11, "Renato De La Paulera", "Humboldt 1100, CABA", Constants.VISITADO);
            case 12:
                return new Client(12, "Walter Zafarian", "Sarmiento 350, CABA", Constants.PENDIENTE);
            case 13:
                return new Client(13, "Martin Liberman", "Lacarra 1000, CABA", Constants.VISITADO);
            case 14:
                return new Client(14, "Matias Garcia", "Jose Hernandez 420, CABA", Constants.PENDIENTE);
            case 15:
                return new Client(15, "Diego Fuks", "Av Cabildo 2530, CABA", Constants.NO_VISITADO);
            case 16:
                return new Client(16, "Marcelo Grandio", "Roosevelt 1655, CABA", Constants.NO_VISITADO);
        }
        return null;
    }

    public void getBySeller(String username, String token, final DiaryActivity.OffRouteFragment fragment, FragmentActivity context){
        restService.getClients(username, token, fragment, context);

        /*ArrayList<Client> clients = new ArrayList<>();

        Client client1 = new Client(1, "Cliente 1", "Av Paseo colon 850, CABA", Constants.VISITADO);
        Client client2 = new Client(2, "Cliente 2", "Av Gral Las Heras 2214, CABA", Constants.NO_VISITADO);

        clients.add(client1);
        clients.add(client2);

        return clients;*/
    }
}
