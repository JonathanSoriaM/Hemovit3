package com.jonathan.Hemovit3.Modelos;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Hemo {
    public String comentario;
    public String codigo;

    public Hemo() {
    }

    public Hemo(String comentario, String codigo) {
        this.comentario = comentario;
        this.codigo = codigo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
