package com.jonathan.Hemovit3;

public class Usuario
{
    public String nombre;
    public String correo;
    public String clinica;
    public String cedula;
    public String acceso;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String clinica, String cedula, String acceso) {
        this.nombre = nombre;
        this.correo = correo;
        this.clinica = clinica;
        this.cedula = cedula;
        this.acceso = acceso;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
