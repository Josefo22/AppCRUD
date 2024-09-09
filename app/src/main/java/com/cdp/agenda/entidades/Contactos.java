package com.cdp.agenda.entidades;

public class Contactos {

    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String deuda;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDeuda() {
        return deuda;
    }

    public void setDeuda(String deuda) {
        this.deuda = deuda;
    }
}
