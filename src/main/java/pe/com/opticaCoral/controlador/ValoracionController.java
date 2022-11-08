package pe.com.opticaCoral.controlador;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.Comentario;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.beans.Producto;
import pe.com.opticaCoral.beans.Valoracion;
import pe.com.opticaCoral.dao.ComentarioJDBCDAO;
import pe.com.opticaCoral.dao.ValoracionJDBCDAO;

public class ValoracionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {

            String accion = request.getParameter("accion");

            HttpSession sesion = request.getSession();
            Login login = (Login) sesion.getAttribute("login");

            if (login != null) {

                if (accion.equals("mostrar")) {

                    int idProducto = Integer.parseInt(request.getParameter("idProducto"));

                    int idPersona = login.getIdPersona();

                    request.setAttribute("idProducto", idProducto);
                    request.setAttribute("idCliente", idPersona);

                    ValoracionJDBCDAO valDao = new ValoracionJDBCDAO();
                    int valor = valDao.valoracionCliente(idProducto, idPersona);
                    request.setAttribute("valor", valor);

                    ComentarioJDBCDAO comDao = new ComentarioJDBCDAO();
                    List<Comentario> listComentarios = comDao.listAllComentariosxProducto(idProducto);
                    request.setAttribute("comentarios", listComentarios);

                    request.getRequestDispatcher("/WEB-INF/valoraciones/estrella.jsp").forward(request, response);

                } else {

                }
            } else {
                System.out.println("REGISTRATE");

                request.setAttribute("valoracion", "true");
                request.getRequestDispatcher("/WEB-INF/login/index.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "estrella":
                    guardarCalificacion(request, response);
                    break;
                case "comentario":
                    guardarComentario(request, response);
                    break;
            }
        }

        response.sendRedirect("home");

    }

    private void guardarCalificacion(HttpServletRequest request, HttpServletResponse response) {
        int cali = Integer.parseInt(request.getParameter("califi"));

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto p = new Producto();
        p.setIdProducto(idProducto);

        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Persona per = new Persona();
        per.setIdPersona(idCliente);
        Cliente cli = new Cliente();
        cli.setIdPersona(per);

        Valoracion val = new Valoracion();
        val.setContador(cali);
        val.setIdProducto(p);
        val.setIdPersona(cli);

        ValoracionJDBCDAO valDao = new ValoracionJDBCDAO();
        System.out.println(valDao.insert(val));
    }

    private void guardarComentario(HttpServletRequest request, HttpServletResponse response) {

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto p = new Producto();
        p.setIdProducto(idProducto);

        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Persona per = new Persona();
        per.setIdPersona(idCliente);
        Cliente cli = new Cliente();
        cli.setIdPersona(per);

        String descripcion = request.getParameter("comentario");

        Comentario comen = new Comentario();
        java.util.Date d = new java.util.Date();
        java.sql.Date fecha = new java.sql.Date(d.getTime());
        comen.setDescripcion(descripcion);
        comen.setIdProducto(p);
        comen.setIdPersona(cli);
        comen.setFecha(fecha);

        ComentarioJDBCDAO comenDao = new ComentarioJDBCDAO();

        System.out.println(comenDao.insert(comen));
    }

}
