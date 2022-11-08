package pe.com.opticaCoral.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Categoria;
import pe.com.opticaCoral.beans.Empleado;
import pe.com.opticaCoral.beans.Marca;
import pe.com.opticaCoral.beans.Producto;
import pe.com.opticaCoral.beans.ProductoMoneda;
import pe.com.opticaCoral.beans.Proveedor;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.IProducto;

public class ProductoJDBCDAO implements IProducto {

    private Connection userConn;

    private final String SQL_SELECT_BY2 = "CALL consultaproducto_id(?, ?)";
    private final String SQL_SELECT_MONEDA = "CALL listar_productos_moneda(?);";
    private final String SQL_SELECT_CATEGORIA = "CALL listar_productos_categoria(?, ?);";
    private final String SQL_SELECT_MARCA = "CALL listar_productos_marca(?, ?);";
    private final String SQL_SELECT_BUSCAR1 = "SELECT p.*, m.precio AS precio2, m.precionuevo AS precion2 FROM tproducto p\n"
            + "INNER JOIN tproductomoneda m ON m.idProducto = p.idProducto\n"
            + "INNER JOIN tmarca mar ON mar.idMarca = p.idMarca\n"
            + "INNER JOIN tcategoria c ON c.idCategoria = p.idCategoria\n"
            + "WHERE p.visible = TRUE AND c.visible = TRUE AND mar.visible = TRUE AND m.moneda = ? AND p.nombre LIKE CONCAT( '%',?,'%') ORDER BY p.nombre ASC;";
    private final String SQL_SELECT_BUSCAR2 = "SELECT p.* FROM tproducto p\n"
            + "INNER JOIN tmarca mar ON mar.idMarca = p.idMarca\n"
            + "INNER JOIN tcategoria c ON c.idCategoria = p.idCategoria\n"
            + "WHERE p.visible = TRUE AND c.visible = TRUE AND mar.visible = TRUE AND p.nombre LIKE CONCAT( '%',?,'%') ORDER BY p.nombre ASC;";

    private final String SQL_SELECT = "SELECT * FROM tproducto";
    private final String SQL_SELECT_BY = "SELECT * FROM tproducto WHERE idProducto = ?";

    private final String SQL_INSERT_PRODUCTO = "INSERT INTO tproducto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, idCategoria, idMarca, idProveedor, empleadoidPersona, igv)\n"
            + "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String SQL_INSERT_MONEDA = "INSERT INTO tproductomoneda(idProductoMoneda, moneda, precio, precionuevo, idProducto) \n"
            + "VALUES (NULL, ?, ?, ?, ?);";
    private final String SQL_INSERT_KARDEX = "INSERT INTO tkardex(idKardex, fecha, stockanterior, entrada, salida, stockactual, idProducto)\n"
            + "VALUES(NULL, ?, ?, ?, ?, ?, ?);";

    private final String SQL_UPDATE = "UPDATE tproducto SET nombre = ?, precio = ?, precionuevo = ?, descripcion = ?, imagen = ?,\n"
            + "video = ?, recomendado = ?, visible = ?, idCategoria = ?, idMarca = ?, igv = ?, \n"
            + "idProveedor = ?, empleadoidPersona = ?\n"
            + "WHERE idProducto = ?;";
    private final String SQL_UPDATE2 = "UPDATE tproducto SET nombre = ?, precio = ?, precionuevo = ?, descripcion = ?, imagen = ?,\n"
            + "recomendado = ?, visible = ?, idCategoria = ?, idMarca = ?, igv = ?, \n"
            + "idProveedor = ?, empleadoidPersona = ?\n"
            + "WHERE idProducto = ?;";
    private final String SQL_UPDATE3 = "UPDATE tproducto SET nombre = ?, precio = ?, precionuevo = ?, descripcion = ?,\n"
            + "video = ?, recomendado = ?, visible = ?, idCategoria = ?, idMarca = ?, igv = ?, \n"
            + "idProveedor = ?, empleadoidPersona = ?\n"
            + "WHERE idProducto = ?;";
    private final String SQL_UPDATE4 = "UPDATE tproducto SET nombre = ?, precio = ?, precionuevo = ?, descripcion = ?,\n"
            + "recomendado = ?, visible = ?, idCategoria = ?, idMarca = ?, igv = ?, \n"
            + "idProveedor = ?, empleadoidPersona = ?\n"
            + "WHERE idProducto = ?;";

