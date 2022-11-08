package pe.com.opticaCoral.conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClaveAutoIncrement {

    private Connection userConn;

    public ClaveAutoIncrement() {
    }

    public ClaveAutoIncrement(Connection userConn) {
        this.userConn = userConn;
    }

    public int auto_increm(String sql) {
        int id = 1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener ID maximo: " + ex.getMessage());
            id = 1;
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return id;
    }

    public static void main(String[] args) {
        ClaveAutoIncrement s = new ClaveAutoIncrement();
        int a = s.auto_increm("SELECT MAX(columnaID) FROM TABLA;");
        System.out.println(a);
    }

}