package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Empleado;

public interface IEmpleado {

    public abstract Empleado findById(int idEmpleado);

    public abstract Empleado validarLoginEmpleado(String email, String clave);

    public abstract List<Empleado> listAll();

    public abstract String updateDatos(Empleado emp);

    public abstract String updatePassword(Empleado emp);

    public abstract String insert(Empleado emp);
}
