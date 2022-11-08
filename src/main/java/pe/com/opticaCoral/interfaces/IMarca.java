package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Marca;

public interface IMarca {

    public abstract Marca findById(int idMarca);

    public abstract List<Marca> listAll();

    public abstract int contarMarca(int idMar);
}
