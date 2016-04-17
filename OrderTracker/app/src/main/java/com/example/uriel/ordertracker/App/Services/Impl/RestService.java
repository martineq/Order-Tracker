package com.example.uriel.ordertracker.App.Services.Impl;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Activities.LogInActivity;
import com.example.uriel.ordertracker.App.Activities.OrderActivity;
import com.example.uriel.ordertracker.App.Activities.ViewMyOrderActivity;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Dto.ClientsDTO;
import com.example.uriel.ordertracker.App.Model.Dto.ProductDTO;
import com.example.uriel.ordertracker.App.Model.Order;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * @param username
     * @param password
     * @param context
     * @throws JSONException
     */
    @Override
    public void login(final String username, final String password, final LogInActivity context) throws JSONException {
        LoginService.newRequest(context).login(username, password);
    }

    /**
     * @param username
     * @param token
     * @param context
     */
    @Override
    public void getClients(final String username, final String token, final DiaryActivity act, final AppCompatActivity context){
        String url = Constants.getClientsServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        ClientsDTO clientsContainer =
                                new Gson().fromJson(response.toString(), ClientsDTO.class);
                        if(Constants.OK_RESPONSE.equals(clientsContainer.getStatus().getResult())) {
                            act.populateClients(clientsContainer.getData());
                        } else {
                            act.handleUnexpectedError(clientsContainer.getStatus().getDescription());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectionService.newTask(context.getApplicationContext()).requestServerAddress();
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

    /**
     * @param username
     * @param token
     * @param context
     */
    @Override
    public void getProducts(final String username, final String token, final OrderActivity context) {
        String url = Constants.getProductsServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        ProductDTO productsContainer =
                                new Gson().fromJson(response.toString(), ProductDTO.class);
                        if(Constants.OK_RESPONSE.equals(productsContainer.getStatus().getResult())) {
                            context.populateBrands(productsContainer.getData());
                            context.populateProducts(productsContainer.getData(), true);
                        } else {
                            context.handleUnexpectedError(productsContainer.getStatus().getDescription());
                        }
                        context.showDownloadingCatalog(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectionService.newTask(context.getApplicationContext()).requestServerAddress();
                context.showDownloadingCatalog(false);
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

    /**
     * @param username
     * @param token
     * @param order
     * @param context
     */
    @Override
    public void sendOrder(final String username, final String token, final Order order, final ViewMyOrderActivity context){
        /*String url = Constants.getClientsServiceUrl();

        JSONObject jsonOrder = order.toJSONObject();

        JsonObjectRequest req = new JsonObjectRequest(url, jsonOrder,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        BaseDTO baseContainer =
                                new Gson().fromJson(response.toString(), BaseDTO.class);
                        if(Constants.OK_RESPONSE.equals(baseContainer.getStatus().getResult())) {
                            context.savedOrder();
                        } else {
                            context.handleUnexpectedError(baseContainer.getStatus().getDescription());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectionService.newTask(context.getApplicationContext()).requestServerAddress();
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
        Request response = Volley.newRequestQueue(context).add(req);*/
    }
}
