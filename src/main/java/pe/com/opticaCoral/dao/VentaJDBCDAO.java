package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.Item;
import pe.com.opticaCoral.beans.Venta;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.correo.Emails;
import pe.com.opticaCoral.interfaces.IVenta;

public class VentaJDBCDAO implements IVenta {

    private Connection userConn;

    private final String SQL_INSERT_VENTA = "INSERT INTO tventa VALUES(NULL, ?, 0, 0, 0, 0, ?,'PAGADO EN ESPERA DE ENVIO', 0);";

    private final String SQL_INSERT_DETALLE = "INSERT INTO tdetalleventa (idDetalleVenta, idProducto, idVenta, cantidad, valorUnitario, importe, descuento, base, impuestos)\n"
            + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String SQL_UPDATE_VENTAS = "UPDATE tventa SET totalNeto = ?, totalDescuento = ?, igv = ?, totalImporte = ?, costoEnvio = ? WHERE idVenta = ?;";

    private final String SQL_INSERT_KARDEX = "INSERT INTO tkardex(idKardex, fecha, stockanterior, entrada, salida, stockactual, idProducto)\n"
            + "VALUES(NULL, ?, ?, ?, ?, ?, ?);";
    private final String SQL_SELECT_STOCK = "SELECT stock FROM tproducto WHERE idProducto = ?;";

    private final String SQL_SELECT_VENTAS_CLIENTES = "SELECT * FROM tventa WHERE ClienteidPersona = ?;";

    private final String SQL_SELECT_BY = "SELECT * FROM tventa WHERE idVenta = ?;";

    private final String SQL_SELECT = "SELECT * FROM tventa;";

    private final String SQL_UPDATE_ESTADO_VENTA = "UPDATE tventa SET estado = ? WHERE idVenta = ?;";

    public VentaJDBCDAO() {
    }

    public VentaJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public String updateVenta(Venta v) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_ESTADO_VENTA);
            ps.setString(1, v.getEstado());
            ps.setInt(2, v.getIdVenta());

            ps.executeUpdate();
            mensaje = "El estado de Venta se ha actualizado correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al actualizar estado de Venta: " + ex.getMessage());
            mensaje = "No fue posible actualizar estado de Venta: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public List<Venta> listAll() {
        List<Venta> listVenta = new ArrayList<>();
        Venta venta = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);

            rs = ps.executeQuery();

            while (rs.next()) {
                int idVen = rs.getInt("idVenta");
                Date fecha = rs.getDate("fecha");
                double totalNeto = rs.getDouble("totalNeto");
                double igv = rs.getDouble("igv");
                double totalDescuento = rs.getDouble("totalDescuento");
                double totalImporte = rs.getDouble("totalImporte");
                int idCliente = rs.getInt("ClienteidPersona");
                String estado = rs.getString("estado");
                double costoEnvio = rs.getDouble("costoEnvio");

                ClienteJDBCDAO cliDao = new ClienteJDBCDAO();
                Cliente cliente = cliDao.findByIdPerfil(idCliente);

                venta = new Venta(idVen, fecha, totalNeto, igv, totalDescuento, totalImporte, cliente, estado, costoEnvio);
                System.out.println("estadso: " + venta.getEstado());
                listVenta.add(venta);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de venta: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listVenta;
    }

    @Override
    public Venta findById(int idVenta) {
        Venta venta = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY);
            ps.setInt(1, idVenta);

            rs = ps.executeQuery();

            if (rs.next()) {
                int idVen = rs.getInt("idVenta");
                Date fecha = rs.getDate("fecha");
                double totalNeto = rs.getDouble("totalNeto");
                double igv = rs.getDouble("igv");
                double totalDescuento = rs.getDouble("totalDescuento");
                double totalImporte = rs.getDouble("totalImporte");
                int idCliente = rs.getInt("ClienteidPersona");
                String estado = rs.getString("estado");
                double costoEnvio = rs.getDouble("costoEnvio");

                ClienteJDBCDAO cliDao = new ClienteJDBCDAO();
                Cliente cliente = cliDao.findByIdPerfil(idCliente);

                venta = new Venta(idVen, fecha, totalNeto, igv, totalDescuento, totalImporte, cliente, estado, costoEnvio);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listBy de venta: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return venta;
    }

    @Override
    public List<Venta> listAllVentasClientes(int idPersona) {
        Venta venta;
        List<Venta> listarVentas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_VENTAS_CLIENTES);
            ps.setInt(1, idPersona);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idVenta = rs.getInt("idVenta");
                Date fecha = rs.getDate("fecha");
                double totalNeto = rs.getDouble("totalNeto");
                double igv = rs.getDouble("igv");
                double totalDescuento = rs.getDouble("totalDescuento");
                double totalImporte = rs.getDouble("totalImporte");
                int idCliente = rs.getInt("ClienteidPersona");
                String estado = rs.getString("estado");
                double costoEnvio = rs.getDouble("costoEnvio");

                ClienteJDBCDAO cliDao = new ClienteJDBCDAO();
                Cliente cliente = cliDao.findByIdPerfil(idCliente);
                venta = new Venta(idVenta, fecha, totalNeto, igv, totalDescuento, totalImporte, cliente, estado, costoEnvio);
                listarVentas.add(venta);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de VentasClientes: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarVentas;
    }

    @Override
    public String insert(Venta v) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps4 = null;
        try {
            int idGeneradoVent = 0;
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ps = conn.prepareStatement(SQL_INSERT_VENTA, Statement.RETURN_GENERATED_KEYS);
//idVenta - 1. fecha - totalNeto - igv - totalDescuento - totalImporte - 2. ClienteidPersona - estado
            java.util.Date d = new java.util.Date();
            java.sql.Date fecha = new java.sql.Date(d.getTime());
            ps.setDate(1, fecha);//
            ps.setDouble(2, v.getClienteidPersona().getIdPersona().getIdPersona());//

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGeneradoVent = generatedKeys.getInt(1);
            }
