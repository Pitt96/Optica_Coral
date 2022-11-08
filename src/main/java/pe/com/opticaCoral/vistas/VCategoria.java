
package pe.com.opticaCoral.vistas;

import pe.com.opticaCoral.dao.CategoriaJDBCDAO;

public class VCategoria {
    
    public boolean isSuperior(int idCat){
        CategoriaJDBCDAO catDao = new CategoriaJDBCDAO();
        return catDao.contarCategoria(idCat);
    }
    
}
