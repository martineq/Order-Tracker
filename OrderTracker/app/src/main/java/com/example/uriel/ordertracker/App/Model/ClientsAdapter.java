package com.example.uriel.ordertracker.App.Model;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.uriel.ordertracker.R;

import java.util.List;

/**
 * Created by Uriel on 25-Mar-16.
 */
public class ClientsAdapter extends ArrayAdapter<Client> {
    private final Activity context;
    private final List<Client> clients;

    public ClientsAdapter(Activity context, List<Client> clients) {
        super(context, R.layout.list_client, clients);

        this.context = context;
        this.clients = clients;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_client, null, true);

        Client client = clients.get(position);

        TextView nameText = (TextView) rowView.findViewById(R.id.nameText);
        View circle = (View) rowView.findViewById(R.id.circle);

        nameText.setText(client.getName());
        String state = client.getState();
        if(state.equals(Constants.VISITADO)){
            circle.setBackgroundColor(Color.GREEN);
        }else if(state.equals(Constants.PENDIENTE)){
            circle.setBackgroundColor(Color.YELLOW);
        }else {
            circle.setBackgroundColor(Color.RED);
        }

        return rowView;
    }
}
