package com.example.uriel.ordertracker.App.Model;

/**
 * Created by Uriel on 24-May-16.
 */
public class Report {
    private int clientesVisitados;
    private int clientesFueraDeRuta;
    private int bultosVendidos;
    private double ventasDelDia;

    public int getClientesVisitados() {
        return clientesVisitados;
    }

    public void setClientesVisitados(int clientesVisitados) {
        this.clientesVisitados = clientesVisitados;
    }

    public int getClientesFueraDeRuta() {
        return clientesFueraDeRuta;
    }

    public void setClientesFueraDeRuta(int clientesFueraDeRuta) {
        this.clientesFueraDeRuta = clientesFueraDeRuta;
    }

    public int getBultosVendidos() {
        return bultosVendidos;
    }

    public void setBultosVendidos(int bultosVendidos) {
        this.bultosVendidos = bultosVendidos;
    }

    public double getVentasDelDia() {
        return ventasDelDia;
    }

    public void setVentasDelDia(double ventasDelDia) {
        this.ventasDelDia = ventasDelDia;
    }
}
