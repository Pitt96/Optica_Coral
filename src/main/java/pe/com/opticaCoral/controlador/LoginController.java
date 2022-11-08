package pe.com.opticaCoral.controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.dao.LoginJDBCDAO;
import pe.com.opticaCoral.excepciones.ErrorLoginException;
import pe.com.opticaCoral.interfaces.ILogin;

public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "cerrarSesion":
                    cerrarSesion(request, response);
                    break;
                case "IniciarSession":
                    request.getRequestDispatcher("/WEB-INF/login/index.jsp").forward(request, response);
                    break;
            }
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
        String valoracion = "false";
        try {
            if (request.getParameter("valoracion") != null) {
                valoracion = request.getParameter("valoracion");
            }
        } catch (Exception e) {

        }
        //String passwordEncriptado = encriptarPassword(clave);

        Login login = new Login();
        login.setEmail(email);
        //login.setClave(passwordEncriptado);
        boolean validarDireccionEmail = login.validarEmail();

        if (!validarDireccionEmail) {
            throw new ServletException(
                    new ErrorLoginException("Proporcione una dirección email válida"));
        }

        ILogin loginDao = new LoginJDBCDAO();
        Login loginSesion = loginDao.validarLogin(email, clave);

        if (loginSesion != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("login", loginSesion);

            System.out.println("USUARIO " + loginSesion.getIdPersona() + " - PRODUCTO " + request.getParameter("idProducto")+" FOTO "+loginSesion.getFoto());
            int idProducto = 0;
            try {
                idProducto = Integer.parseInt(request.getParameter("idProducto"));
            } catch (NumberFormatException e) {
            }

            if (valoracion.equals("true")) {
                response.sendRedirect("home");
            } else if (idProducto != 0) {
                response.sendRedirect("cart?idProducto=" + request.getParameter("idProducto") + "&accion=order");
            } else {
                response.sendRedirect("home");
            }

            //response.sendRedirect(request.getContextPath() + "/WEB-INF/listarProductos.jsp");
            //request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/listarProductos.jsp").forward(request, response);
        } else {
            throw new ServletException(
                    new ErrorLoginException("No existe ningun usuario con estas credenciales"));
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("cart");
        response.sendRedirect("home");
    }

    /*
    private String encriptarPassword(String pass) {
        return DigestUtils.md5Hex(pass);
    }
     */
}