    private final String SQL_UPDATE_STOCK = "INSERT INTO tkardex(idKardex, fecha, stockanterior, entrada, salida, stockactual, idProducto)\n"
            + "VALUES(NULL, ?, ?, ?, ?, ?, ?);";

    public ProductoJDBCDAO() {
    }

    public ProductoJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public List<Producto> listAllProductoBuscar(String moneda, String valor) {
        Producto producto;
        List<Producto> listarProductos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            
            if (!moneda.trim().equals("PEN")) {
                ps = conn.prepareStatement(SQL_SELECT_BUSCAR1);
                ps.setString(1, moneda);
                ps.setString(2,  valor );
                rs = ps.executeQuery();
                while (rs.next()) {
                    int idProducto = rs.getInt("idProducto");
                    String nombre = rs.getString("nombre");
                    double precio = 0;
                    double precionuevo = 0;
                    if (!moneda.equalsIgnoreCase("PEN")) {
                        precio = rs.getDouble("precio2");
                        precionuevo = rs.getDouble("precion2");
                    } else {
                        precio = rs.getDouble("precio");
                        precionuevo = rs.getDouble("precionuevo");
                    }
                    String descripcion = rs.getString("descripcion");
                    String imagen = rs.getString("imagen");
                    String video = rs.getString("video");
                    boolean recomendado = rs.getBoolean("recomendado");
                    int stock = rs.getInt("stock");
                    boolean visible = rs.getBoolean("visible");
                    int idCategoria = rs.getInt("idCategoria");
                    int idMarca = rs.getInt("idMarca");
                    int idProveedor = rs.getInt("idProveedor");
                    int empleadoidPersona = rs.getInt("empleadoidPersona");
                    double igv = rs.getDouble("igv");

                    Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                    Marca mar = new MarcaJDBCDAO().findById(idMarca);
                    Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                    Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                    producto = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp, igv);

                    listarProductos.add(producto);
                }
            } else {
                ps = conn.prepareStatement(SQL_SELECT_BUSCAR2);
                ps.setString(1, valor );
                rs = ps.executeQuery();
                while (rs.next()) {
                    int idProducto = rs.getInt("idProducto");
                    String nombre = rs.getString("nombre");
                    double precio = 0;
                    double precionuevo = 0;
                    if (!moneda.equalsIgnoreCase("PEN")) {
                        precio = rs.getDouble("precio2");
                        precionuevo = rs.getDouble("precion2");
                    } else {
                        precio = rs.getDouble("precio");
                        precionuevo = rs.getDouble("precionuevo");
                    }
                    String descripcion = rs.getString("descripcion");
                    String imagen = rs.getString("imagen");
                    String video = rs.getString("video");
                    boolean recomendado = rs.getBoolean("recomendado");
                    int stock = rs.getInt("stock");
                    boolean visible = rs.getBoolean("visible");
                    int idCategoria = rs.getInt("idCategoria");
                    int idMarca = rs.getInt("idMarca");
                    int idProveedor = rs.getInt("idProveedor");
                    int empleadoidPersona = rs.getInt("empleadoidPersona");
                    double igv = rs.getDouble("igv");

                    Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                    Marca mar = new MarcaJDBCDAO().findById(idMarca);
                    Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                    Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                    producto = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp, igv);

                    listarProductos.add(producto);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Productos Busqueda: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarProductos;
    }

    @Override
    public String updateStock(Producto producto) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ps = conn.prepareCall(SQL_UPDATE_STOCK);

            java.util.Date d = new java.util.Date();
            java.sql.Date fecha = new java.sql.Date(d.getTime());

