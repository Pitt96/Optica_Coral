package pe.com.opticaCoral.vistas;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.com.opticaCoral.beans.Categoria;
import pe.com.opticaCoral.beans.Marca;
import pe.com.opticaCoral.beans.Producto;
import pe.com.opticaCoral.dao.CategoriaJDBCDAO;
import pe.com.opticaCoral.dao.MarcaJDBCDAO;
import pe.com.opticaCoral.dao.ProductoJDBCDAO;

public class Home extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Obtiene la session actual
        HttpSession sesion = request.getSession();

        //Al cargar establece por defecto la moneda en soles
        if (sesion.getAttribute("moneda") == null) {
            sesion.setAttribute("moneda", "PEN");
            sesion.setAttribute("nom_moneda", "Moneda en Soles");
        }
        if (request.getParameter("category") != null) {
            sesion.setAttribute("category", Integer.parseInt(request.getParameter("category")));
        } else if (request.getParameter("brand") != null) {
            sesion.setAttribute("brand", Integer.parseInt(request.getParameter("brand")));
        } else {
            sesion.setAttribute("category", 0);
            sesion.setAttribute("brand", 0);
        }

        //Obtiene la session actual
        HttpSession sesion2 = request.getSession();

        ProductoJDBCDAO productoDao = new ProductoJDBCDAO();
        List<Producto> listarProductos = null;

        int idCategoria = 0;
        try {
            idCategoria = Integer.parseInt(request.getParameter("category"));
        } catch (NumberFormatException e) {
            //System.out.println(" " + e.getMessage());
        }

        int idMarca = 0;
        try {
            idMarca = Integer.parseInt(request.getParameter("brand"));
        } catch (NumberFormatException e) {
            //System.out.println(" " + e.getMessage());
        }
        
        String buscar = null;
        try {
            buscar = request.getParameter("valor");
            
        } catch (NumberFormatException e) {
            //System.out.println(" " + e.getMessage());
        }

        if (idCategoria > 0) { //sesion2.getAttribute("category").toString()
            listarProductos = productoDao.listAllProductoCategoria(sesion2.getAttribute("moneda").toString(), idCategoria);
            request.setAttribute("productos", listarProductos);
        } else if (idMarca > 0) {//sesion2.getAttribute("brand").toString()
            listarProductos = productoDao.listAllProductoMarca(sesion2.getAttribute("moneda").toString(), idMarca);
            request.setAttribute("productos", listarProductos);
        }else if(buscar != null){
            listarProductos = productoDao.listAllProductoBuscar(sesion2.getAttribute("moneda").toString(), buscar);
            request.setAttribute("productos", listarProductos);
        } else {
            //Al cargar se le pasa la lista de productos
            listarProductos = productoDao.listAllProductoMoneda(sesion2.getAttribute("moneda").toString());
            request.setAttribute("productos", listarProductos);
        }

        //Al cargar se le pasa la lista de categorias
        CategoriaJDBCDAO cateDao = new CategoriaJDBCDAO();
        List<Categoria> listarCategoria = cateDao.listAll();
        request.setAttribute("categorias", listarCategoria);

        //Al cargar se le pasa la lista de Marcas
        MarcaJDBCDAO marDao = new MarcaJDBCDAO();
        List<Marca> listarMarca = marDao.listAll();
        request.setAttribute("marcas", listarMarca);

        //Se dirige a listarproductos
        request.getRequestDispatcher("/WEB-INF/listarProductos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        System.out.println("POST HOME");
    }

}
