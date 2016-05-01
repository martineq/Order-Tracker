package com.example.uriel.ordertracker.App.Model;

/**
 * Created by poly on 15/04/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Activities.DetailsActivity;
import com.example.uriel.ordertracker.App.Activities.ViewRouteActivity;
import com.example.uriel.ordertracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScheduledClientFragment extends Fragment {

    private View rootView;

    ArrayList<Client> clientList;

    String day;
    int numDay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.scheduled_client_tab_fragment, container, false);

        ArrayList<Client> clients = new ArrayList<>();
        if(clientList!=null) {
            clients = populateClients();
        }

        final HashMap<Integer, String> adresses = new HashMap<>();
        int i = 1;
        for (Client client: clients) {
            adresses.put(i, client.getAddress() + ", " + client.getCity());
            i++;
        }
        //DiaryActivity act = (DiaryActivity) this.getActivity();
        //act.setButton(adresses);

        final Activity context = this.getActivity();
        FloatingActionButton routeButton = (FloatingActionButton) rootView.findViewById(R.id.routeButton);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adresses.size() > 0) {
                    Intent intent = new Intent(context, ViewRouteActivity.class);
                    intent.putExtra("adresses", adresses);
                    startActivity(intent);
                } else {
                    SweetAlertDialog dialog = Helpers.getErrorDialog(context, "Atencion", "No hay clientes el dia seleccionado");
                    dialog.show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setTitle();
    }

    public void setTitle(){

        TextView t=(TextView)getView().findViewById(R.id.textSch);
        t.setText("Agenda para el dia " + day);

    }

    public List<Client> getList() {
        final ListView listView = (ListView) rootView.findViewById(R.id.listViewSch);
        return ((ScheduledClientsAdapter)listView.getAdapter()).getClients();
    }

    public void setList(final ArrayList<Client> clients) {
        clientList=clients;
    }

    public void setDay(String c,int num) {

        day=c;
        numDay=num;
    }

    public ArrayList<Client> populateClients() {
        final ArrayList<Client> clientList2=new ArrayList<Client>();
        if (rootView != null && clientList!=null) {
            final ListView listView = (ListView) rootView.findViewById(R.id.listViewSch);

            //Solo selecciono los clientes del dia
            for (Client cli: clientList) {
                if(cli.getDate().getDay()==numDay){
                    clientList2.add(cli);
                }
            }
            // Ordeno la lista para pasarla al fragmento
            Collections.sort(clientList2);

            if(!clientList2.isEmpty()) {
                ScheduledClientsAdapter clientsAdapter = new ScheduledClientsAdapter(getActivity(), clientList2);
                listView.setAdapter(clientsAdapter);

                final Activity context = getActivity();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Client client = clientList2.get(position);
                        HashMap<String, String> clientDetails = new HashMap<String, String>();
                        clientDetails.put("id", String.valueOf(client.getId()));
                        clientDetails.put("name", client.getName());
                        clientDetails.put("address", client.getAddress());
                        clientDetails.put("city", client.getCity());
                        clientDetails.put("state", client.getState());
                        clientDetails.put("date", String.valueOf(client.getLongDate()));
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra("client", clientDetails);
                        startActivity(intent);
                    }
                });
            }
        }
        return clientList2;
    }
}