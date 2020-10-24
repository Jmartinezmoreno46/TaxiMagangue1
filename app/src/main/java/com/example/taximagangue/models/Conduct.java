package com.example.taximagangue.models;

public class Conduct {

    String id;
    String nombre;
    String email;
    String vehiculoMarca;
    String VehiculoPlaca;


    public Conduct(String id, String nombre, String email, String vehiculoMarca, String vehiculoPlaca) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.vehiculoMarca = vehiculoMarca;
        VehiculoPlaca = vehiculoPlaca;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehiculoMarca() {
        return vehiculoMarca;
    }

    public void setVehiculoMarca(String vehiculoMarca) {
        this.vehiculoMarca = vehiculoMarca;
    }

    public String getVehiculoPlaca() {
        return VehiculoPlaca;
    }

    public void setVehiculoPlaca(String vehiculoPlaca) {
        VehiculoPlaca = vehiculoPlaca;
    }
}
