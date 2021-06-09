package com.example.esqueleto_kianart.entity;

public class Imagen {
    private int numeroMeGusta;
    private String imagen;
    private String idImagen;

    public Imagen(){
    }

    public Imagen(int numeroMeGusta, String imagen) {
        this.numeroMeGusta = numeroMeGusta;
        this.imagen = imagen;
        this.idImagen = imagen;
    }

    public int getNumeroMeGusta() {
        return numeroMeGusta;
    }

    public void setNumeroMeGusta(int numeroMeGusta) {
        this.numeroMeGusta = numeroMeGusta;
    }

    public String getImagen() {
        return imagen;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
