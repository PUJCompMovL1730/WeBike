package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Camila on 17/11/2017.
 */

public class PlannedRoute extends  SpecialPublication implements Serializable{

    private String nombre;
    private String organiza;
    private String origen;
    private String destino;
    private String fecha;
    private String descripcion;

    public PlannedRoute(){
    }

    public PlannedRoute(String nombre, String organiza,String origen, String destino, String fecha, String descripcion) {
        this.organiza = organiza;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrganiza() {
        return organiza;
    }

    public void setOrganiza(String organiza) {
        this.organiza = organiza;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "PlannedRoute{" +
                "nombre='" + nombre + '\'' +
                ", organiza='" + organiza + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", fecha='" + fecha + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
