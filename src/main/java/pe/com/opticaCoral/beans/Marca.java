package pe.com.opticaCoral.beans;

public class Marca {

    private int idMarca;
    private String nombre;
    private boolean visible;

    public Marca() {
    }

    public Marca(int idMarca, String nombre, boolean visible) {
        this.idMarca = idMarca;
        this.nombre = nombre;
        this.visible = visible;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
