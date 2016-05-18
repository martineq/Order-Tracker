package com.example.uriel.ordertracker.App.Services.Impl;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uriel.ordertracker.App.Activities.DiaryActivity;
import com.example.uriel.ordertracker.App.Activities.OrderActivity;
import com.example.uriel.ordertracker.App.Activities.OrderHistoryActivity;
import com.example.uriel.ordertracker.App.Activities.QRReaderActivity;
import com.example.uriel.ordertracker.App.Activities.ViewMyOrderActivity;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Dto.BaseDTO;
import com.example.uriel.ordertracker.App.Model.Dto.ClientsDTO;
import com.example.uriel.ordertracker.App.Model.Dto.OrderDTO;
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
                        try {
                            ClientsDTO clientsContainer =
                                    new Gson().fromJson(response.toString(), ClientsDTO.class);
                            if(Constants.OK_RESPONSE.equals(clientsContainer.getStatus().getResult())) {
                                act.populateClients(clientsContainer.getData());
                            } else {
                                act.handleUnexpectedError(clientsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error");
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
                        try {
                            ProductDTO productsContainer =
                                    new Gson().fromJson(response.toString(), ProductDTO.class);
                            if(Constants.OK_RESPONSE.equals(productsContainer.getStatus().getResult())) {
                                context.populateBrands(productsContainer.getData());
                                context.populateProducts(productsContainer.getData(), true);
                            } else {
                                context.handleUnexpectedError(productsContainer.getStatus().getDescription());
                            }
                            context.showDownloadingCatalog(false);
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error");
                            context.showDownloadingCatalog(false);
                        }
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
        String url = Constants.sendOrderServiceUrl();

        JSONObject jsonOrder = order.toJSONObject();

        JsonObjectRequest req = new JsonObjectRequest(url, jsonOrder,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            BaseDTO baseContainer =
                                    new Gson().fromJson(response.toString(), BaseDTO.class);
                            if(Constants.OK_RESPONSE.equals(baseContainer.getStatus().getResult())) {
                                context.savedOrder();
                            } else {
                                context.handleUnexpectedError(baseContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error");
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
     * @param qr
     * @param context
     */
    @Override
    public void sendQR(final String username, final String token, final String visit_id, final String qr, final QRReaderActivity context) throws JSONException {
        String url = Constants.sendQRServiceUrl();

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("qr", qr);
        JSONObject jsonqr = new JSONObject(params);

        JsonObjectRequest req = new JsonObjectRequest(url, jsonqr,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            BaseDTO baseContainer =
                                    new Gson().fromJson(response.toString(), BaseDTO.class);
                            if(Constants.OK_RESPONSE.equals(baseContainer.getStatus().getResult())) {
                                context.validQR();
                            } else {
                                context.handleUnexpectedError(baseContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error");
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
                headers.put("visit_id", visit_id);
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
     * @param desde
     * @param hasta
     *      */
    @Override
    public void getOrderHistory(final String username, final String token, final long desde, final long hasta, final OrderHistoryActivity context) {
        String url = Constants.getOrdersServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            OrderDTO ordersContainer =
                                    new Gson().fromJson(response.toString(), OrderDTO.class);
                            if(Constants.OK_RESPONSE.equals(ordersContainer.getStatus().getResult())) {
                                context.populateOrders(ordersContainer.getData());
                            } else {
                                context.handleUnexpectedError(ordersContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("No se encontraron pedidos para las fechas seleccionadas");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ConnectionService.newTask(context.getApplicationContext()).requestServerAddress();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                headers.put("date_from", String.valueOf(desde));
                headers.put("date_upto", String.valueOf(hasta));
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void registerGcmToken(final String username, final String token, final String tokengcm, Context context) throws JSONException {
        String url = Constants.registerGcmTokenServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                headers.put("tokengcm", tokengcm);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(context).add(req);
    }
}
