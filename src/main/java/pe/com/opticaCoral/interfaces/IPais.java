package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Pais;

public interface IPais {

    public abstract Pais findById(int idPais);

    public abstract List<Pais> listAll();
}
