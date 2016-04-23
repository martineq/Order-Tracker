package com.example.uriel.ordertracker.App.Activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.uriel.ordertracker.App.Services.Impl.ManageGoogleRoutes;
import com.example.uriel.ordertracker.App.Services.Interface.OnTaskCompleted;
import com.example.uriel.ordertracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteActivity extends DrawerActivity implements OnTaskCompleted {

    MyMapFragment myMapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Actividad diaria");
        configDrawerAfterCreate(savedInstanceState);

        ArrayList<String> adresses = (ArrayList<String>) getIntent().getExtras().getSerializable("adresses");

        if (adresses.size() > 0) {
            //Disparamos la tarea asíncrona definida en la clase ManageGoogleRoutes
            //pasando los puntos A y B para el calculo de la ruta y la obtención de
            //las coordenadas que nos permitirán dibujar la ruta a seguir.
            new ManageGoogleRoutes(this).execute(adresses);
        } else {
        }

    }

    @Override
    public void onTaskCompleted(List<List<HashMap<String, String>>> listLatLong) {
        myMapFrag = new MyMapFragment();
        myMapFrag.setRoutes(listLatLong);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.container, myMapFrag);
        transaction.commit();
    }
}
