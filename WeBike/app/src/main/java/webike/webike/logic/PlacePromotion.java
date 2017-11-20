package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Camila on 17/11/2017.
 */

public class PlacePromotion extends SpecialPublication implements Serializable{

    private String nombre;
    private String organiza;
    private String lugar;
    private String description;
    private String key;
    private Double longitud;
    private Double latitud;

    public PlacePromotion() {
    }

    public PlacePromotion(String nombre, String organiza, String lugar, Double longitud, Double latitud, String descripcion, String key) {
        this.nombre = nombre;
        this.organiza = organiza;
        this.lugar = lugar;
        this.longitud = longitud;
        this.latitud = latitud;
        this.description = descripcion;
        this.key = key;
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

    public Double getLongitud() {
        return this.longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PlacePromotion{" +
                "nombre='" + nombre + '\'' +
                ", organiza='" + organiza + '\'' +
                ", lugar='" + lugar + '\'' +
                ", description='" + description + '\'' +
                ", key='" + key + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                '}';
    }
}
