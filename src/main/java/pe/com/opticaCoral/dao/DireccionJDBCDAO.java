package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.com.opticaCoral.beans.Direccion;
import pe.com.opticaCoral.beans.Estado;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IDireccion;

public class DireccionJDBCDAO implements IDireccion {

    private Connection userConn;
    private final String SQL_UPDATE = "UPDATE tdireccion SET calle = ?, numero = ?, distrito = ?, provincia = ?, codigopostal = ?, idEstado = ?\n"
            + "WHERE idPersona = ?;";
    private final String SQL_SELECT_EXISTE = "SELECT * FROM tdireccion WHERE idPersona = ?";
    private final String SQL_INSERT = "INSERT INTO tdireccion(idDireccion, calle, numero, distrito, provincia, codigopostal, idEstado, idPersona)\n"
            + "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?);";
    private final String SQL_SELECTBY = "SELECT * FROM tdireccion WHERE idPersona = ?;";

    public DireccionJDBCDAO() {
    }

    public DireccionJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public String insert(Direccion dir) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);
            ps.setString(1, dir.getCalle());
            ps.setInt(2, dir.getNumero());
            ps.setString(3, dir.getDistrito());
            ps.setString(4, dir.getProvincia());
            ps.setInt(5, dir.getCodigopostal());
            ps.setInt(6, dir.getIdEstado().getIdEstado());
            ps.setInt(7, dir.getIdPersona().getIdPersona());

            ps.executeUpdate();
            mensaje = "La direccion se ha registrado correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al registrar Direccion: " + ex.getMessage());
            mensaje = "No fue posible registrar al Direccion: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public String update(Direccion dir) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);
            ps.setString(1, dir.getCalle());
            ps.setInt(2, dir.getNumero());
            ps.setString(3, dir.getDistrito());
            ps.setString(4, dir.getProvincia());
            ps.setInt(5, dir.getCodigopostal());
            ps.setInt(6, dir.getIdEstado().getIdEstado());
            ps.setInt(7, dir.getIdPersona().getIdPersona());

            ps.executeUpdate();
            mensaje = "La direccion se ha actualizado correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al actualizar Direccion: " + ex.getMessage());
            mensaje = "No fue posible actualizar al Direccion: " + ex.getMessage();
            } finally {
                Conexion.close(ps);
                if (this.userConn == null) {
                    Conexion.close(conn);
                }
            }
        return mensaje;
    }

    @Override
    public Direccion findById(int idPersona) {
        Direccion dir = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idPersona);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idDireccion");
                String calle = rs.getString("calle");
                int numero = rs.getInt("numero");
                String distrito = rs.getString("distrito");
                String provincia = rs.getString("provincia");
                int codigopostal = rs.getInt("codigopostal");
                int idEstado = rs.getInt("idEstado");
                int idPer = rs.getInt("idPersona");

                EstadoJDBCDAO esDao = new EstadoJDBCDAO();
                Estado estado = esDao.findById(idEstado);

                PersonaJDBCDAO perDao = new PersonaJDBCDAO();
                Persona persona = perDao.findById(idPer);

                dir = new Direccion(id, calle, numero, distrito, provincia, codigopostal, estado, persona);
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar DireccionBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return dir;
    }

    @Override
    public boolean existe(int idPersona) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_EXISTE);
            ps.setLong(1, idPersona);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar DireccionBy: " + ex.getMessage());
            return false;
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

    }

}
