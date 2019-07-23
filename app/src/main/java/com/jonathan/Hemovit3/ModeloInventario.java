package com.jonathan.Hemovit3;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ModeloInventario
{
    public String solucion;
    public String factor;

    public ModeloInventario() {
    }

    public ModeloInventario(String solucion, String factor) {
        this.solucion = solucion;
        this.factor = factor;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }
}
