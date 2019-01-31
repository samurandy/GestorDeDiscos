package com.example.chv.gestordediscos.Model;

/**
 * Created by CHV on 27/01/2018.
 */

public class Pelicula {


    private Long id;
    String titulo;
    String descripcion;
    String imagen;
    String valoracion;
    String duracion;

    public Pelicula(Long id, String titulo, String descripcion, String imagen, String valoracion, String duracion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.valoracion = valoracion;
        this.duracion = duracion;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }
}
