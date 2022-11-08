package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.TipoEmpleado;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.ITipoEmpleado;

public class TipoEmpleadoJDBCDAO implements ITipoEmpleado {

    private Connection userConn;
    private final String SQL_SELECTBY = "SELECT * FROM ttipoempleado WHERE idTipoEmpleado = ?;";
    private final String SQL_SELECT = "SELECT * FROM ttipoempleado";

    public TipoEmpleadoJDBCDAO() {
    }

    public TipoEmpleadoJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }
    
    @Override
    public List<TipoEmpleado> listAll() {
        TipoEmpleado tp;
        List<TipoEmpleado> listarTipoEmpleado = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idTipoEmpleado");
                String descripcion = rs.getString("descripcion");

                tp = new TipoEmpleado(id, descripcion);

                listarTipoEmpleado.add(tp);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Tipo Empleado: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarTipoEmpleado;
    }

    @Override
    public TipoEmpleado findById(int idTipoEmpleado) {
        TipoEmpleado tp = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idTipoEmpleado);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idTipoEmpleado");
                String descripcion = rs.getString("descripcion");
                tp = new TipoEmpleado(id, descripcion);

            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar TipoEmpleadoBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return tp;
    }

}
