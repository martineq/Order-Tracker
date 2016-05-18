package com.example.uriel.ordertracker.App.Services.Impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Uriel on 17-May-16.
 */
public class MiInstanceIDListenerService extends InstanceIDListenerService {

    private Context context;

    public MiInstanceIDListenerService(Context context){
        this.context = context;
    }

    /**
     * Se llama cuando Gcm servers actualizan el registration token, principalemnte por motivos  de seguridad
     */
    @Override
    public void onTokenRefresh() {
        //obtener nuevamente el token y enviarlo a la aplicacion servidor
        RegitroGcmcAsyncTask regitroGcmcAsyncTask = new RegitroGcmcAsyncTask();
        regitroGcmcAsyncTask.execute();
    }

    private class RegitroGcmcAsyncTask extends AsyncTask<String , String, Object>
    {

        @Override
        protected Object doInBackground(String ... params) {

            try {
                SharedPreferences sharedPref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                String username = sharedPref.getString(RestService.LOGIN_RESPONSE_NAME, "");
                String token = sharedPref.getString(RestService.LOGIN_TOKEN, "");

                String registrationToken = Helpers.ObtenerRegistrationTokenEnGcm(context);

                Helpers.RegistrarseEnAplicacionServidor(context,registrationToken, username, token);
                return "";
            }
            catch (Exception ex){
                return ex;
            }
        }

        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(Object result)
        {
            if(result instanceof  String)
            {
                String resulatado = (String)result;
            }
            else if (result instanceof Exception)//Si el resultado es una Excepcion..hay error
            {
                Exception ex = (Exception) result;
            }
        }

    }

}
