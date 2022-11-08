package pe.com.opticaCoral.beans;

public class Estado {

    private int idEstado;
    private Pais idPais;
    private String nombre;

    public Estado() {
    }

    public Estado(int idEstado, Pais idPais, String nombre) {
        this.idEstado = idEstado;
        this.idPais = idPais;
        this.nombre = nombre;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
