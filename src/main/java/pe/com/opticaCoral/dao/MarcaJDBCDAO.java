package pe.com.opticaCoral.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Marca;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IMarca;

public class MarcaJDBCDAO implements IMarca {

    private Connection userConn;
    private final String SQL_SELECTBY = "SELECT * FROM tmarca WHERE idMarca = ?;";
    private final String SQL_SELECT = "SELECT * FROM tmarca;";
    private final String SQL_COUNT_MARCA = "CALL listar_contar_marcas(?);";

    public MarcaJDBCDAO() {
    }

    public MarcaJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public List<Marca> listAll() {
        Marca mar;
        List<Marca> listarMarcas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idMarca = rs.getInt("idMarca");
                String nombre = rs.getString("nombre");
                boolean visible = rs.getBoolean("visible");

                mar = new Marca(idMarca, nombre, visible);
                listarMarcas.add(mar);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Marcas: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarMarcas;
    }

    @Override
    public int contarMarca(int idMar) {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            cs = conn.prepareCall(SQL_COUNT_MARCA);
            cs.setInt(1, idMar);
            rs = cs.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException ex) {
            System.out.println("Error en contarMarca: " + ex.getMessage());
            return 0;
        } finally {
            Conexion.close(rs);
            Conexion.close(cs);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
    }

    @Override
    public Marca findById(int idMarca) {
        Marca mar = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idMarca);
            rs = ps.executeQuery();

            while (rs.next()) {
                mar = new Marca();
                mar.setIdMarca(rs.getInt("idMarca"));
                mar.setNombre(rs.getString("nombre"));
                mar.setVisible(rs.getBoolean("visible"));
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar MarcaBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mar;
    }
}
