package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Producto;
import pe.com.opticaCoral.beans.ProductoMoneda;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IProductoMoneda;

public class ProductoMonedaJDBCDAO implements IProductoMoneda {

    private Connection userConn;
    private final String SQL_SELECT_DIVISAS = "SELECT * FROM tproductomoneda WHERE idProducto = ? ORDER BY moneda DESC;";

    private final String SQL_UPDATE = "UPDATE tproductomoneda SET precio = ?, precionuevo = ? WHERE idProductoMoneda = ?;";

    public ProductoMonedaJDBCDAO() {
    }

    public ProductoMonedaJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public String update(ProductoMoneda pm) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareCall(SQL_UPDATE);

            ps.setDouble(1, pm.getPrecio());
            ps.setDouble(2, pm.getPrecionuevo());
            ps.setInt(3, pm.getIdProductoMoneda());

            ps.executeUpdate();
            mensaje = "ProductoMoneda se ha actualizado correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al actualizar ProductoMoneda: " + ex.getMessage());
            mensaje = "No fue posible actualizar ProductoMoneda: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public List<ProductoMoneda> findById(int idProd) {
        ProductoMoneda pm = null;
        List<ProductoMoneda> listarProductoMoneda = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_DIVISAS);
            ps.setInt(1, idProd);

            rs = ps.executeQuery();

            while (rs.next()) {
                int idProductoMoneda = rs.getInt("idProductoMoneda");
                String moneda = rs.getString("moneda");
                double precio = rs.getDouble("precio");
                double precionuevo = rs.getDouble("precionuevo");
                int idProducto = rs.getInt("idProducto");

                Producto prod = new ProductoJDBCDAO().findById(idProducto);

                pm = new ProductoMoneda(idProductoMoneda, moneda, precio, precionuevo, prod);
                listarProductoMoneda.add(pm);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listBy de ProductoMoneda: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarProductoMoneda;
    }
}
