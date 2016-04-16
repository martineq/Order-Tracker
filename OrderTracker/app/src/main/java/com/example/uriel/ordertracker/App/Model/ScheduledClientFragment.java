package com.example.uriel.ordertracker.App.Model;

/**
 * Created by poly on 15/04/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Activities.DetailsActivity;
import com.example.uriel.ordertracker.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduledClientFragment extends Fragment {

    private View rootView;

    ArrayList<Client> clientList;

    String day;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.scheduled_client_tab_fragment, container, false);

        if(clientList!=null) {
            populateClients();
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setTitle();
    }

    public void setTitle(){

        TextView t=(TextView)getView().findViewById(R.id.textSch);
        t.setText("Agenda para el dia "+day);

    }

    public void setList(final ArrayList<Client> clients) {
        clientList=clients;
    }

    public void setText(String c) {
        day=c;
    }



    public void populateClients() {
        if (rootView != null && clientList!=null) {
            final ListView listView = (ListView) rootView.findViewById(R.id.listViewSch);

            //TODO aca solo deberia pasar los clientes del dia
           ScheduledClientsAdapter clientsAdapter = new ScheduledClientsAdapter(getActivity(), clientList);
            listView.setAdapter(clientsAdapter);

            final Activity context = getActivity();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Client client = clientList.get(position);
                    HashMap<String, String> clientDetails = new HashMap<String, String>();
                    clientDetails.put("id", String.valueOf(client.getId()));
                    clientDetails.put("name", client.getName());
                    clientDetails.put("address", client.getAddress());
                    clientDetails.put("city", client.getCity());
                    clientDetails.put("state", client.getState());
                    clientDetails.put("visitDate", String.valueOf(client.getVisitDate()));
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("client", clientDetails);
                    startActivity(intent);
                }
            });
        }
    }
}