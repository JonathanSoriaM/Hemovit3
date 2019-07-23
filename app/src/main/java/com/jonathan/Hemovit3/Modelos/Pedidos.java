package com.jonathan.Hemovit3.Modelos;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Pedidos {
    public int cantidad;
    public String tipo;

    public Pedidos() {
    }

    public Pedidos(int cantidad, String tipo) {
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
