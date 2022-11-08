package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IPersona;

public class PersonaJDBCDAO implements IPersona {

    private Connection userConn;
    private final String SQL_UPDATE_PERFIL = "UPDATE tcliente SET perfil = ? WHERE idPersona = ?;";
    private final String SQL_UPDATE_DATOS = "UPDATE tpersona SET nombres = ?, apellidop = ?, apellidom = ?, sexo = ?, telefono = ?, foto = ? WHERE idPersona = ?;";
    private final String SQL_UPDATE_DATOS2 = "UPDATE tpersona SET nombres = ?, apellidop = ?, apellidom = ?, sexo = ?, telefono = ? WHERE idPersona = ?;";
    private final String SQL_UPDATE_CLAVE = "UPDATE tpersona SET clave = AES_ENCRYPT(?,'123456789') WHERE idPersona = ?;";
    private final String SQL_INSERT_CLIENTE_PERSONA = "INSERT INTO tpersona (idPersona, nombres, apellidop, apellidom, sexo, telefono, email, foto, visible, clave) \n"
            + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, TRUE, AES_ENCRYPT(?,'123456789'));";
    private final String SQL_INSERT_CLIENTE = "INSERT INTO tcliente (idPersona, perfil) VALUES (?, ?);";
    private final String SQL_SELECTBY = "SELECT idPersona, nombres, apellidop, apellidom, sexo, telefono, email, foto, visible, AES_DECRYPT(clave,'123456789') as clave FROM tpersona WHERE idPersona = ?;";

    public PersonaJDBCDAO() {
    }

    public PersonaJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    /*nombres, apellidoP, apellidoM, sexo, telefono, email, foto, clave, perfil*/
    @Override
    public String insertCliente(Persona p) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            int idGeneradoPers = 0;
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ps = conn.prepareStatement(SQL_INSERT_CLIENTE_PERSONA, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidop());
            ps.setString(3, p.getApellidom());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getFoto());
            ps.setString(8, p.getClave());
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGeneradoPers = generatedKeys.getInt(1);
            }

            for (Cliente cliente : p.getCliente()) {
                ps2 = conn.prepareStatement(SQL_INSERT_CLIENTE);
                ps2.setInt(1, idGeneradoPers);
                ps2.setString(2, cliente.getPerfil());
                ps2.executeUpdate();
            }

            conn.commit();
            mensaje = "El cliente se ha creado correctamente.";

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    System.err.print("La transacción no pudo realizarse");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.println("no pudo hacerse el rollback de la transacción");
                }
            }
            System.out.println("Error al registrar Cliente: " + ex.getMessage());
            mensaje = "No fue posible registrar al Cliente: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            Conexion.close(ps2);
            try {
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
            }
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public String updateDatos(Persona p) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ps = conn.prepareStatement(SQL_UPDATE_DATOS);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidop());
            ps.setString(3, p.getApellidom());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getFoto());
            ps.setInt(7, p.getIdPersona());
            ps.executeUpdate();

            for (Cliente cliente : p.getCliente()) {
                ps2 = conn.prepareStatement(SQL_UPDATE_PERFIL);
                ps2.setString(1, cliente.getPerfil());
                ps2.setInt(2, p.getIdPersona());
                ps2.executeUpdate();
            }

            mensaje = "El perfil del cliente se ha actualizado correctamente.";
            conn.commit();

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    System.err.print("La transacción no pudo realizarse");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.println("no pudo hacerse el rollback de la transacción");
                }
            }
            System.out.println("Error al actualizar perfil del Cliente: " + ex.getMessage());
            mensaje = "Error al actualizar perfil del Cliente: " + ex.getMessage();
        } finally {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
            }
            Conexion.close(ps);
            Conexion.close(ps2);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public String updateDatos2(Persona p) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ps = conn.prepareStatement(SQL_UPDATE_DATOS2);
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidop());
            ps.setString(3, p.getApellidom());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setInt(6, p.getIdPersona());
            ps.executeUpdate();

            for (Cliente cliente : p.getCliente()) {
                ps2 = conn.prepareStatement(SQL_UPDATE_PERFIL);
                ps2.setString(1, cliente.getPerfil());
                ps2.setInt(2, p.getIdPersona());
                ps2.executeUpdate();
            }

            mensaje = "El perfil del cliente se ha actualizado correctamente.";
            conn.commit();

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    System.err.print("La transacción no pudo realizarse");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.println("no pudo hacerse el rollback de la transacción");
                }
            }
            System.out.println("Error al actualizar perfil del Cliente: " + ex.getMessage());
            mensaje = "Error al actualizar perfil del Cliente: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            try {
                conn.setAutoCommit(false);
            } catch (SQLException ex) {
            }
            Conexion.close(ps);
            Conexion.close(ps2);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public String updateClave(Persona p) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareCall(SQL_UPDATE_CLAVE);
            ps.setString(1, p.getClave());
            ps.setInt(2, p.getIdPersona());

            ps.executeUpdate();
            mensaje = "Se cambio el password correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al actualizar password: " + ex.getMessage());
            mensaje = "Error al actualizar password: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
                Conexion.close(ps);
            }
        }
        return mensaje;
    }

    @Override
    public Persona findById(int idPersona) {
        Persona p = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idPersona);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idPersona");
                String nombres = rs.getString("nombres");
                String apellidop = rs.getString("apellidop");
                String apellidom = rs.getString("apellidom");
                String sexo = rs.getString("sexo");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String foto = rs.getString("foto");
                boolean visible = rs.getBoolean("visible");
                String clave = rs.getString("clave");

                p = new Persona(id, nombres, apellidop, apellidom, sexo, telefono, email, foto, visible, clave);
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar PersonaBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return p;
    }

}
