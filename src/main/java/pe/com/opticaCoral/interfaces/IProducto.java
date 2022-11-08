package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Producto;

public interface IProducto {

    public abstract List<Producto> listAllProductoMoneda(String moneda);

    public abstract List<Producto> listAllProductoCategoria(String moneda, int idCategoria);

    public abstract List<Producto> listAllProductoMarca(String moneda, int idMar);

    public abstract String insert(Producto producto);

    public abstract List<Producto> listAll();

    public abstract Producto findById(int idProducto);

    public abstract String update(Producto producto);

    public abstract String updateStock(Producto producto);

    public abstract Producto findById2(String moneda, int idProd);

    public abstract List<Producto> listAllProductoBuscar(String moneda, String valor);

}
