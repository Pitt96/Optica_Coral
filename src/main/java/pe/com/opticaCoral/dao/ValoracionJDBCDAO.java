package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.com.opticaCoral.beans.Valoracion;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.ICalificacion;

public class ValoracionJDBCDAO implements ICalificacion {

    private Connection userConn;
    private final String SQL_SELECT_VALORACION = "SELECT cal.contador FROM tcalificacion cal\n"
            + "INNER JOIN tproducto p ON p.idProducto = cal.idProducto\n"
            + "INNER JOIN tcliente cli ON cli.idPersona = cal.idPersona\n"
            + "WHERE cal.idPersona = ? AND cal.idProducto = ?";
    private final String SQL_SELECT_PROMEDIO = "SELECT COUNT(cal.idCalificacion) AS cantidad, SUM(cal.contador) AS suma FROM tcalificacion cal\n"
            + "INNER JOIN tproducto p ON p.idProducto = cal.idProducto\n"
            + "WHERE cal.idProducto = ?";
    private final String SQL_INSERT = "INSERT INTO tcalificacion(idCalificacion, contador, visible, idProducto, idPersona)\n"
            + "VALUES(NULL, ?, TRUE, ?, ?);";
    private final String SQL_CONSULTA = "SELECT idCalificacion FROM tcalificacion WHERE idProducto = ? AND idPersona = ?;";
    private final String SQL_UPDATE = "UPDATE tcalificacion SET contador = ? WHERE idCalificacion = ?";

    public ValoracionJDBCDAO() {
    }

    public ValoracionJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public String insert(Valoracion val) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();

            int idCalificacion = 0;
            try {
                idCalificacion = existeValoracion(val.getIdProducto().getIdProducto(), val.getIdPersona().getIdPersona().getIdPersona());
            } catch (Exception e) {
            }
            System.out.println("VALLL "+idCalificacion);

            if (idCalificacion > 0) {
                //update
                ps = conn.prepareStatement(SQL_UPDATE);
                ps.setInt(1, val.getContador());
                ps.setInt(2, idCalificacion);
            } else {
                //insert
                ps = conn.prepareStatement(SQL_INSERT);
                ps.setInt(1, val.getContador());
                ps.setInt(2, val.getIdProducto().getIdProducto());
                ps.setInt(3, val.getIdPersona().getIdPersona().getIdPersona());
            }

            ps.executeUpdate();
            mensaje = "La valoracion se ha registrado correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al registrar Valoracion: " + ex.getMessage());
            mensaje = "No fue posible registrar la Valoracion: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public int existeValoracion(int idProducto, int idCliente) {
        int idCalificacion = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_CONSULTA);
            ps.setInt(1, idProducto);
            ps.setInt(2, idCliente);
            rs = ps.executeQuery();

            if (rs.next()) {
                idCalificacion = rs.getInt(1);;
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar ValoracionBy idCalificacion: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return idCalificacion;
    }

    @Override
    public int promedioValoracion(int idProducto) {
        int promedio = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_PROMEDIO);
            ps.setLong(1, idProducto);
            rs = ps.executeQuery();

            if (rs.next()) {

                int cantidad = rs.getInt("cantidad");
                int suma = rs.getInt("suma");

                try {
                    promedio = suma / cantidad;
                } catch (ArithmeticException e) {
                    //System.out.println(e.getMessage());
                }

                return promedio;

            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar ValoracionBy promedio: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return promedio;
    }

    @Override
    public int valoracionCliente(int idProducto, int idCliente) {
        int valor = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_VALORACION);
            ps.setInt(1, idCliente);
            ps.setInt(2, idProducto);
            rs = ps.executeQuery();

            if (rs.next()) {
                return valor = rs.getInt("contador");
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar valoracionClienteBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return valor;
    }

}
