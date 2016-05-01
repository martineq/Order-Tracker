package com.example.uriel.ordertracker.App.Model.Dto;

/**
 * Created by Uriel on 01-May-16.
 */
public class PlainOrder {

    private String client;
    private String estado;
    private double importeTotal;
    private long fecha;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
