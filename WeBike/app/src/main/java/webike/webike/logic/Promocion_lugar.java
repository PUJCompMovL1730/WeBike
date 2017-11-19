package webike.webike.logic;



/**
 * Created by Carlos on 18/11/2017.
 */

public class Promocion_lugar {
    private String nombre;
    private int ranking;
    private double latitud;
    private double longitud;
    private String descripcion;


    public Promocion_lugar(String nombre, int ranking, double latitud, double longitud, String descripcion) {
        this.nombre = nombre;
        this.ranking = ranking;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
