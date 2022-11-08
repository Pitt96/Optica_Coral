package pe.com.opticaCoral.beans;

public class Valoracion {

    private int idCalificacion;
    private int contador;
    private boolean visible;
    private Producto idProducto;
    private Cliente idPersona;

    public Valoracion() {
    }

    public Valoracion(int idCalificacion, int contador, boolean visible, Producto idProducto, Cliente idPersona) {
        this.idCalificacion = idCalificacion;
        this.contador = contador;
        this.visible = visible;
        this.idProducto = idProducto;
        this.idPersona = idPersona;
    }

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(int idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Cliente getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Cliente idPersona) {
        this.idPersona = idPersona;
    }

}
