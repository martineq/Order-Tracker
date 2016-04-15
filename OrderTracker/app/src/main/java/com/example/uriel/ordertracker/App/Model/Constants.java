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
    public static String BASE_URL = "190.31.6.157";
    public static final String SERVER_URL_PROPERTY = "SERVER_URL";
    public static final String SERVER_IP_REQUEST = "http://martinhernan.xyz/ip";
    public static final String LOCALHOST_PORT = ":8080/";
    public static final String HTTP = "http://";
    public static final String LOGIN_SERVICE = "authentication/authenticate";
    public static final String LOGOUT_SERVICE = "logout";
    public static final String CLIENT_SERVICE = "client/list";
    public static final String PRODUCT_SERVICE = "product/list";
    public static final String SHARED_PREFERENCES = "commonKey";
    public static final long MINIMUM_REQUEST_IP_TIME = 300000; // 5 min = 300s = 300000ms

    public static String getLoginServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + LOGIN_SERVICE); }
    public static String getLogoutServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + LOGOUT_SERVICE); }
    public static String getClientsServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + CLIENT_SERVICE); }
    public static String getProductsServiceUrl() { return (HTTP + BASE_URL + LOCALHOST_PORT + PRODUCT_SERVICE); }

    //Response
    public static final String ERROR_RESPONSE = "error";
    public static final String OK_RESPONSE = "ok";

    public static final int INVALID_USERNAME = 1;
    public static final int INVALID_PASSWORD = 2;
    public static final int INVALID_TOKEN = 3;
    public static final int ERROR_SEND_MESSAGE = 4;
    public static final int ERROR_USER_PROFILE_DOESNT_EXISTS = 5;
    public static final int USERNAME_ALREADY_EXISTS = 6;
    public static final int NO_PASSWORD = 7;
    public static final int NO_USERNAME = 8;

    //File
    public static final String PROGRAM_FOLDER = "OrderTracker/";
    public static final String IP_FILE = "ip.txt";
}
