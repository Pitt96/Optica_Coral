package pe.com.opticaCoral.vistas;

import pe.com.opticaCoral.dao.ValoracionJDBCDAO;

public class VPromedio {

    public int promedioEstrellas(int idProducto) {
        ValoracionJDBCDAO calDao = new ValoracionJDBCDAO();
        return calDao.promedioValoracion(idProducto);
    }

}
