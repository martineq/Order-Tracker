package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 23-Mar-16.
 */
public class Constants {
    //respuesta de login
    public static String USER_OK = "USER_OK";
    public static String USER_INVALID = "USER_INVALID";
    public static String PASSWORD_INVALID = "PASSWORD_INVALID";

    //estados de clientes
    public static String VISITADO = "VISITADO";
    public static String PENDIENTE = "PENDIENTE";
    public static String NO_VISITADO = "NO_VISITADO";

    //RestApi
    public static final String BASE_URL = "190.230.5.13";
    //public static String BASE_URL = "";
    public static final String LOCALHOST_PORT = ":8080/";
    public static final String HTTP = "http://";
    public static final String API = "api/";
    public static final String LOGIN_SERVICE = "login";
    public static final String LOGOUT_SERVICE = "logout";
    public static final String CLIENT_SERVICE = "client/list";
    public static final String CONVERSATION_SERVICE = "conversation";
    public static final String MESSAGE_SERVICE = "message";
    public static final String SHARED_PREFERENCES = "commonKey";

    public static String getLoginServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + LOGIN_SERVICE); }
    public static String getLogoutServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + LOGOUT_SERVICE); }
    public static String getClientsServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + CLIENT_SERVICE); }
    public static String getConversationServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + CONVERSATION_SERVICE); }
    public static String getMessageServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + MESSAGE_SERVICE); }

    //Response
    public static final String ERROR_RESPONSE = "ERROR";
    public static final String OK_RESPONSE = "OK";

    public static final int INVALID_USERNAME = 1;
    public static final int INVALID_PASSWORD = 2;
    public static final int INVALID_TOKEN = 3;
    public static final int ERROR_SEND_MESSAGE = 4;
    public static final int ERROR_USER_PROFILE_DOESNT_EXISTS = 5;
    public static final int USERNAME_ALREADY_EXISTS = 6;
    public static final int NO_PASSWORD = 7;
    public static final int NO_USERNAME = 8;


}
