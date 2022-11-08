package pe.com.opticaCoral.controlador;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CambiarMoneda extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        if (request.getParameter("moneda") != null) {
            switch (request.getParameter("moneda")) {
                case "USD":
                    sesion.setAttribute("moneda", request.getParameter("moneda"));
                    sesion.setAttribute("nom_moneda", "Moneda en Dolares");
                    break;
                case "EUR":
                    sesion.setAttribute("moneda", request.getParameter("moneda"));
                    sesion.setAttribute("nom_moneda", "Moneda en Euros");
                    break;
                case "CNY":
                    sesion.setAttribute("moneda", request.getParameter("moneda"));
                    sesion.setAttribute("nom_moneda", "Moneda en YUAN");
                    break;
                default:
                    sesion.setAttribute("moneda", "PEN");
                    sesion.setAttribute("nom_moneda", "Moneda en Soles");
                    break;

            }
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


}
