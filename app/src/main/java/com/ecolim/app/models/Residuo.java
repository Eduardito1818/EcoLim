package com.ecolim.app.models;

public class Residuo {
    private int id;
    private String tipo;
    private double cantidad;
    private String unidad;
    private String ubicacion;
    private String fecha;
    private String trabajador;

    public Residuo() {}

    public Residuo(String tipo, double cantidad, String unidad, String ubicacion, String fecha, String trabajador) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.trabajador = trabajador;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getTrabajador() { return trabajador; }
    public void setTrabajador(String trabajador) { this.trabajador = trabajador; }
}