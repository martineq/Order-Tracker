package com.example.uriel.ordertracker.App.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Services.Impl.ClientService;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.R;

public class ClientDetailsActivity extends AppCompatActivity {

    private IClientService clientService;
    private int clientId;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle args = getIntent().getExtras();
        clientId = args.getInt("clientId");
        clientService = new ClientService();

        client = clientService.getById(clientId);
        String name = client.getName();
        toolbar.setTitle(name);

        TextView clientAddress = (TextView) findViewById(R.id.clientAddress);
        clientAddress.setText(client.getAddress());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ir a leer qr para empezar pedido
            }
        });
    }

}
