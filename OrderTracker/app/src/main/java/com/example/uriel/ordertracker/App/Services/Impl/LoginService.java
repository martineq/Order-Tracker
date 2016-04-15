package com.example.uriel.ordertracker.App.Services.Impl;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uriel.ordertracker.App.Activities.LogInActivity;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Dto.UserDTO;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by martin on 15/04/16.
 */
interface ServerURLUpdateListener {
    void urlUpdated();
}

public class LoginService implements Response.Listener<JSONObject>, Response.ErrorListener, ServerURLUpdateListener {

    private LogInActivity logInActivity;
    private JsonObjectRequest jsonObjectRequest;
    private String username;
    private String password;
    private boolean waitingUpdate;
    private ConnectionService.URLServerSource urlServerSource;

    public static LoginService newRequest(LogInActivity logInActivity) {
        logInActivity.showProgress(true);
        return new LoginService(logInActivity);
    }

    private LoginService(LogInActivity logInActivity) {
        this.logInActivity = logInActivity;
    }

    public void login(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.jsonObjectRequest = null;
        this.waitingUpdate = true;
        ConnectionService.urlServerSource = ConnectionService.URLServerSource.START;
        this.loginIntent();
    }

    private JsonObjectRequest generateJsonLoginRequest() {
        String url = Constants.getLoginServiceUrl();

        return new JsonObjectRequest(url, null, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("username", username);
                headers.put("password", password);
                return headers;
            }
        };

        /* jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); */
    }

    private void loginIntent() {
        urlServerSource = ConnectionService.newTask(logInActivity.getApplicationContext()).requestServerAddress(this);

        this.jsonObjectRequest = this.generateJsonLoginRequest();

        if ( urlServerSource != ConnectionService.URLServerSource.IP_REQUESTED || waitingUpdate == false ) {
            Volley.newRequestQueue(logInActivity).add(jsonObjectRequest);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if ( urlServerSource != ConnectionService.URLServerSource.IP_REQUESTED ) {
            this.loginIntent();
        }

        else if ( waitingUpdate == false ) {
            logInActivity.handleUnexpectedError("Error en la conexión");
            this.clearLoginActivityLink();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        UserDTO userContainer = new Gson().fromJson(response.toString(), UserDTO.class);
        if(Constants.OK_RESPONSE.equals(userContainer.getStatus().getResult())) {
            logInActivity.processLoginResponse(userContainer.getData());
        } else {
            logInActivity.handleUnexpectedError(userContainer.getStatus().getDescription());
            logInActivity.showProgress(false);
        }

        this.clearLoginActivityLink();
    }

    private void clearLoginActivityLink() {
        this.logInActivity = null;
        this.jsonObjectRequest = null;
        this.username = "";
        this.password = "";
    }

    @Override
    public void urlUpdated() {
        this.waitingUpdate = false;
        this.loginIntent();
    }
}
