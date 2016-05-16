package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 31-Mar-16.
 */

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Uriel on 21-Jan-16.
 */
public class Order {
    private int id;
    private Client client;
    private Date fecha;
    private double importeTotal;
    private String estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public ArrayList<OrderLine> getLines() {
        return lines;
    }

    public void setLineas(ArrayList<OrderLine> lines) {
        this.lines = lines;
    }

    public JSONObject toJSONObject(){
        HashMap<String,String> params = new HashMap<String,String>();
        if (this.client != null) params.put("visit_id", String.valueOf(this.client.getVisit_id()));
        if (this.client != null) params.put("client", String.valueOf(this.client.getId()));
        if (this.fecha != null) params.put("fecha", String.valueOf(this.fecha.getTime()));
        if (this.estado != null){
            params.put("estado", this.estado);
        }else {
            params.put("estado", "");
        }
        if (this.importeTotal > 0) params.put("importeTotal", String.valueOf(importeTotal));
        if (this.seller != null) params.put("vendedor", String.valueOf(this.seller.getId()));
        String lines = "[";
        for (OrderLine line: this.lines) {
            lines += line.toJSONObject() + ",";
        }
        lines = lines.substring(0, lines.length()-1) + "]";
        if (this.lines.size() > 0) params.put("lines", lines);
        return new JSONObject(params);
    }

}

