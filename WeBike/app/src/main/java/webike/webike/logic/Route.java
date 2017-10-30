package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Juan on 10/28/2017.
 */

public class Route implements Serializable{
    private String nombre;
    private String origen;
    private String destino;
    private String hora;

    private String fecha;
    private String duracion;
    private String clima;
    private String distancia;
    private String descripcion;

    public Route(String nombre, String origen, String destino, String hora, String fecha, String duracion, String clima, String distancia, String descripcion) {
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.hora = hora;
        this.fecha = fecha;
        this.duracion = duracion;
        this.clima = clima;
        this.distancia = distancia;
        this.descripcion = descripcion;
    }

    public Route(){}

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
