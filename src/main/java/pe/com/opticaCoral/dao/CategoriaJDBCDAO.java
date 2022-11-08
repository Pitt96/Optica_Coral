package pe.com.opticaCoral.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.com.opticaCoral.beans.Categoria;
import pe.com.opticaCoral.conexion.Conexion;
import pe.com.opticaCoral.interfaces.ICategoria;

public class CategoriaJDBCDAO implements ICategoria {

    private Connection userConn;
    private final String SQL_SELECTBY = "SELECT * FROM tcategoria WHERE idCategoria = ?;";
    private final String SQL_SELECT = "SELECT * FROM tcategoria;";
    private final String SQL_COUNT_CATEGORIA = "SELECT COUNT(*) AS cantidad FROM tcategoria WHERE categoriaId = ? AND idCategoria != ?";

    public CategoriaJDBCDAO() {
    }

    public CategoriaJDBCDAO(Connection userConn) {
        this.userConn = userConn;
    }

    @Override
    public List<Categoria> listAll() {
        Categoria cat;
        List<Categoria> listarCategorias = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                String nombre = rs.getString("nombre");
                boolean visible = rs.getBoolean("visible");
                int categoriaId = rs.getInt("categoriaId");

                Categoria cate = findById(categoriaId);

                cat = new Categoria(idCategoria, nombre, visible, cate);
                if (categoriaId > 0) {
                    cat.setPrincipal(true);
                }

                listarCategorias.add(cat);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listAll de Categorias: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return listarCategorias;
    }

    @Override
    public Categoria findById(int idCategoria) {
        Categoria cat = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_SELECTBY);
            ps.setLong(1, idCategoria);
            rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("idCategoria");
                String nombre = rs.getString("nombre");
                boolean visible = rs.getBoolean("visible");
                int categoriaId = rs.getInt("categoriaId");

                Categoria cate = findById(categoriaId);
                cat = new Categoria(id, nombre, visible, cate);

                if (categoriaId > 0) {
                    cat.setPrincipal(true);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar CategoriaBy: " + ex.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return cat;
    }

    @Override
    public boolean contarCategoria(int idCategoria) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            ps = conn.prepareStatement(SQL_COUNT_CATEGORIA);
            ps.setInt(1, idCategoria);
            ps.setInt(2, idCategoria);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt("cantidad") > 0;

        } catch (SQLException ex) {
            System.out.println("Error en contarCategoria: " + ex.getMessage());
            return false;
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
    }
}
