package pe.com.opticaCoral.beans;

public class Privilegio {

    private TipoEmpleado idTipoEmpleado;
    private boolean crear;
    private boolean modificar;
    private boolean consultar;
    private boolean desactivar;

    public Privilegio() {
    }

    public Privilegio(TipoEmpleado idTipoEmpleado, boolean crear, boolean modificar, boolean consultar, boolean desactivar) {
        this.idTipoEmpleado = idTipoEmpleado;
        this.crear = crear;
        this.modificar = modificar;
        this.consultar = consultar;
        this.desactivar = desactivar;
    }

    public TipoEmpleado getIdTipoEmpleado() {
        return idTipoEmpleado;
    }

    public void setIdTipoEmpleado(TipoEmpleado idTipoEmpleado) {
        this.idTipoEmpleado = idTipoEmpleado;
    }

    public boolean isCrear() {
        return crear;
    }

    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    public boolean isModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public boolean isDesactivar() {
        return desactivar;
    }

    public void setDesactivar(boolean desactivar) {
        this.desactivar = desactivar;
    }

}
