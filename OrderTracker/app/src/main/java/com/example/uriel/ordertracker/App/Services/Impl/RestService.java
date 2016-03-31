package com.example.uriel.ordertracker.App.Services.Impl;

import android.support.v4.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Activities.LogInActivity;
import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Dto.UserDTO;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Uriel on 29-Mar-16.
 */
public class RestService implements IRestService {
    private static RestService ourInstance = null;

    public static RestService getInstance() {
        if(ourInstance == null) {
            ourInstance = new RestService();
        }
        return ourInstance;
    }

    protected RestService() {
    }

    /**
     * @param username
     * @param password
     * @param context
     * @throws JSONException
     */
    @Override
    public void login(final String username, final String password, final LogInActivity context) throws JSONException {
        String url = Constants.getLoginServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserDTO userContainer = new Gson().fromJson(response.toString(), UserDTO.class);
                        if("OK".equals(userContainer.getResult())) {
                            context.processLoginResponse(userContainer.getData());
                        } else {
                            context.handleUnexpectedError(userContainer.getCode());
                            context.showProgress(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError(1001);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("password", password);
                return headers;
            }
        };

        /*
        req.setRetryPolicy(new DefaultRetryPolicy(10000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                */

        // Add the request to the RequestQueue.
        Request response = Volley.newRequestQueue(context).add(req);
    }

    /**
     * @param username
     * @param token
     * @param context
     */
    @Override
    public void getClients(final String username, final String token, final DiaryActivity.OffRouteFragment fragment, final FragmentActivity context){
        String url = Constants.getClientsServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Client> clients = new ArrayList<>();

                        Client client1 = new Client(1, "Aldo Proieto", "Av Paseo colon 850, CABA", Constants.VISITADO);
                        Client client2 = new Client(2, "Federico Bulos", "Av Gral Las Heras 2214, CABA", Constants.NO_VISITADO);
                        Client client3 = new Client(3, "Sebestian Vignolo", "Lavalleja 173, CABA", Constants.VISITADO);
                        Client client4 = new Client(4, "Pablo Ladaga", "Av Scalabrini Ortiz 2417, CABA", Constants.PENDIENTE);
                        Client client5 = new Client(5, "Juan Carlos Pellegrini", "Av Angel Gallardo 265, CABA", Constants.VISITADO);
                        Client client6 = new Client(6, "Emiliano Raggi", "Av Cnel Apolinario Figueroa 1043, CABA", Constants.PENDIENTE);
                        Client client7 = new Client(7, "Roberto Leto", "Paysandu 1459, CABA", Constants.NO_VISITADO);
                        Client client8 = new Client(8, "Jose Amado", "Av Cordoba 1450, CABA", Constants.NO_VISITADO);
                        Client client9 = new Client(9, "Daniel Retamozo", "Av Corrientes 850, CABA", Constants.VISITADO);
                        Client client10 = new Client(10, "Juan Fernandez", "Araoz 657, CABA", Constants.NO_VISITADO);
                        Client client11 = new Client(11, "Renato De La Paulera", "Humboldt 1100, CABA", Constants.VISITADO);
                        Client client12 = new Client(12, "Walter Zafarian", "Sarmiento 350, CABA", Constants.PENDIENTE);
                        Client client13 = new Client(13, "Martin Liberman", "Lacarra 1000, CABA", Constants.VISITADO);
                        Client client14 = new Client(14, "Matias Garcia", "Jose Hernandez 420, CABA", Constants.PENDIENTE);
                        Client client15 = new Client(15, "Diego Fuks", "Av Cabildo 2530, CABA", Constants.NO_VISITADO);
                        Client client16 = new Client(16, "Marcelo Grandio", "Roosevelt 1655, CABA", Constants.NO_VISITADO);

                        clients.add(client1);
                        clients.add(client2);
                        clients.add(client3);
                        clients.add(client4);
                        clients.add(client5);
                        clients.add(client6);
                        clients.add(client7);
                        clients.add(client8);
                        clients.add(client9);
                        clients.add(client10);
                        clients.add(client11);
                        clients.add(client12);
                        clients.add(client13);
                        clients.add(client14);
                        clients.add(client15);
                        clients.add(client16);

                        fragment.populateClients(clients);
                        /*ClientsDTO clientsContainer =
                                new Gson().fromJson(response.toString(), ClientsDTO.class);
                        if("OK".equals(clientsContainer.getResult())) {
                            fragment.populateClients(clientsContainer.getData());
                        } else {
                            int a = 0;
                            // Do something with userContainer.getCode() to display proper error
                        }*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int a = 0;
                //handle error
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(context).add(req);
    }
}
