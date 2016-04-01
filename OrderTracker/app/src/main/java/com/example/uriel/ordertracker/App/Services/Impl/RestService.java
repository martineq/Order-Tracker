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
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Dto.ClientsDTO;
import com.example.uriel.ordertracker.App.Model.Dto.UserDTO;
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
                        ClientsDTO clientsContainer =
                                new Gson().fromJson(response.toString(), ClientsDTO.class);
                        if("OK".equals(clientsContainer.getResult())) {
                            fragment.populateClients(clientsContainer.getData());
                        } else {
                            int a = 0;
                            // Do something with userContainer.getCode() to display proper error
                        }
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
