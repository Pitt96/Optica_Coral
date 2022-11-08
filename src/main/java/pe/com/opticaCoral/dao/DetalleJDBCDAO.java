package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.DetalleVenta;
import pe.com.opticaCoral.beans.Producto;
import pe.com.opticaCoral.beans.Venta;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IDetalleVenta;

public class DetalleJDBCDAO implements IDetalleVenta {

    private Connection userConn;
    private final String SQL_SELECT_DETALLE = "SELECT * FROM tdetalleventa WHERE idVenta = ?;";

    public DetalleJDBCDAO() {
    }

    public DetalleJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public List<DetalleVenta> listAllDetallesVentasClientes(int idVenta) {
        DetalleVenta detalleVenta;
        List<DetalleVenta> listarDetalleVentas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_DETALLE);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idDetalleVenta = rs.getInt("idDetalleVenta");
                int idProducto = rs.getInt("idProducto");
                int idVen = rs.getInt("idVenta");
                int cantidad = rs.getInt("cantidad");
                double valorUnitario = rs.getDouble("valorUnitario");
                double importe = rs.getDouble("importe");
                double descuento = rs.getDouble("descuento");
                double base = rs.getDouble("base");
                double impuesto = rs.getDouble("impuestos");

                ProductoJDBCDAO proDao = new ProductoJDBCDAO();
                Producto producto = proDao.findById(idProducto);

                VentaJDBCDAO venDao = new VentaJDBCDAO();
                Venta venta = venDao.findById(idVen);

                detalleVenta = new DetalleVenta(idDetalleVenta, producto, venta, cantidad, valorUnitario, importe, descuento, base, impuesto);
                listarDetalleVentas.add(detalleVenta);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de DetalleVentasClientes: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarDetalleVentas;
    }

}
