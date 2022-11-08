package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.com.opticaCoral.beans.Envio;
import pe.com.opticaCoral.beans.Pais;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IEnvio;

public class EnvioJDBCDAO implements IEnvio {

    private Connection userConn;
    private final String SQL_ENVIO = "SELECT pai.idPais, env.costo, env.envio FROM tdireccion dir\n"
            + "INNER JOIN testado est ON est.idEstado = dir.idEstado\n"
            + "INNER JOIN tpais pai ON pai.idPais = est.idPais\n"
            + "INNER JOIN tenvio env ON env.idPais = pai.idPais\n"
            + "WHERE dir.idPersona = ?";

    public EnvioJDBCDAO() {
    }

    public EnvioJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public Envio validarDireccionEnvio(int idPersona) {
        Envio env = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_ENVIO);
            ps.setInt(1, idPersona);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idPais = rs.getInt("idPais");
                double costo = rs.getDouble("costo");
                boolean envio = rs.getBoolean("envio");
                PaisJDBCDAO paDao = new PaisJDBCDAO();
                Pais pais = paDao.findById(idPais);
                env = new Envio(pais, costo, envio);
                System.out.println("validarDireccionEnvioDAO: " + env.getIdPais()+" - "+env.getCosto() + " - "+env.isEnvio());
            }

        } catch (SQLException ex) {
            System.out.println("Error de EnvioBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return env;
    }
}
