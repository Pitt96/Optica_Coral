package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Estado;
import pe.com.opticaCoral.beans.Proveedor;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IProveedor;

public class ProveedorJDBCDAO implements IProveedor {

    private Connection userConn;
    private final String SQL_SELECT = "SELECT * FROM tproveedor;";
    private final String SQL_SELECTBY = "SELECT * FROM tproveedor WHERE idProveedor = ?;";

    public ProveedorJDBCDAO() {
    }

    public ProveedorJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public List<Proveedor> listAll() {
        Proveedor prov;
        List<Proveedor> listarProveedores = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            listarProveedores = new ArrayList<>();
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idProveedor");
                System.out.println(rs.getString("nombre"));
                String observacion = rs.getString("observacion");
                String razonsocial = rs.getString("razonsocial");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                boolean visible = rs.getBoolean("visible");
                int idEstado = rs.getInt("idEstado");

                Estado est = new EstadoJDBCDAO().findById(idEstado);
                prov = new Proveedor(id, observacion, razonsocial, nombre, direccion, telefono, email, visible, est);

                listarProveedores.add(prov);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Proveedores: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarProveedores;
    }

    @Override
    public Proveedor findById(int idProveedor) {
        Proveedor provee = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idProveedor);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idProveedor");
                String observacion = rs.getString("observacion");
                String razonsocial = rs.getString("razonsocial");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                boolean visible = rs.getBoolean("visible");
                int idEstado = rs.getInt("idEstado");

                Estado est = new EstadoJDBCDAO().findById(idEstado);
                provee = new Proveedor(id, observacion, razonsocial, nombre, direccion, telefono, email, visible, est);
            }

        } catch (SQLException ex) {
            System.out.println("Error al consultar ProveedorBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return provee;
    }

}
