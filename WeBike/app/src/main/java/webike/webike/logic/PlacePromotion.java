package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Camila on 17/11/2017.
 */

public class PlacePromotion extends SpecialPublication implements Serializable{

    private String nombre;
    private String organiza;
    private String lugar;
    private String descripcion;

    public PlacePromotion() {
    }

    public PlacePromotion(String nombre, String organiza,String descripcion, String lugar) {
        this.organiza = organiza;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.lugar = lugar;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) { this.lugar = lugar; }

    @Override
    public String toString() {
        return "PlacePromotion{" +
                "nombre='" + nombre + '\'' +
                ", organiza='" + organiza + '\'' +
                ", lugar='" + lugar + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
