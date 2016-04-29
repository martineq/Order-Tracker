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
    private static User user = null;
    private static SessionInformation sessionInformation = null;

    public static SessionInformation InitializeSessionInformation(Context context) {
        SessionInformation.sessionInformation = new SessionInformation(context);
        SessionInformation.user = SessionInformation.sessionInformation.loadUserInformation();

        return SessionInformation.sessionInformation;
    }

    public static SessionInformation getEditor() {
        return SessionInformation.sessionInformation;
    }

    private SharedPreferences sharedPreferences;

    public SessionInformation(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static boolean isValidSession() {
        if ( user == null )
            SessionInformation.getEditor().loadUserInformation();

        return ( user.getUsername().length() != 0 ) && ( user.getPassword().length() != 0 );
    }

    public User loadUserInformation() {
        User user = new User(0, "", "" );
        this.loadUserInformation(user);
        return user;
    }

    public void loadUserInformation(User userCopy) {
        if ( user == null )
            this.loadSavedUser();

        this.copyUserInformation(userCopy);
    }

    public void updateUserInformation(User userInfo) {
        user = null;
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        try {
            if (userInfo.getId() != 0)
                editor.putInt(RestService.LOGIN_RESPONSE_ID, userInfo.getId());
        } catch (NullPointerException e) {}

        try {
            if ( userInfo.getUsername().length() != 0 )
                editor.putString(RestService.LOGIN_RESPONSE_NAME, userInfo.getUsername());
        } catch (NullPointerException e) {}

        try {
            if ( userInfo.getPassword().length() != 0 )
                editor.putString(RestService.LOGIN_PASSWORD, userInfo.getPassword());
        } catch (NullPointerException e) {}

        try {
            if ( userInfo.getToken().length() != 0 )
                editor.putString(RestService.LOGIN_TOKEN, userInfo.getToken());
        } catch (NullPointerException e) {}

        editor.commit();
    }


    public void saveUserInformation(User userInfo) {
        user = null;
        SharedPreferences.Editor editor = this.sharedPreferences.edit();

        editor.putInt(RestService.LOGIN_RESPONSE_ID, userInfo.getId());
        editor.putString(RestService.LOGIN_RESPONSE_NAME, userInfo.getUsername());
        editor.putString(RestService.LOGIN_PASSWORD, userInfo.getPassword());
        editor.putString(RestService.LOGIN_TOKEN, userInfo.getToken());

        editor.commit();
    }

    public void removeUserInformation() {
        user = null;
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(RestService.LOGIN_RESPONSE_ID);
        editor.remove(RestService.LOGIN_RESPONSE_NAME);
        editor.remove(RestService.LOGIN_PASSWORD);
        editor.remove(RestService.LOGIN_TOKEN);

        editor.commit();
    }

    private void copyUserInformation(User userCopy) {
        userCopy.setId(user.getId());
        userCopy.setUsername(user.getUsername());
        userCopy.setPassword(user.getPassword());
        userCopy.setToken(user.getToken());
    }

    private User loadSavedUser() {
        SessionInformation.user = new User(0, "", "");
        user.setId( this.sharedPreferences.getInt( RestService.LOGIN_RESPONSE_ID, 0));
        user.setUsername( this.sharedPreferences.getString( RestService.LOGIN_RESPONSE_NAME, ""));
        user.setPassword( this.sharedPreferences.getString( RestService.LOGIN_PASSWORD, ""));
        user.setToken( this.sharedPreferences.getString( RestService.LOGIN_TOKEN, ""));

        return user;
    }

    public static int getSessionID() {
        try {
            return user.getId();
        } catch (NullPointerException e ) {
            SessionInformation.sessionInformation.loadSavedUser();
            return user.getId();
        }
    }

    public static String getSessionUsername() {
        try {
            return user.getUsername();
        } catch (NullPointerException e ) {
            SessionInformation.sessionInformation.loadSavedUser();
            return user.getUsername();
        }
    }

    public static String getSessionPassword() {
        try {
            return user.getPassword();
        } catch (NullPointerException e ) {
            SessionInformation.sessionInformation.loadSavedUser();
            return user.getPassword();
        }
    }

    public static String getSessionToken() {
        try {
            return user.getToken();
        } catch (NullPointerException e ) {
            SessionInformation.sessionInformation.loadSavedUser();
            return user.getToken();
        }
    }


}
