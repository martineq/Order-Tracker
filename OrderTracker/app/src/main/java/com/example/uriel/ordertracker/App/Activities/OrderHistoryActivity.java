package com.example.uriel.ordertracker.App.Activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Dto.PlainOrder;
import com.example.uriel.ordertracker.App.Model.Dto.RVAdapter;
import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.SessionInformation;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Impl.OrderService;
import com.example.uriel.ordertracker.App.Services.Interface.IOrderService;
import com.example.uriel.ordertracker.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderHistoryActivity extends DrawerActivity {

    RecyclerView rv;
    TextView textDesde;
    TextView textHasta;
    DatePicker calendarDesde;
    DatePicker calendarHasta;
    Button buscarButton;
    Button volverButton;
    IOrderService orderService;
    String username;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Reporte de pedidos");
        configDrawerAfterCreate(savedInstanceState);

        User user = SessionInformation.getEditor().loadUserInformation();
        username = user.getUsername();
        token = user.getToken();

        orderService = new OrderService();

        textDesde = (TextView)findViewById(R.id.desde);
        textHasta = (TextView)findViewById(R.id.hasta);
        calendarDesde = (DatePicker)findViewById(R.id.calendarDesde);
        calendarHasta = (DatePicker)findViewById(R.id.calendarHasta);
        buscarButton = (Button)findViewById(R.id.buscarButton);
        volverButton = (Button)findViewById(R.id.volverButton);

        rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        final OrderHistoryActivity context = this;
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long fechaDesde = getDate(calendarDesde).getTime();
                long fechaHasta = getDate(calendarHasta).getTime();

                try {
                    orderService.getOrderHistory(username, token, fechaDesde, fechaHasta, context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDesde.setVisibility(View.VISIBLE);
                textHasta.setVisibility(View.VISIBLE);
                calendarDesde.setVisibility(View.VISIBLE);
                calendarHasta.setVisibility(View.VISIBLE);
                buscarButton.setVisibility(View.VISIBLE);

                rv.setVisibility(View.INVISIBLE);
                volverButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private Date getDate(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public void populateOrders(ArrayList<PlainOrder> orders){
        textDesde.setVisibility(View.INVISIBLE);
        textHasta.setVisibility(View.INVISIBLE);
        calendarDesde.setVisibility(View.INVISIBLE);
        calendarHasta.setVisibility(View.INVISIBLE);
        buscarButton.setVisibility(View.INVISIBLE);

        RVAdapter adapter = new RVAdapter(orders);
        rv.setAdapter(adapter);
        rv.setVisibility(View.VISIBLE);
        volverButton.setVisibility(View.VISIBLE);
    }

    public void handleUnexpectedError(String error){
        SweetAlertDialog dialog = Helpers.getErrorDialog(this, "Error en la consulta. Intente nuevamente", error);
        dialog.show();
    }
}
