package pe.com.opticaCoral.beans;

public class Direccion {

    private int idDireccion;
    private String calle;
    private int numero;
    private String distrito;
    private String provincia;
    private int codigopostal;
    private Estado idEstado;
    private Persona idPersona;

    public Direccion() {
    }

    public Direccion(String calle, int numero, String distrito, String provincia, int codigopostal, Estado idEstado, Persona idPersona) {
        this.calle = calle;
        this.numero = numero;
        this.distrito = distrito;
        this.provincia = provincia;
        this.codigopostal = codigopostal;
        this.idEstado = idEstado;
        this.idPersona = idPersona;
    }
    
    

    public Direccion(int idDireccion, String calle, int numero, String distrito, String provincia, int codigopostal, Estado idEstado, Persona idPersona) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.numero = numero;
        this.distrito = distrito;
        this.provincia = provincia;
        this.codigopostal = codigopostal;
        this.idEstado = idEstado;
        this.idPersona = idPersona;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(int codigopostal) {
        this.codigopostal = codigopostal;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

}
