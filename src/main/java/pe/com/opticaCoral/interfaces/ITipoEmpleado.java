package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.TipoEmpleado;

public interface ITipoEmpleado {

    public abstract TipoEmpleado findById(int idTipoEmpleado);

    public abstract List<TipoEmpleado> listAll();

}
