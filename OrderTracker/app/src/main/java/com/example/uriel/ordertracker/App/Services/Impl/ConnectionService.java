package com.example.uriel.ordertracker.App.Services.Impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Patterns;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uriel.ordertracker.App.Model.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ConnectionService implements Response.Listener<String>, Response.ErrorListener {

    public enum URLServerSource { START, STATIC_HOST, LAST_USED, IP_REQUESTED };

    public static URLServerSource urlServerSource = URLServerSource.START;
    public static long lastServerIPRequest = 0;
    private ServerURLUpdateListener listener;

    private Context context;

    // ConnectionService should be used as task, created used and discarded not as a state of an object.
    public static ConnectionService newTask(Context context) {
        ConnectionService.writeIPtoFile(Constants.BASE_URL, false);
        return new ConnectionService(context);
    }

    public ConnectionService.URLServerSource  requestServerAddress() {
        return this.requestServerAddress(null);
    }

    public ConnectionService.URLServerSource  requestServerAddress(ServerURLUpdateListener listener) {

        ConnectionService.URLServerSource urlServerSource = ConnectionService.urlServerSource;

        if ( urlServerSource == URLServerSource.START ) {
            ConnectionService.urlServerSource = URLServerSource.LAST_USED;
            this.loadLastSavedServerAddress();
            this.clean();
        }

        else if ( urlServerSource == URLServerSource.LAST_USED ) {
            readIpFromFile();
            ConnectionService.urlServerSource = URLServerSource.STATIC_HOST;
            this.clean();
        }

        else {
            ConnectionService.urlServerSource = URLServerSource.IP_REQUESTED;
            this.listener = listener;
            this.requestServerURLUpdate();
        }

        return ConnectionService.urlServerSource;
    }

    private ConnectionService(Context context) {
        this.context = context;
    }

    private boolean loadLastSavedServerAddress() {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String serverIP = sharedPreferences.getString(Constants.SERVER_URL_PROPERTY, Constants.BASE_URL);
        boolean ipAdressModified = false;

        if ( serverIP.compareTo(Constants.BASE_URL) != 0 ) {
            Constants.BASE_URL = serverIP;
            ipAdressModified = true;
            this.clean();
        }

        return ipAdressModified;
    }

    private boolean requestServerURLUpdate() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        long now = new Date().getTime();

        try {
            if (connectivityManager.getActiveNetworkInfo().isConnected() == true) {
                if ((now - lastServerIPRequest) > Constants.MINIMUM_REQUEST_IP_TIME) {
                lastServerIPRequest = now;
                    this.updateServerURL();
                } else this.clean();
            }
        } catch ( NullPointerException e) {
            this.clean();
        }

        return ( now - lastServerIPRequest ) == 0;
    }

    private void updateServerURL() {
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(new StringRequest(Request.Method.GET, Constants.SERVER_IP_REQUEST, this, this));
    }

    @Override
    public void onResponse(String response) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        try {
            String ipAddress = response.substring(0, response.indexOf('\n'));
            if ( Patterns.IP_ADDRESS.matcher(ipAddress).matches() == true ) {
                Constants.BASE_URL = ipAddress;
                ConnectionService.writeIPtoFile(ipAddress, true);
                sharedPreferences.edit().putString(Constants.SERVER_URL_PROPERTY,Constants.BASE_URL).commit();
            }
        }
        catch (IndexOutOfBoundsException e) {}

        finally {
            this.callListener();
            this.clean();
        }
    }

    private static void writeIPtoFile(String ip, boolean eraseExistingFile) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), Constants.PROGRAM_FOLDER);
            file.mkdirs();

            File ipFile = new File(file.getPath() +"/"+ Constants.IP_FILE);

            if ( eraseExistingFile == true || ipFile.exists() == false ) {
                ipFile.createNewFile();

                FileWriter fileWriter = new FileWriter(ipFile);
                fileWriter.write(ip);
                fileWriter.close();
            }
        }
        catch (NullPointerException e) {}
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
    }

    private static void readIpFromFile() {
        try {
            File fileIP = new File(Environment.getExternalStorageDirectory()+"/"+Constants.PROGRAM_FOLDER+Constants.IP_FILE);
            BufferedReader br = new BufferedReader(new FileReader(fileIP));
            Constants.BASE_URL = br.readLine();
            br.close();
        }
        catch (NullPointerException e) {}
        catch (FileNotFoundException e) {}
        catch (IOException e) {}
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        urlServerSource = URLServerSource.START;
        this.callListener();
        this.clean();
    }

    private void callListener() {
        try{
            this.listener.urlUpdated();
        }
        catch(NullPointerException e) {}
    }

    private void clean() {
        this.context = null;
        this.listener = null;
    }
}
