package webike.webike.logic;

import java.io.Serializable;

/**
 * Created by Camila on 17/11/2017.
 */

public class PlacePromotion extends SpecialPublication implements Serializable{

    private String nombre;
    private String description;
    private String key;
    private Double longitud;
    private Double latitud;

    public PlacePromotion() {
    }

    public PlacePromotion(String nombre,Double longitud, Double latitud, String descripcion, String key) {
        this.nombre = nombre;
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
                ", description='" + description + '\'' +
                ", key='" + key + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                '}';
    }
}
