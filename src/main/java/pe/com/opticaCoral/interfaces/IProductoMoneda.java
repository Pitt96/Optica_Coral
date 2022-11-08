package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.ProductoMoneda;

public interface IProductoMoneda {

    public abstract List<ProductoMoneda> findById(int idProd);

    public abstract String update(ProductoMoneda pm);

}
