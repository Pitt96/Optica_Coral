package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.Comentario;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IComentario;

public class ComentarioJDBCDAO implements IComentario {

    private Connection userConn;
    private final String SQL_SELECT_COMENTARIOS = "SELECT cli.idPersona, cli.perfil, comen.fecha, comen.descripcion FROM tcomentario comen\n"
            + "INNER JOIN tproducto pro ON pro.idProducto = comen.idProducto\n"
            + "INNER JOIN tcliente cli ON cli.idPersona = comen.idPersona\n"
            + "WHERE pro.idProducto = ? order by comen.fecha;";
    private final String SQL_INSERT = "INSERT INTO tcomentario(idComentario, descripcion, visible, idProducto, idPersona, fecha)\n"
            + "VALUES(NULL, ?, TRUE, ?, ?, ?);";

    public ComentarioJDBCDAO() {
    }

    public ComentarioJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public String insert(Comentario comen) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);
            ps.setString(1, comen.getDescripcion());
            ps.setInt(2, comen.getIdProducto().getIdProducto());
            ps.setInt(3, comen.getIdPersona().getIdPersona().getIdPersona());
            ps.setDate(4, comen.getFecha());

            ps.executeUpdate();
            mensaje = "El comentario se ha registrado correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al registrar Comentario: " + ex.getMessage());
            mensaje = "No fue posible registrar el Comentario: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public List<Comentario> listAllComentariosxProducto(int idProducto) {
        Comentario comen;
        List<Comentario> listarComentarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_COMENTARIOS);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idPersona = rs.getInt("idPersona");
                String descripcion = rs.getString("descripcion");
                Date fecha = rs.getDate("fecha");

                ClienteJDBCDAO cliDao = new ClienteJDBCDAO();
                Cliente cli = cliDao.findByIdPerfil(idPersona);

                comen = new Comentario(descripcion, cli, fecha);
                listarComentarios.add(comen);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Comentarios: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarComentarios;
    }

}
