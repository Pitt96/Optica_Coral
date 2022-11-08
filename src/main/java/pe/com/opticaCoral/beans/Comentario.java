package pe.com.opticaCoral.beans;

import java.sql.Date;

public class Comentario {

    private int idComentario;
    private String descripcion;
    private boolean visible;
    private Producto idProducto;
    private Cliente idPersona;
    private Date fecha;

    public Comentario() {
    }

    public Comentario(String descripcion, Cliente idPersona, Date fecha) {
        this.descripcion = descripcion;
        this.idPersona = idPersona;
        this.fecha = fecha;
    }
    
    

    public Comentario(int idComentario, String descripcion, boolean visible, Producto idProducto, Cliente idPersona, Date fecha) {
        this.idComentario = idComentario;
        this.descripcion = descripcion;
        this.visible = visible;
        this.idProducto = idProducto;
        this.idPersona = idPersona;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
