package pe.com.opticaCoral.controlador;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.com.opticaCoral.beans.DetalleVenta;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.beans.Venta;
import pe.com.opticaCoral.dao.DetalleJDBCDAO;
import pe.com.opticaCoral.dao.VentaJDBCDAO;

public class VentaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "verCompras":
                    verComprasCliente(request, response);
                    break;
                case "detalleVentaCliente":
                    verDetalleCompraCliente(request, response);
                    break;
                case "correo": {
//                    try {
//                        enviarDetalleporCorreo(request, response);
//                    } catch (MessagingException ex) {
//                        Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
                break;

            }
        } else {
            System.out.println("LISTA VENTAS");
            VentaJDBCDAO ventDao = new VentaJDBCDAO();
            List<Venta> listVenta = ventDao.listAll();

            request.setAttribute("ventas", listVenta);
            request.getRequestDispatcher("/WEB-INF/ventasadmin/index.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "editarEstadoV":
                    editarEstadoVenta(request, response);
                    break;
            }
        }
    }

    private void verComprasCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Login login = (Login) sesion.getAttribute("login");
        int idCliente = login.getIdPersona();

        VentaJDBCDAO ventDao = new VentaJDBCDAO();
        List<Venta> listVenta = ventDao.listAllVentasClientes(idCliente);

        request.setAttribute("ventas", listVenta);
        request.getRequestDispatcher("/WEB-INF/ventasclientes/index.jsp").forward(request, response);

    }

    private void verDetalleCompraCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idVenta = 0;
        if (request.getParameter("idVenta") != null) {
            idVenta = Integer.parseInt(request.getParameter("idVenta"));

            DetalleJDBCDAO dtv = new DetalleJDBCDAO();
            List<DetalleVenta> listaDetalle = dtv.listAllDetallesVentasClientes(idVenta);

            request.setAttribute("detalle", listaDetalle);
            request.getRequestDispatcher("/WEB-INF/ventasclientes/detalle.jsp").forward(request, response);
        } else {
            System.out.println("VENTA NULL " + idVenta);
        }

    }

    private void editarEstadoVenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        String estado = request.getParameter("estadov");

        VentaJDBCDAO ventDao = new VentaJDBCDAO();
        Venta v = new Venta();
        v.setEstado(estado);
        v.setIdVenta(idVenta);
        String mensaje = ventDao.updateVenta(v);

        if (mensaje != null) {
            List<Venta> listVenta = ventDao.listAll();

            request.setAttribute("ventas", listVenta);
            request.getRequestDispatcher("/WEB-INF/ventasadmin/index.jsp").forward(request, response);
        }
    }

}
