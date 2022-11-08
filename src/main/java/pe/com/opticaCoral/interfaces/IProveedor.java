package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Proveedor;

public interface IProveedor {

    public abstract Proveedor findById(int idProveedor);

    public abstract List<Proveedor> listAll();
}
