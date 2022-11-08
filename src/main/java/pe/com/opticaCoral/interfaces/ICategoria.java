package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Categoria;

public interface ICategoria {

    public abstract Categoria findById(int idCategoria);

    public abstract List<Categoria> listAll();

    public abstract boolean contarCategoria(int idCategoria);

}