            int v_stockanterior = 0;
            ps2 = conn.prepareStatement("SELECT tproducto.stock FROM tproducto WHERE tproducto.idProducto = ?");
            ps2.setInt(1, producto.getIdProducto());
            ResultSet rs1 = ps2.executeQuery();
            if (rs1.next()) {
                v_stockanterior = rs1.getInt(1);
            }

            int v_stockactual = (v_stockanterior + producto.getStock() - 0);

            //fecha, stockanterior, entrada, salida, stockactual, idProducto
            ps.setDate(1, fecha);//
            ps.setInt(2, v_stockanterior);
            ps.setInt(3, producto.getStock());
            ps.setInt(4, 0);
            ps.setInt(5, v_stockactual);
            ps.setInt(6, producto.getIdProducto());
            ps.executeUpdate();

            conn.commit();

            mensaje = "El stock del producto se ha actualizado correctamente.";

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    System.err.print("La transacción no pudo realizarse");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.println("no pudo hacerse el rollback de la transacción");
                }
            }
            System.out.println("Error al actualizar stock del producto: " + ex.getMessage());
            mensaje = "No fue posible actualizar stock del producto: " + ex.getMessage();
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
    public String update(Producto producto) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();

            if (producto.getImagen() != null && producto.getVideo() != null) {
                ps = conn.prepareStatement(SQL_UPDATE);
//nombre - precio - precionuevo - descripcion - imagen - video - recomendado - visible - idCategoria - idMarca - idProveedor - empleadoidPersona - idProducto
                ps.setString(1, producto.getNombre());//
                ps.setDouble(2, producto.getPrecio());//
                ps.setDouble(3, producto.getPrecionuevo());//
                ps.setString(4, producto.getDescripcion());//
                ps.setString(5, producto.getImagen());//
                ps.setString(6, producto.getVideo());//
                ps.setBoolean(7, producto.isRecomendado());//
                ps.setBoolean(8, producto.isVisible());//
                ps.setInt(9, producto.getIdCategoria().getIdCategoria());//
                ps.setInt(10, producto.getIdMarca().getIdMarca());//
                ps.setDouble(11, producto.getIgv());
                ps.setInt(12, producto.getIdProveedor().getIdProveedor());//
                ps.setInt(13, producto.getEmpleadoidPersona().getIdPersona().getIdPersona());//
                ps.setInt(14, producto.getIdProducto());
            } else if (producto.getImagen() != null && producto.getVideo() == null) {
                ps = conn.prepareStatement(SQL_UPDATE2);
//nombre - precio - precionuevo - descripcion - imagen - recomendado - visible - idCategoria - idMarca - idProveedor - empleadoidPersona - idProducto
                ps.setString(1, producto.getNombre());//
                ps.setDouble(2, producto.getPrecio());//
                ps.setDouble(3, producto.getPrecionuevo());//
                ps.setString(4, producto.getDescripcion());//
                ps.setString(5, producto.getImagen());//
                ps.setBoolean(6, producto.isRecomendado());//
                ps.setBoolean(7, producto.isVisible());//
                ps.setInt(8, producto.getIdCategoria().getIdCategoria());//
                ps.setInt(9, producto.getIdMarca().getIdMarca());//
                ps.setDouble(10, producto.getIgv());
                ps.setInt(11, producto.getIdProveedor().getIdProveedor());//
                ps.setInt(12, producto.getEmpleadoidPersona().getIdPersona().getIdPersona());//
                ps.setInt(13, producto.getIdProducto());
            } else if (producto.getImagen() == null && producto.getVideo() != null) {
                ps = conn.prepareStatement(SQL_UPDATE3);
//nombre - precio - precionuevo - descripcion - video - recomendado - visible - idCategoria - idMarca - idProveedor - empleadoidPersona - idProducto
                ps.setString(1, producto.getNombre());//
                ps.setDouble(2, producto.getPrecio());//
                ps.setDouble(3, producto.getPrecionuevo());//
                ps.setString(4, producto.getDescripcion());//
                ps.setString(5, producto.getVideo());//
                ps.setBoolean(6, producto.isRecomendado());//
                ps.setBoolean(7, producto.isVisible());//
                ps.setInt(8, producto.getIdCategoria().getIdCategoria());//
                ps.setInt(9, producto.getIdMarca().getIdMarca());//
                ps.setDouble(10, producto.getIgv());
                ps.setInt(11, producto.getIdProveedor().getIdProveedor());//
                ps.setInt(12, producto.getEmpleadoidPersona().getIdPersona().getIdPersona());//
                ps.setInt(13, producto.getIdProducto());
            } else if (producto.getImagen() == null && producto.getVideo() == null) {
                ps = conn.prepareStatement(SQL_UPDATE4);
//nombre - precio - precionuevo - descripcion - recomendado - visible - idCategoria - idMarca - idProveedor - empleadoidPersona - idProducto
                ps.setString(1, producto.getNombre());//
                ps.setDouble(2, producto.getPrecio());//
                ps.setDouble(3, producto.getPrecionuevo());//
                ps.setString(4, producto.getDescripcion());//
                ps.setBoolean(5, producto.isRecomendado());//
                ps.setBoolean(6, producto.isVisible());//
                ps.setInt(7, producto.getIdCategoria().getIdCategoria());//
                ps.setInt(8, producto.getIdMarca().getIdMarca());//
                ps.setDouble(9, producto.getIgv());
                ps.setInt(10, producto.getIdProveedor().getIdProveedor());//
                ps.setInt(11, producto.getEmpleadoidPersona().getIdPersona().getIdPersona());//
                ps.setInt(12, producto.getIdProducto());
            }

            ps.executeUpdate();
            mensaje = "El producto se ha actualizado correctamente.";

        } catch (SQLException ex) {
            System.out.println("Error al actualizar producto: " + ex.getMessage());
            mensaje = "No fue posible actualizar el producto: " + ex.getMessage();
        } finally {
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return mensaje;
    }

    @Override
    public String insert(Producto producto) {
        String mensaje = "";
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            int idGeneradoPro = 0;
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            ps = conn.prepareStatement(SQL_INSERT_PRODUCTO, Statement.RETURN_GENERATED_KEYS);
//nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, idCategoria, idMarca, idProveedor, empleadoidPersona
            ps.setString(1, producto.getNombre());//
            ps.setDouble(2, producto.getPrecio());//
            ps.setDouble(3, producto.getPrecionuevo());//
            ps.setString(4, producto.getDescripcion());//
            ps.setString(5, producto.getImagen());//
            ps.setString(6, producto.getVideo());//
            ps.setBoolean(7, producto.isRecomendado());//
            ps.setInt(8, 0);//el trigger se encargara de actualizar el stock ingresado
            ps.setBoolean(9, producto.isVisible());//
            ps.setInt(10, producto.getIdCategoria().getIdCategoria());//
            ps.setInt(11, producto.getIdMarca().getIdMarca());//
            ps.setInt(12, producto.getIdProveedor().getIdProveedor());//
            ps.setInt(13, producto.getEmpleadoidPersona().getIdPersona().getIdPersona());//
            ps.setDouble(14, producto.getIgv());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGeneradoPro = generatedKeys.getInt(1);
            }
            //moneda, precio, precionuevo, idProducto
            for (ProductoMoneda pm : producto.getProductoMoneda()) {
                ps2 = conn.prepareStatement(SQL_INSERT_MONEDA);
                ps2.setString(1, pm.getMoneda());
                ps2.setDouble(2, pm.getPrecio());
                ps2.setDouble(3, pm.getPrecionuevo());
                ps2.setInt(4, idGeneradoPro);
                ps2.executeUpdate();
            }

            java.util.Date d = new java.util.Date();
            java.sql.Date fecha = new java.sql.Date(d.getTime());
            //fecha, stockanterior, entrada, salida, stockactual, idProducto
            ps2 = conn.prepareStatement(SQL_INSERT_KARDEX);
            ps2.setDate(1, fecha);
            ps2.setDouble(2, 0);//stock anterior es 0 porque recien se ingresa
            ps2.setDouble(3, producto.getStock());//Se lee de producto - entrada
            ps2.setDouble(4, 0);//es cero porque se esta ingresando - salida
            ps2.setInt(5, producto.getStock());//Es el mismo que se ingreso - actual
            ps2.setDouble(6, idGeneradoPro);//id del producto
            ps2.executeUpdate();

            conn.commit();
            mensaje = "El producto se ha creado correctamente.";

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    System.err.print("La transacción no pudo realizarse");
                    conn.rollback();
                } catch (SQLException excep) {
                    System.err.println("no pudo hacerse el rollback de la transacción");
                }
            }
            System.out.println("Error al registrar producto: " + ex.getMessage());
            mensaje = "No fue posible registrar el producto: " + ex.getMessage();
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
    public List<Producto> listAll() {
        Producto producto;
        List<Producto> listarProductos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);

            rs = ps.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                double precionuevo = rs.getDouble("precionuevo");
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                String video = rs.getString("video");
                boolean recomendado = rs.getBoolean("recomendado");
                int stock = rs.getInt("stock");
                boolean visible = rs.getBoolean("visible");
                int idCategoria = rs.getInt("idCategoria");
                int idMarca = rs.getInt("idMarca");
                int idProveedor = rs.getInt("idProveedor");
                int empleadoidPersona = rs.getInt("empleadoidPersona");
                double igv = rs.getDouble("igv");

                Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                Marca mar = new MarcaJDBCDAO().findById(idMarca);
                Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                producto = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp, igv);

                listarProductos.add(producto);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Productos: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarProductos;
    }

    @Override
    public Producto findById(int idProd) {
        Producto p = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY);
            ps.setInt(1, idProd);

            rs = ps.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                double precionuevo = rs.getDouble("precionuevo");
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                String video = rs.getString("video");
                boolean recomendado = rs.getBoolean("recomendado");
                int stock = rs.getInt("stock");
                boolean visible = rs.getBoolean("visible");
                int idCategoria = rs.getInt("idCategoria");
                int idMarca = rs.getInt("idMarca");
                int idProveedor = rs.getInt("idProveedor");
                int empleadoidPersona = rs.getInt("empleadoidPersona");
                double igv = rs.getDouble("igv");

                Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                Marca mar = new MarcaJDBCDAO().findById(idMarca);
                Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                p = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp, igv);

            }

        } catch (SQLException ex) {
            System.out.println("Error en listBy de Productos: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return p;
    }

    @Override
    public Producto findById2(String moneda, int idProd) {
        Producto p = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_BY2);
            ps.setString(1, moneda);
            ps.setInt(2, idProd);

            rs = ps.executeQuery();

            if (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");

                double precio = 0;
                double precionuevo = 0;
                if (!moneda.equalsIgnoreCase("PEN")) {
                    precio = rs.getDouble("precio2");
                    precionuevo = rs.getDouble("precion2");
                } else {
                    precio = rs.getDouble("precio");
                    precionuevo = rs.getDouble("precionuevo");
                }

                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                String video = rs.getString("video");
                boolean recomendado = rs.getBoolean("recomendado");
                int stock = rs.getInt("stock");
                boolean visible = rs.getBoolean("visible");
                int idCategoria = rs.getInt("idCategoria");
                int idMarca = rs.getInt("idMarca");
                int idProveedor = rs.getInt("idProveedor");
                int empleadoidPersona = rs.getInt("empleadoidPersona");
                double igv = rs.getDouble("igv");

                Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                Marca mar = new MarcaJDBCDAO().findById(idMarca);
                Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                p = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp, igv);

            }

        } catch (SQLException ex) {
            System.out.println("Error en listBy2 de Productos: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return p;
    }

    @Override
    public List<Producto> listAllProductoMoneda(String moneda) {
        Producto producto;
        List<Producto> listarProductos = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            cs = conn.prepareCall(SQL_SELECT_MONEDA);
            cs.setString(1, moneda);

            rs = cs.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                double precio = 0;
                double precionuevo = 0;
                if (!moneda.equalsIgnoreCase("PEN")) {
                    precio = rs.getDouble("precio2");
                    precionuevo = rs.getDouble("precion2");
                } else {
                    precio = rs.getDouble("precio");
                    precionuevo = rs.getDouble("precionuevo");
                }
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                String video = rs.getString("video");
                boolean recomendado = rs.getBoolean("recomendado");
                int stock = rs.getInt("stock");
                boolean visible = rs.getBoolean("visible");
                int idCategoria = rs.getInt("idCategoria");
                int idMarca = rs.getInt("idMarca");
                int idProveedor = rs.getInt("idProveedor");
                int empleadoidPersona = rs.getInt("empleadoidPersona");
                double igv = rs.getDouble("igv");

                Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                Marca mar = new MarcaJDBCDAO().findById(idMarca);
                Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                producto = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp, igv);

                listarProductos.add(producto);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Productos: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(cs);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarProductos;
    }

    @Override
    public List<Producto> listAllProductoCategoria(String moneda, int idCat) {
        Producto producto;
        List<Producto> listarProductos = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            cs = conn.prepareCall(SQL_SELECT_CATEGORIA);
            cs.setString(1, moneda);
            cs.setInt(2, idCat);

            rs = cs.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                double precio = 0;
                double precionuevo = 0;
                if (!moneda.equalsIgnoreCase("PEN")) {
                    precio = rs.getDouble("precio2");
                    precionuevo = rs.getDouble("precion2");
                } else {
                    precio = rs.getDouble("precio");
                    precionuevo = rs.getDouble("precionuevo");
                }
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                String video = rs.getString("video");
                boolean recomendado = rs.getBoolean("recomendado");
                int stock = rs.getInt("stock");
                boolean visible = rs.getBoolean("visible");
                int idCategoria = rs.getInt("idCategoria");
                int idMarca = rs.getInt("idMarca");
                int idProveedor = rs.getInt("idProveedor");
                int empleadoidPersona = rs.getInt("empleadoidPersona");

                Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                Marca mar = new MarcaJDBCDAO().findById(idMarca);
                Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                producto = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp);

                listarProductos.add(producto);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de ProductosCategoria: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(cs);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarProductos;
    }

    @Override
    public List<Producto> listAllProductoMarca(String moneda, int idMar) {
        Producto producto;
        List<Producto> listarProductos = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            cs = conn.prepareCall(SQL_SELECT_MARCA);
            cs.setString(1, moneda);
            cs.setInt(2, idMar);

            rs = cs.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                double precio = 0;
                double precionuevo = 0;
                if (!moneda.equalsIgnoreCase("PEN")) {
                    precio = rs.getDouble("precio2");
                    precionuevo = rs.getDouble("precion2");
                } else {
                    precio = rs.getDouble("precio");
                    precionuevo = rs.getDouble("precionuevo");
                }
                String descripcion = rs.getString("descripcion");
                String imagen = rs.getString("imagen");
                String video = rs.getString("video");
                boolean recomendado = rs.getBoolean("recomendado");
                int stock = rs.getInt("stock");
                boolean visible = rs.getBoolean("visible");
                int idCategoria = rs.getInt("idCategoria");
                int idMarca = rs.getInt("idMarca");
                int idProveedor = rs.getInt("idProveedor");
                int empleadoidPersona = rs.getInt("empleadoidPersona");

                Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
                Marca mar = new MarcaJDBCDAO().findById(idMarca);
                Proveedor provee = new ProveedorJDBCDAO().findById(idProveedor);
                Empleado emp = new EmpleadoJDBCDAO().findById(empleadoidPersona);

                producto = new Producto(idProducto, nombre, precio, precionuevo, descripcion, imagen, video, recomendado, stock, visible, cat, mar, provee, emp);

                listarProductos.add(producto);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de ProductosMarca: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(cs);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return listarProductos;
    }

}
