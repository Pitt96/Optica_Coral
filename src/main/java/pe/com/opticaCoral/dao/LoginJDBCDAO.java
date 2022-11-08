package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.ILogin;

public class LoginJDBCDAO implements ILogin {

    private Connection userConn;
    private final String SQL_LOGIN = "SELECT per.idPersona, cli.perfil, per.foto FROM tpersona per \n"
            + "INNER JOIN tcliente cli ON cli.idPersona = per.idPersona\n"
            + "WHERE per.email = ? AND AES_DECRYPT(per.clave,'123456789') = ?";
    private final String SQL_SELECT = "SELECT per.idPersona, cli.perfil, per.foto FROM tpersona per \n"
            + "INNER JOIN tcliente cli ON cli.idPersona = per.idPersona\n"
            + "WHERE per.idPersona = ?;";

    public LoginJDBCDAO() {
    }

    public LoginJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public Login validarLogin(String email, String clave) {
        Login login = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_LOGIN);
            ps.setString(1, email);
            ps.setString(2, clave);
            rs = ps.executeQuery();

            while (rs.next()) {
                login = new Login();
                int idPersona = rs.getInt("idPersona");
                String perfil = rs.getString("perfil");
                String foto = rs.getString("foto");

                login.setIdPersona(idPersona);
                login.setPerfil(perfil);
                login.setFoto(foto);
            }

        } catch (SQLException ex) {
            System.out.println("Error de Login: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return login;
    }

    @Override
    public Login listAll(int idPersona) {
        Login login = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);
            ps.setInt(1, idPersona);
            rs = ps.executeQuery();

            while (rs.next()) {
                login = new Login();
                int idPer = rs.getInt("idPersona");
                String perfil = rs.getString("perfil");
                String foto = rs.getString("foto");

                login.setIdPersona(idPersona);
                login.setPerfil(perfil);
                login.setFoto(foto);
            }

        } catch (SQLException ex) {
            System.out.println("Error de Login SELECT: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return login;
    }

}
