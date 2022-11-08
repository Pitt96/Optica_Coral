package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.ICliente;

public class ClienteJDBCDAO  implements ICliente{

    private Connection userConn;
    private final String SQL_SELECTBY_PERFIL = "select * from tcliente where idPersona = ?;";

    public ClienteJDBCDAO(){
    }

    public ClienteJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public Cliente findByIdPerfil(int idPersona) {
        Cliente cli = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY_PERFIL);
            ps.setLong(1, idPersona);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idPer = rs.getInt("idPersona");
                String perfil = rs.getString("perfil");

                PersonaJDBCDAO pDao = new PersonaJDBCDAO();
                Persona p = pDao.findById(idPer);

                cli = new Cliente(p, perfil);
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar ClienteBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return cli;
    }

}
