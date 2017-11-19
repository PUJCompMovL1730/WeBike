package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Juan on 10/28/2017.
 */

public class Route implements Serializable{

    private String origen;
    private String destino;
    private String fecha;
    private String duracion;
    private String clima;
    private String distancia;
    private String descripcion;
    private String url;

    public Route(String origen, String destino, String fecha, String duracion, String clima, String distancia) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.duracion = duracion;
        this.clima = clima;
        this.distancia = distancia;
    }

    public Route(){}

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

}
