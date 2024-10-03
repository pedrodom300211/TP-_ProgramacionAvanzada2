package com.example.tp4_programacionavanzada2.entidad;

public class Articulo {

    private int id;
    private int idCategoria;
    private String nombre;
    private int stock;

    public Articulo(){

    }

    public Articulo(int id, int idCategoria, String nombre, int stock) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", idCategoria=" + idCategoria +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                '}';
    }
}
