package pe.com.opticaCoral.beans;

public class Empleado {

    private Persona idPersona;
    private TipoEmpleado idTipoEmpleado;

    public Empleado() {
    }

    public Empleado(Persona idPersona, TipoEmpleado idTipoEmpleado) {
        this.idPersona = idPersona;
        this.idTipoEmpleado = idTipoEmpleado;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public TipoEmpleado getIdTipoEmpleado() {
        return idTipoEmpleado;
    }

    public void setIdTipoEmpleado(TipoEmpleado idTipoEmpleado) {
        this.idTipoEmpleado = idTipoEmpleado;
    }

}
