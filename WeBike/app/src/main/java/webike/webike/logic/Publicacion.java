package webike.webike.logic;

/**
 * Created by Juan on 10/28/2017.
 */

public class Publicacion {
    private String origen;
    private String destino;
    private String hora;
    private String descripcion;

    public Publicacion() {
    }

    public Publicacion(String origen, String destino, String hora, String descripcion) {
        this.origen = origen;
        this.destino = destino;
        this.hora = hora;
        this.descripcion = descripcion;
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
}
