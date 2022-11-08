package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Pais;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IPais;

public class PaisJDBCDAO implements IPais {

    private Connection userConn;
    private final String SQL_SELECT = "SELECT * FROM tpais ORDER BY nombre;";
    private final String SQL_SELECTBY = "SELECT * FROM tpais WHERE idPais = ? ORDER BY nombre;";

    public PaisJDBCDAO() {
    }

    public PaisJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public List<Pais> listAll() {
        Pais pais;
        List<Pais> listarPaises = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idPais = rs.getInt("idPais");
                String nombre = rs.getString("nombre");

                pais = new Pais(idPais, nombre);
                listarPaises.add(pais);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Paises: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarPaises;
    }

    @Override
    public Pais findById(int idPais) {
        Pais pai = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idPais);
            rs = ps.executeQuery();

            while (rs.next()) {
                pai = new Pais();
                pai.setIdPais(rs.getInt("idPais"));
                pai.setNombre(rs.getString("nombre"));
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar Pais: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return pai;
    }
}
