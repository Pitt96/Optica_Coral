package pe.com.opticaCoral.controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.com.opticaCoral.beans.Empleado;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.dao.EmpleadoJDBCDAO;
import pe.com.opticaCoral.excepciones.ErrorLoginException;
import pe.com.opticaCoral.interfaces.IEmpleado;

public class AdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Empleado login = (Empleado) sesion.getAttribute("loginE");

        if (login != null) {

            if (request.getParameter("accion") != null) {
                String accion = request.getParameter("accion");
                switch (accion) {
                    case "crudempleado": //Ir al servlet empleado
                        response.sendRedirect("empleados");
                        break;
                    case "crudproducto": //Ir al servlet producto
                        response.sendRedirect("productos");
                        break;
                    case "home": //Regresar a la tienda y cerrar sesion
                        sesion.removeAttribute("loginE");
                        response.sendRedirect("home");
                        break;
                    case "cerrarSession": //Cerrar sesion y regresar a login
                        sesion.removeAttribute("loginE");
                        response.sendRedirect("admin");
                        break;
                    case "ventas":
                        response.sendRedirect("ventas");
                        break;
                }
            } else {
                request.getRequestDispatcher("/WEB-INF/empleados/inicio.jsp").forward(request, response);
            }

        } else {
            request.getRequestDispatcher("/WEB-INF/empleados/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "validarUsuario":
                    validarUsuario(request, response);
                    break;
            }
        }

    }

    private void validarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String clave = request.getParameter("password");
        //String passwordEncriptado = encriptarPassword(clave);

        Login login = new Login();
        
        login.setEmail(email);

        boolean validarDireccionEmail = login.validarEmail();

        if (!validarDireccionEmail) {
            throw new ServletException(
                    new ErrorLoginException("Proporcione una dirección email válida"));
        }

        IEmpleado loginDao = new EmpleadoJDBCDAO();
        Empleado loginSesion = loginDao.validarLoginEmpleado(email, clave);

        if (loginSesion != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("loginE", loginSesion);

            System.out.println("USUARIO " + loginSesion.getIdPersona().getIdPersona());

            request.getRequestDispatcher("WEB-INF/empleados/inicio.jsp").forward(request, response);

            //response.sendRedirect(request.getContextPath() + "/WEB-INF/listarProductos.jsp");
            //request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/listarProductos.jsp").forward(request, response);
        } else {
            throw new ServletException(
                    new ErrorLoginException("No existe ningun usuario con estas credenciales"));
        }
    }

    /*
    private String encriptarPassword(String pass) {
        return DigestUtils.md5Hex(pass);
    }*/
}
