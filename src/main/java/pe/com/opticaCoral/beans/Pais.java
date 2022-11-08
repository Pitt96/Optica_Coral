package pe.com.opticaCoral.beans;

public class Pais {

    private int idPais;
    private String nombre;

    public Pais() {
    }

    public Pais(int idPais, String nombre) {
        this.idPais = idPais;
        this.nombre = nombre;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
