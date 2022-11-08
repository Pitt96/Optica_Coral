package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Empleado;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.beans.TipoEmpleado;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IEmpleado;

public class EmpleadoJDBCDAO implements IEmpleado {

    private Connection userConn;
    private final String SQL_SELECT = "SELECT * FROM tpersona per\n"
            + "INNER JOIN templeado emp ON emp.idPersona = per.idPersona\n"
            + "WHERE per.visible = TRUE ORDER BY per.idPersona;";
    private final String SQL_SELECTBY = "SELECT * FROM templeado WHERE idPersona = ?;";

    private final String SQL_LOGIN_EMPLEADO = "SELECT per.idPersona, emp.idTipoEmpleado FROM tpersona per\n"
            + "INNER JOIN templeado emp ON emp.idPersona = per.idPersona\n"
            + "WHERE per.email = ? AND AES_DECRYPT(per.clave,'123456789') = ?";

    private final String SQL_UPDATE_DATOS = "UPDATE tpersona SET nombres = ?, apellidop = ?, apellidom = ?, sexo = ?, telefono = ?, foto = ? WHERE idPersona = ?;";
    private final String SQL_UPDATE_DATOS2 = "UPDATE tpersona SET nombres = ?, apellidop = ?, apellidom = ?, sexo = ?, telefono = ? WHERE idPersona = ?;";
    private final String SQL_UPDATE_TIPOEMPLEADO = "UPDATE templeado SET idTipoEmpleado = ? WHERE idPersona = ?;";

    private final String SQL_UPDATE_CLAVE = "UPDATE tpersona SET clave = AES_ENCRYPT(?,'123456789') WHERE idPersona = ?;";

    private final String SQL_INSERT = "INSERT INTO tpersona (idPersona, nombres, apellidop, apellidom, sexo, telefono, email, foto, visible, clave)\n"
            + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, TRUE, ?);";
    private final String SQL_INSERT_EMPLEADO = "INSERT INTO templeado (idPersona, idTipoEmpleado)\n"
            + "VALUES (?, ?);";

    public EmpleadoJDBCDAO() {
    }

    public EmpleadoJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public String insert(Empleado emp) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            int idPersona = 0;
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, emp.getIdPersona().getNombres());
            ps.setString(2, emp.getIdPersona().getApellidop());
            ps.setString(3, emp.getIdPersona().getApellidom());
            ps.setString(4, emp.getIdPersona().getSexo());
            ps.setString(5, emp.getIdPersona().getTelefono());
            ps.setString(6, emp.getIdPersona().getEmail());
            ps.setString(7, emp.getIdPersona().getFoto());
            ps.setString(8, emp.getIdPersona().getClave());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idPersona = rs.getInt(1);
            }

            ps2 = conn.prepareStatement(SQL_INSERT_EMPLEADO);
            ps2.setInt(1, idPersona);
            ps2.setInt(2, emp.getIdTipoEmpleado().getIdTipoEmpleado());
            ps2.executeUpdate();

            mensaje = "Se ha registrado el empleado correctamente";
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
            System.out.println("Error al insertar datos del empleado: " + ex.getMessage());
            mensaje = "Error al insertar datos del empleado: " + ex.getMessage();
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
    public String updatePassword(Empleado emp) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();

            ps = conn.prepareStatement(SQL_UPDATE_CLAVE);
            ps.setString(1, emp.getIdPersona().getClave());
            ps.setInt(2, emp.getIdPersona().getIdPersona());

            ps.executeUpdate();

            mensaje = "Se ha actualizado el password correctamente";

        } catch (SQLException ex) {
            System.out.println("Error al actualizar password del empleado: " + ex.getMessage());
            mensaje = "Error al actualizar password del empleado: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public String updateDatos(Empleado emp) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            if (emp.getIdPersona().getFoto() != null) {
                ps = conn.prepareStatement(SQL_UPDATE_DATOS);
                ps.setString(1, emp.getIdPersona().getNombres());
                ps.setString(2, emp.getIdPersona().getApellidop());
                ps.setString(3, emp.getIdPersona().getApellidom());
                ps.setString(4, emp.getIdPersona().getSexo());
                ps.setString(5, emp.getIdPersona().getTelefono());
                ps.setString(6, emp.getIdPersona().getFoto());
                ps.setInt(7, emp.getIdPersona().getIdPersona());
                ps.executeUpdate();

            } else {
                ps = conn.prepareStatement(SQL_UPDATE_DATOS2);
                ps.setString(1, emp.getIdPersona().getNombres());
                ps.setString(2, emp.getIdPersona().getApellidop());
                ps.setString(3, emp.getIdPersona().getApellidom());
                ps.setString(4, emp.getIdPersona().getSexo());
                ps.setString(5, emp.getIdPersona().getTelefono());
                ps.setInt(6, emp.getIdPersona().getIdPersona());
                ps.executeUpdate();
            }

            ps2 = conn.prepareStatement(SQL_UPDATE_TIPOEMPLEADO);
            ps2.setInt(1, emp.getIdTipoEmpleado().getIdTipoEmpleado());
            ps2.setInt(2, emp.getIdPersona().getIdPersona());
            ps2.executeUpdate();

            mensaje = "Se ha actualizado los datos correctamente";
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
            System.out.println("Error al actualizar datos del empleado: " + ex.getMessage());
            mensaje = "Error al actualizar datos del empleado: " + ex.getMessage();
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
    public List<Empleado> listAll() {
        Empleado emp;
        List<Empleado> listarEmpleados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idPersona");
                int idTipoEmpleado = rs.getInt("idTipoEmpleado");

                PersonaJDBCDAO perDao = new PersonaJDBCDAO();
                Persona per = perDao.findById(id);

                TipoEmpleadoJDBCDAO tpDao = new TipoEmpleadoJDBCDAO();
                TipoEmpleado tp = tpDao.findById(idTipoEmpleado);

                emp = new Empleado(per, tp);

                listarEmpleados.add(emp);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Empleados: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarEmpleados;
    }

    @Override
    public Empleado findById(int idPersona) {
        Empleado emp = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idPersona);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idPers = rs.getInt("idPersona");
                int idTipoEmpleado = rs.getInt("idTipoEmpleado");

                Persona p = new PersonaJDBCDAO().findById(idPers);
                TipoEmpleado tp = new TipoEmpleadoJDBCDAO().findById(idTipoEmpleado);

                emp = new Empleado(p, tp);
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar EmpleadoBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return emp;
    }

    @Override
    public Empleado validarLoginEmpleado(String email, String clave) {
        Empleado login = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_LOGIN_EMPLEADO);
            ps.setString(1, email);
            ps.setString(2, clave);
            rs = ps.executeQuery();

            while (rs.next()) {
                login = new Empleado();
                int idPersona = rs.getInt("idPersona");
                int idTipoEmpleado = rs.getInt("idTipoEmpleado");

                PersonaJDBCDAO perDao = new PersonaJDBCDAO();
                Persona per = perDao.findById(idPersona);
                login.setIdPersona(per);
                TipoEmpleadoJDBCDAO temp = new TipoEmpleadoJDBCDAO();
                TipoEmpleado tp = temp.findById(idTipoEmpleado);
                login.setIdTipoEmpleado(tp);
            }

        } catch (SQLException ex) {
            System.out.println("Error de Login empleado: " + ex.getMessage());
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
