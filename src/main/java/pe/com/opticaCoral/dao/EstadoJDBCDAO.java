package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Estado;
import pe.com.opticaCoral.beans.Pais;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IEstado;

public class EstadoJDBCDAO implements IEstado{

    private Connection userConn;
    private final String SQL_SELECT = "SELECT * FROM testado;";
    private final String SQL_SELECT2 = "SELECT * FROM testado WHERE idPais = ?;";
    private final String SQL_SELECTBY = "SELECT * FROM testado WHERE idEstado = ?;";
    private final String SQL_SELECTBY2 = "SELECT * FROM testado WHERE idPais = ?;";

    public EstadoJDBCDAO() {
    }

    public EstadoJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }
    
    @Override
    public List<Estado> listAll() {
        Estado estado;
        List<Estado> listarEstados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idEstado = rs.getInt("idEstado");
                int idPa = rs.getInt("idPais");
                String nombre = rs.getString("nombre");
                
                PaisJDBCDAO paisDao = new PaisJDBCDAO();
                Pais pais = paisDao.findById(idPa);

                estado = new Estado(idEstado, pais, nombre);
                listarEstados.add(estado);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Estados: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarEstados;
    }
    
    @Override
    public List<Estado> listAll(int idPais) {
        Estado estado;
        List<Estado> listarEstados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT2);
            ps.setInt(1, idPais);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idEstado = rs.getInt("idEstado");
                int idPa = rs.getInt("idPais");
                String nombre = rs.getString("nombre");
                
                PaisJDBCDAO paisDao = new PaisJDBCDAO();
                Pais pais = paisDao.findById(idPais);

                estado = new Estado(idEstado, pais, nombre);
                listarEstados.add(estado);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Estados: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarEstados;
    }

    @Override
    public Estado findById(int idEstado) {
        Estado est = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idEstado);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idEstado");
                String nombre = rs.getString("nombre");
                int idPais = rs.getInt("idPais");
                
                Pais pais = new PaisJDBCDAO().findById(idPais);
                est = new Estado(id, pais, nombre);
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar EstadoBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return est;
    }
    
    @Override
    public Estado findById2(int idPais) {
        Estado est = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY2);
            ps.setLong(1, idPais);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idEstado");
                String nombre = rs.getString("nombre");
                int idPa = rs.getInt("idPais");
                
                Pais pais = new PaisJDBCDAO().findById(idPa);
                est = new Estado(id, pais, nombre);
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar EstadoBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return est;
    }

}
