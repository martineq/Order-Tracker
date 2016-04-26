package com.example.uriel.ordertracker.App.Activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;

import com.example.uriel.ordertracker.R;

public class OrderHistoryActivity extends DrawerActivity {

    DatePicker calendarDesde;
    DatePicker calendarHasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarDesde = (DatePicker)findViewById(R.id.desde);
        calendarHasta = (DatePicker)findViewById(R.id.hasta);



        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        //estas 3 lineas ponerlas cuando recibi los pedidos del server
        /*RVAdapter adapter = new RVAdapter(orders);
        rv.setAdapter(adapter);
        rv.setVisibility(View.VISIBLE);*/
    }

}