//idProducto - idVenta - cantidad - precio - (importe) - descuento - (base) - (impuestos)
            double importe = 0;
            double base = 0;
            double impuesto = 0;
            //double tasa = 18.0;

            double sumaImporte = 0;
            double sumaImpuesto = 0;
            double sumaDescuento = 0;

            for (Item dv : v.getItemDetalles()) {
                ps2 = conn.prepareStatement(SQL_INSERT_DETALLE);

                importe = dv.getCantidad() * dv.getP().getPrecio();
                base = importe - dv.getP().getPrecionuevo();
                impuesto = (base * dv.getP().getIgv()) / 100;

                sumaImporte += importe;
                sumaImpuesto += impuesto;
                sumaDescuento += dv.getP().getPrecionuevo();

                ps2.setInt(1, dv.getP().getIdProducto());
                ps2.setInt(2, idGeneradoVent);
                ps2.setDouble(3, dv.getCantidad());
                ps2.setDouble(4, dv.getP().getPrecio());
                ps2.setDouble(5, importe);
                ps2.setDouble(6, dv.getP().getPrecionuevo());
                ps2.setDouble(7, base);
                ps2.setDouble(8, impuesto);
                ps2.executeUpdate();

                //fecha, stockanterior, entrada, salida, stockactual, idProducto
                int stockAnterior = stockAnterior(dv.getP().getIdProducto());
                int stockActual = stockAnterior + 0 - dv.getCantidad();
                ps4 = conn.prepareStatement(SQL_INSERT_KARDEX);
                ps4.setDate(1, fecha);//fecha
                ps4.setDouble(2, stockAnterior);//stock anterior 
                ps4.setDouble(3, 0);//es cero porque sale y no entra
                ps4.setDouble(4, dv.getCantidad());//se ingresa la cantidad que sale del producto
                ps4.setInt(5, stockActual);//
                ps4.setDouble(6, dv.getP().getIdProducto());//id del producto
                ps4.executeUpdate();
            }

            ps3 = conn.prepareStatement(SQL_UPDATE_VENTAS);
            /* total - sumaDescuento - sumaImpuestos - sumaImportes - idVenta */
            double total = sumaImporte - sumaDescuento + sumaImpuesto + v.getCostoEnvio();
            ps3.setDouble(1, total);
            ps3.setDouble(2, sumaDescuento);
            ps3.setDouble(3, sumaImpuesto);
            ps3.setDouble(4, sumaImporte);
            ps3.setDouble(5, v.getCostoEnvio());//
            ps3.setInt(6, idGeneradoVent);

            ps3.executeUpdate();
            conn.commit();
            mensaje = "La venta se ha generado correctamente.";

//            Emails send = new Emails();
//            try {
//                send.enviarCorreo("indicedepublicaciones@gmail.com", "Su compra se ha realizado con exito", v);
//            } catch (MessagingException ex) {
//                Logger.getLogger(VentaJDBCDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    System.err.print("La transacción no pudo realizarse");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.println("no pudo hacerse el rollback de la transacción");
                }
            }
            System.out.println("Error al registrar venta: " + ex.getMessage());
            mensaje = "No fue posible registrar la venta: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            Conexion.close(ps2);
            Conexion.close(ps3);
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
    public int stockAnterior(int idProducto) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_STOCK);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar ValoracionBy promedio: " + ex.getMessage());
            return 0;
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
    }
}
