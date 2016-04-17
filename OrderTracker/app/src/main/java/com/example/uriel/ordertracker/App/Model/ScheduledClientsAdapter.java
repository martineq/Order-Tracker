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

public class ScheduledClientsAdapter extends ArrayAdapter<Client> {
    private final Activity context;
    private final List<Client> clients;

    public ScheduledClientsAdapter(Activity context, List<Client> clients) {
        super(context, R.layout.list_client, clients);

        this.context = context;
        this.clients = clients;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_client, null, true);

        String min;

        Client client = clients.get(position);

        TextView idText = (TextView) rowView.findViewById(R.id.idText);
        TextView nameText = (TextView) rowView.findViewById(R.id.nameText);
        TextView hourText = (TextView) rowView.findViewById(R.id.hourText);
        View rectangle = rowView.findViewById(R.id.rectangle);

        idText.setText(String.valueOf(client.getId()));
        nameText.setText(client.getName());
        String state = client.getState();

        min=String.valueOf(client.getDate().getMinutes());
        if(min.length()==1) {
            min=min+"0";
        }
        String hour=String.valueOf((client.getDate().getHours()))+":"+min;
        hourText.setText(hour);

        if(state.equals(Constants.VISITADO)){
            rectangle.setBackgroundColor(Color.GREEN);
        }else if(state.equals(Constants.PENDIENTE)){
            rectangle.setBackgroundColor(Color.YELLOW);
        }else {
            rectangle.setBackgroundColor(Color.RED);
        }

        return rowView;
    }
}
