package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Juan on 10/28/2017.
 */

public class Publicacion extends AbstractPublication implements Serializable{
    private String origen;
    private String destino;
    private String hora;
    private String descripcion;
    private String nombre;
    private String key;

    public Publicacion() {
    }

    public Publicacion(String nombre ,String origen, String destino, String hora, String descripcion, String key) {
        this.origen = origen;
        this.destino = destino;
        this.hora = hora;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", hora='" + hora + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
