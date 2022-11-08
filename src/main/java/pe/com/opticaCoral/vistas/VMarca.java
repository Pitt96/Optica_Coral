
package pe.com.opticaCoral.vistas;

import pe.com.opticaCoral.dao.MarcaJDBCDAO;

public class VMarca {
    
    public int contarMarca(int idMarca){
        MarcaJDBCDAO mDao = new MarcaJDBCDAO();
        return mDao.contarMarca(idMarca);
    }
    
}
