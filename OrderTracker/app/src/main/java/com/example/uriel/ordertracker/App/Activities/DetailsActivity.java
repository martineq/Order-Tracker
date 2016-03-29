package com.example.uriel.ordertracker.App.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Client;
import com.example.uriel.ordertracker.App.Model.Constants;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Services.Impl.ClientService;
import com.example.uriel.ordertracker.App.Services.Interface.IClientService;
import com.example.uriel.ordertracker.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailsActivity extends DrawerActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private IClientService clientService;
    private int clientId;
    private Client client;
    private GoogleMap mMap;
    double latitude;
    double longitude;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle args = getIntent().getExtras();
        clientId = args.getInt("clientId");
        clientService = new ClientService();

        client = clientService.getById(clientId);
        name = client.getName();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle(name);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        View rectangle = findViewById(R.id.rectangle);
        String state = client.getState();
        if(state.equals(Constants.VISITADO)){
            rectangle.setBackgroundColor(Color.GREEN);
        }else if(state.equals(Constants.PENDIENTE)){
            rectangle.setBackgroundColor(Color.YELLOW);
        }else {
            rectangle.setBackgroundColor(Color.RED);
        }

        TextView addressText = (TextView) findViewById(R.id.addressText);
        addressText.setText(client.getAddress());
        List<Address> geocodeMatches = null;
        try {
            geocodeMatches = new Geocoder(this).getFromLocationName(client.getAddress(), 1);

            if (!geocodeMatches.isEmpty())
            {
                latitude = geocodeMatches.get(0).getLatitude();
                longitude = geocodeMatches.get(0).getLongitude();
            }
        } catch (IOException e) {
            SweetAlertDialog dialog = Helpers.getErrorDialog(this, "Error", "Ocurrio un error al obtener la ubicación. Compruebe su conexión e intente nuevamente");
            dialog.show();
        }

        final Activity context = this;
        Button pedidoButton = (Button) findViewById(R.id.pedidoButton);
        pedidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("clientId", clientId);
                intent.putExtra("clientName", name);
                startActivity(intent);
            }
        });

        configDrawerAfterCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng address = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(address).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, 16));
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
