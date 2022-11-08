package pe.com.opticaCoral.beans;

public class Proveedor {

    private int idProveedor;
    private String observacion;
    private String razonsocial;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private boolean visible;
    private Estado idEstado;

    public Proveedor() {
    }

    public Proveedor(int idProveedor, String observacion, String razonsocial, String nombre, String direccion, String telefono, String email, boolean visible, Estado idEstado) {
        this.idProveedor = idProveedor;
        this.observacion = observacion;
        this.razonsocial = razonsocial;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.visible = visible;
        this.idEstado = idEstado;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

}
