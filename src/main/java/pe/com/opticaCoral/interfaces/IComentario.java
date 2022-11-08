package pe.com.opticaCoral.interfaces;

import java.util.List;
import pe.com.opticaCoral.beans.Comentario;

public interface IComentario {

    public abstract List<Comentario> listAllComentariosxProducto(int idProducto);

    public abstract String insert(Comentario comen);

}
