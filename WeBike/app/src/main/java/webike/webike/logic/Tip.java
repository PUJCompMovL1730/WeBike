package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Juan on 11/19/2017.
 */

public class Tip implements Serializable {

    public String titulo;
    public String numero;
    public String descripcion;

    public Tip(){
    }

    public Tip(String numero, String titulo, String descripcion){
        this.numero = numero;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNumero() { return numero; }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
