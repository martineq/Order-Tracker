package com.example.uriel.ordertracker.App.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.uriel.ordertracker.App.Model.Helpers;
import com.example.uriel.ordertracker.App.Model.Report;
import com.example.uriel.ordertracker.App.Model.SessionInformation;
import com.example.uriel.ordertracker.App.Model.User;
import com.example.uriel.ordertracker.App.Services.Impl.RestService;
import com.example.uriel.ordertracker.App.Services.Interface.IRestService;
import com.example.uriel.ordertracker.R;

import org.json.JSONException;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ReportActivity extends DrawerActivity {
    private IRestService restService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Reporte diario");
        configDrawerAfterCreate(savedInstanceState);

        User user = SessionInformation.getEditor().loadUserInformation();
        String username = user.getUsername();
        String token = user.getToken();

        restService = new RestService();
        try {
            restService.getReport(username, token, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateReport(Report report){
        String dayOfWeek = getDayOfWeek();
        TextView dayText = (TextView)findViewById(R.id.dia);
        dayText.setText("Reporte del dia: " + dayOfWeek);

        TextView visitedText = (TextView)findViewById(R.id.visitados);
        visitedText.setText("Clientes visitados del dia: " + String.valueOf(report.getClientesVisitados()));

        TextView offRouteText = (TextView)findViewById(R.id.fueraDeRuta);
        offRouteText.setText("Clientes visitados fuera de ruta: " + String.valueOf(report.getClientesFueraDeRuta()));

        TextView bultosText = (TextView)findViewById(R.id.bultos);
        bultosText.setText("Bultos vendidos: " + String.valueOf(report.getBultosVendidos()));

        TextView moneyText = (TextView)findViewById(R.id.dinero);
        moneyText.setText("Dinero vendido: $" + String.valueOf(report.getVentasDelDia()));
    }

    public void handleUnexpectedError(String error){
        SweetAlertDialog dialog = Helpers.getErrorDialog(this, "Error en la consulta. Intente nuevamente", error);
        dialog.show();
    }

    public String getDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Domingo";
            case Calendar.MONDAY:
                return "Lunes";
            case Calendar.TUESDAY:
                return "Martes";
            case Calendar.WEDNESDAY:
                return "Miercoles";
            case Calendar.THURSDAY:
                return "Jueves";
            case Calendar.FRIDAY:
                return "Viernes";
            case Calendar.SATURDAY:
                return "Sabado";
        }
        return "";
    }
}
