package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Venta;

public interface IVenta {

    public abstract String insert(Venta v);

    public abstract int stockAnterior(int idProducto);

    public abstract List<Venta> listAllVentasClientes(int idPersona);

    public abstract Venta findById(int idVenta);

    public abstract List<Venta> listAll();

    public abstract String updateVenta(Venta v);

}
