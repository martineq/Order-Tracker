package com.example.uriel.ordertracker.App.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import com.example.uriel.ordertracker.App.Services.Impl.RestService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SessionInformation {

    public static SessionInformation sessionInformation = null;

    public static SessionInformation InitializeSessionInformation(Context context) {
        SessionInformation.sessionInformation = new SessionInformation(context);
        return SessionInformation.sessionInformation;
    }

    public static SessionInformation getEditor() {
        return SessionInformation.sessionInformation;
    }


    private SharedPreferences sharedPreferences;
    private Set<String> options;

    public SessionInformation(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.loadOptions();
    }

    public void loadOptions() {
        this.options = new HashSet<>();

        this.options.add(RestService.LOGIN_RESPONSE_ID);
        this.options.add(RestService.LOGIN_RESPONSE_NAME);
        this.options.add(RestService.LOGIN_PASSWORD);
        this.options.add(RestService.LOGIN_TOKEN);
    }

    public User loadUserInformation() {
        User user = new User(0,"","");
        this.loadUserInformation(user);
        return user;
    }

    public void loadUserInformation(User user) {
        for (String option : this.options) {
            this.loadUserInformation(user, option);
        }
    }

    public void saveUserInformation(User user) {
        for (String option : this.options) {
            this.saveUserInformation(user, option);
        }
    }

    public void saveUserInformation(User user, String option) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        if (option.compareTo(RestService.LOGIN_RESPONSE_ID) == 0)
            editor.putInt(option, user.getId());

        if (option.compareTo(RestService.LOGIN_RESPONSE_NAME) == 0)
            editor.putString(option, user.getUsername());

        if (option.compareTo(RestService.LOGIN_PASSWORD) == 0)
            editor.putString(option, user.getPassword());

        if (option.compareTo(RestService.LOGIN_TOKEN) == 0)
            editor.putString(option, user.getToken());

        editor.commit();
    }

    public void loadUserInformation(User user, String option) {

        if (option.compareTo(RestService.LOGIN_RESPONSE_ID) == 0)
            this.setInteger(user, option);

        if (option.compareTo(RestService.LOGIN_RESPONSE_NAME) == 0)
            user.setUsername(this.sharedPreferences.getString(option, ""));

        if (option.compareTo(RestService.LOGIN_PASSWORD) == 0)
            user.setPassword(this.sharedPreferences.getString(option, ""));

        if (option.compareTo(RestService.LOGIN_TOKEN) == 0)
            user.setToken(this.sharedPreferences.getString(option, ""));
    }

    public void removeUserInformation() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        for (String option : this.options)
            editor.remove(option);
        editor.commit();
    }

    private void setInteger(User user, String option) {
        try {
            user.setId(this.sharedPreferences.getInt(option, -1));
        }
        catch (ClassCastException e) {
            user.setId(-1);
        }
    }

}
