package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 31-Mar-16.
 */

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Uriel on 21-Jan-16.
 */
public class Order {
    private int id;
    private Client client;
    private Date fecha;
    private double importeTotal;
    private boolean enviado;
    private User seller;
    private ArrayList<OrderLine> lines;

    public Order(int id, Client client, Date fecha, double importeTotal, User seller, ArrayList<OrderLine> lines){
        this.id = id;
        this.client = client;
        this.fecha = fecha;
        this.importeTotal = importeTotal;
        this.lines = lines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporteTotal() {
        return Double.valueOf(String.format("%.2f", importeTotal));
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public ArrayList<OrderLine> getLines() {
        return lines;
    }

    public void setLineas(ArrayList<OrderLine> lines) {
        this.lines = lines;
    }
}

