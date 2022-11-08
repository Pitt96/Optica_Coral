package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.DetalleVenta;

public interface IDetalleVenta {

    public abstract List<DetalleVenta> listAllDetallesVentasClientes(int idVenta);

}
