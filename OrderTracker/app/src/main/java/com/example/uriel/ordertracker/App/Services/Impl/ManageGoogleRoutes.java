package com.example.uriel.ordertracker.App.Services.Impl;

import android.os.AsyncTask;
import android.util.Log;

import com.example.uriel.ordertracker.App.Services.Interface.OnTaskCompleted;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ManageGoogleRoutes extends AsyncTask<ArrayList<String>, Integer, List<List<HashMap<String, String>>>> {
    public static String APP_TAG = "ManageGoogleRoutes ";
    public static String END_POINT = "http://maps.googleapis.com/maps/api/directions/json?";
    private OnTaskCompleted listener;

    public ManageGoogleRoutes(OnTaskCompleted listener){
        //Al instanciar la clase le pasamos un objeto que implemente la interfaz OnTaskCompleted
        //Para de esta manera poder regresar el control de este AsynTask a la Main Activity
        this.listener=listener;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(ArrayList<String>... params) {
        return getRutasGoogle(params[0]);
    }

    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        if (result != null) {
            listener.onTaskCompleted(result);
        }
    }

    private List<List<HashMap<String, String>>> getRutasGoogle(ArrayList<String> points) {
        //Se define el objeto URL
        JSONObject jsonObj = null;
        String  reqtUrl = null;
        BufferedReader in = null;
        List<List<HashMap<String, String>>> routes = null;

        try {
            for(int i = 0; i < points.size() - 1; i++){
                /*http://maps.googleapis.com/maps/api/directions/json?
                 * origin=Calle Aribau, 185
                 * destination=Calle Industria, 104
                 * sensor=false
                 * mode=transit
                url = new URL(END_POINT);*/

                //Creamos un objeto Cliente HTTP para manejar la peticion al servidor
                HttpClient httpClient = new DefaultHttpClient();

                //Configuramos los parametos que vaos a enviar con la peticion HTTP POST
                List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("origin", points.get(0)));
                params.add(new BasicNameValuePair("destination", points.get(1)));
                params.add(new BasicNameValuePair("sensor", "false"));
                params.add(new BasicNameValuePair("mode", "driving"));

                String strParams = URLEncodedUtils.format(params, "utf-8");
                reqtUrl = END_POINT + strParams;
                //Creamos objeto para armar peticion de tipo HTTP GET
                HttpGet getReq = new HttpGet(reqtUrl);
                //getReq.setHeader("Content-type", "application/json");

                //Se ejecuta el envio de la peticion y se espera la respuesta de la misma.
                HttpResponse response = httpClient.execute(getReq);
                Log.w(APP_TAG, response.getStatusLine().toString());

                //Obtengo el contenido de la respuesta en formato InputStream Buffer y la paso a formato String
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }

                String strJSON = sb.toString();
                jsonObj = new JSONObject(strJSON);
                routes = parse(jsonObj);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return routes;
    }


    public List<List<HashMap<String,String>>> parse(JSONObject jObject){
        //Este método PARSEA el JSONObject que retorna del API de Rutas de Google devolviendo
        //una lista del lista de HashMap Strings con el listado de Coordenadas de Lat y Long,
        //con la cual se podrá dibujar pollinas que describan la ruta entre 2 puntos.
        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>();
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }
        return routes;
    }

    /**
     * Method to decode polyline points
     * Cotesía de: jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

}