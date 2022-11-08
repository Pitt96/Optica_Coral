package pe.com.opticaCoral.vistas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.DetalleVenta;
import pe.com.opticaCoral.beans.Direccion;
import pe.com.opticaCoral.beans.Envio;
import pe.com.opticaCoral.beans.Estado;
import pe.com.opticaCoral.beans.Item;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.beans.Pais;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.beans.Producto;
import pe.com.opticaCoral.beans.Venta;
import pe.com.opticaCoral.correo.Emails;
import pe.com.opticaCoral.dao.ClienteJDBCDAO;
import pe.com.opticaCoral.dao.DetalleJDBCDAO;
import pe.com.opticaCoral.dao.DireccionJDBCDAO;
import pe.com.opticaCoral.dao.EnvioJDBCDAO;
import pe.com.opticaCoral.dao.EstadoJDBCDAO;
import pe.com.opticaCoral.dao.PaisJDBCDAO;
import pe.com.opticaCoral.dao.PersonaJDBCDAO;
import pe.com.opticaCoral.dao.ProductoJDBCDAO;
import pe.com.opticaCoral.dao.VentaJDBCDAO;

public class CartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Verificamos si el cliente esta logueado
        HttpSession sesion = request.getSession();
        Login login = (Login) sesion.getAttribute("login");

        if (login != null) {
            //Verificar que el parametro accion sea diferente de null
            if (request.getParameter("accion") != null) {

                //Obtenemos el valor del parametro accion
                String accion = request.getParameter("accion");
                //Obtenemos el id del producto elegido
                int idP = 0;
                try {
                    idP = Integer.parseInt(request.getParameter("idProducto"));
                } catch (NumberFormatException e) {
                    System.out.println("idP NULL " + e.getMessage());
                }
                //Creamos un objeto de Producto;
                Producto p;
                Producto p2;
                //Creamos un objeto de producto DAO
                ProductoJDBCDAO pDao = new ProductoJDBCDAO();
                //Creamos una sesion
                HttpSession session = request.getSession();
                //Empezamos comprobar el valor del parametro accion
                if (accion.equals("order") && idP != 0) { //order se envia del boton comprar en listarProducto
                    //Si la sesion cart es nulo, es decir es la primera vez que se hace clic en comprar
                    if (session.getAttribute("cart") == null) {
                        //Se crea una lista List
                        List<Item> cart = new ArrayList<>();
                        //Obtenemos el producto de acuerdo a la moneda y el ID
                        //p = pDao.findById2(session.getAttribute("moneda").toString(), idP); //EDIT
                        p = pDao.findById2("USD", idP);
                        //Se agrega a la lista
                        cart.add(new Item(p, 1));
                        //se agrega a la sesion
                        session.setAttribute("cart", cart);
                        //**********
                        request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
                    } else {//Se sigue agregando productos a la lista
                        //Mediante el atributo sesion se asigna esos datos a la lista
                        List<Item> cart = (ArrayList<Item>) session.getAttribute("cart");
                        //...
                        int indice = yaExisteProducto(idP, cart);
                        //quiere decir que este producto no existe en la lista 
                        if (indice == -1) {
                            //obtenemos los datos del producto
                            //p = pDao.findById2(session.getAttribute("moneda").toString(), idP); //EDIT
                            p = pDao.findById2("USD", idP);
                            //y lo asignamos a la lista
                            cart.add(new Item(p, 1));
                        } else {//ya existe el producto en la lista
                            //obtenemos la cantidad y lo sumamos + 1
                            int cantidad = cart.get(indice).getCantidad() + 1;
                            p2 = pDao.findById(idP);
                            if (cantidad <= p2.getStock()) {
                                //actualizamos la cantidad
                                cart.get(indice).setCantidad(cantidad);
                            }
                        }
                        session.setAttribute("cart", cart);
                        //********
                        request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
                    }
                } else if (accion.equals("delete")) {//si la accion es delete
                    List<Item> cart = (ArrayList<Item>) session.getAttribute("cart");
                    int indice = yaExisteProducto(idP, cart);
                    cart.remove(indice);
                    session.setAttribute("cart", cart);
                    //***********
                    request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);

                } else if (accion.equals("mas")) {
                    List<Item> cart = (ArrayList<Item>) session.getAttribute("cart");
                    int indice = yaExisteProducto(idP, cart);
                    int cantidad = cart.get(indice).getCantidad() + 1;
                    p2 = pDao.findById(idP);
                    if (cantidad <= p2.getStock()) {
                        //actualizamos la cantidad
                        cart.get(indice).setCantidad(cantidad);
                        session.setAttribute("cart", cart);
                    }
                    //***************
                    request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);

                } else if (accion.equals("menos")) {
                    List<Item> cart = (ArrayList<Item>) session.getAttribute("cart");
                    int indice = yaExisteProducto(idP, cart);
                    int cantidad = cart.get(indice).getCantidad() - 1;
                    if (cantidad > 0) {
                        //actualizamos la cantidad
                        cart.get(indice).setCantidad(cantidad);
                        session.setAttribute("cart", cart);
                    }
                    //*****************
                    request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
                } else if (accion.equals("comprar")) {

                    //mostrar formas de pago
                    vistaCompra(request, response);
                    //comprarProductoRegistro(request, response);

                } else if (accion.equals("finish")) {
                    System.out.println("PAGO FINALIZADO");
                    comprarProductoRegistro(request, response);
                    //se remueve el carrito
                    session.removeAttribute("cart");
                    //se dirige a la pagina finish
                    request.getRequestDispatcher("/WEB-INF/finish.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
            }
        } else {
            //Pedimos al usuario que inicie sesion o se registre
            System.out.println("REGISTRATE");

            //request.getRequestDispatcher("home").forward(request, response);
        }
        //request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "Finalizarcompra":
                    System.out.println("*******COMPRANDO********");
                    //comprarPayPal(request, response);
                    //comprarProductoRegistro(request, response);
                    break;
            }
        }
    }

    //Este metodo comprueba si existe un producto por medio del ID
    private int yaExisteProducto(int idP, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getP().getIdProducto() == idP) {
                return i;
            }
        }
        return -1;
    }

    private void comprarProductoRegistro(HttpServletRequest request, HttpServletResponse response) {

        HttpSession sesion1 = request.getSession();
        Login login = (Login) sesion1.getAttribute("login");

        Persona per = new Persona();
        per.setIdPersona(login.getIdPersona());

        Cliente cliente = new Cliente();
        cliente.setIdPersona(per);

        EnvioJDBCDAO envDao = new EnvioJDBCDAO();
        Envio env = envDao.validarDireccionEnvio(login.getIdPersona());

        HttpSession sesion2 = request.getSession();
        List<Item> cartList = (ArrayList<Item>) sesion2.getAttribute("cart");

        Venta venta = new Venta();
        venta.setItemDetalles(cartList);
        venta.setClienteidPersona(cliente);
        venta.setCostoEnvio(env.getCosto());

        VentaJDBCDAO veDao = new VentaJDBCDAO();
        System.out.println(veDao.insert(venta));

    }

    private void vistaCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion1 = request.getSession();
        Login login = (Login) sesion1.getAttribute("login");

        Persona per = new Persona();
        per.setIdPersona(login.getIdPersona());

        Cliente cliente = new Cliente();
        cliente.setIdPersona(per);

        EnvioJDBCDAO envDao = new EnvioJDBCDAO();
        Envio env = envDao.validarDireccionEnvio(login.getIdPersona());
        //System.out.println("LOGIN idPersona: "+login.getIdPersona());

        if (env != null) {
            //
            double sumaImporte = 0;
            double sumaImpuesto = 0;
            double sumaDescuento = 0;

            HttpSession sesion2 = request.getSession();
            List<Item> cartList = (ArrayList<Item>) sesion2.getAttribute("cart");

            for (Item dv : cartList) {
                sumaImporte += dv.getImporte();
                dv.getBase();
                sumaImpuesto += dv.getImpuesto();
                sumaDescuento += dv.getP().getPrecionuevo();
            }

            double total1 = sumaImporte - sumaDescuento + sumaImpuesto;
            double total = 0.0;
            double envio = 0.0;
            if (env.isEnvio()) {
                envio = 0.0;
                total = sumaImporte - sumaDescuento + sumaImpuesto + 0;
                System.out.println("vistaCompra - envio 0");
            } else {
                envio = env.getCosto();
                total = sumaImporte - sumaDescuento + sumaImpuesto + env.getCosto();
                System.out.println("vistaCompra - " + env.getCosto());
            }

            request.setAttribute("sumaImporte", sumaImporte);
            request.setAttribute("sumaImpuesto", sumaImpuesto);
            request.setAttribute("sumaDescuento", sumaDescuento);
            request.setAttribute("sumaEnvio", envio);
            request.setAttribute("total1", total1);
            request.setAttribute("total", total);
            request.setAttribute("envio", env);
            request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
        } else {
            System.out.println("Registre una direccion antes de continuar comprar");
            //
            formEditar(request, response);
        }
    }

    private void formEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Login login = (Login) sesion.getAttribute("login");
        int idPersona = login.getIdPersona();

        PersonaJDBCDAO perDao = new PersonaJDBCDAO();
        Persona p = perDao.findById(idPersona);

        ClienteJDBCDAO cliDao = new ClienteJDBCDAO();
        Cliente cliente = cliDao.findByIdPerfil(idPersona);

        DireccionJDBCDAO dirDao = new DireccionJDBCDAO();
        Direccion dir = dirDao.findById(idPersona);

        EstadoJDBCDAO estDao = new EstadoJDBCDAO();
        List<Estado> listEstados = estDao.listAll();
        request.setAttribute("estados", listEstados);

        PaisJDBCDAO paisDao = new PaisJDBCDAO();
        List<Pais> listarPaises = paisDao.listAll();
        request.setAttribute("paises", listarPaises);

        if (p != null) {
            request.setAttribute("persona", p);
        }

        if (cliente != null) {
            request.setAttribute("cliente", cliente);
        }

        if (dir != null) {
            request.setAttribute("direccion", dir);
        }

        request.setAttribute("tipoForm", "Actualizar");
        request.setAttribute("mensaje", "Ingrese una direccion Valida para continuar");
        request.getRequestDispatcher("/WEB-INF/login/formActualizar.jsp").forward(request, response);
    }

    private void enviarDetalleporCorreo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, AddressException, MessagingException {

        int idVenta = Integer.parseInt(request.getParameter("idVenta"));

        VentaJDBCDAO ventDao = new VentaJDBCDAO();
        Venta venta = ventDao.findById(idVenta);//

        if (venta != null) {

            DetalleJDBCDAO detDao = new DetalleJDBCDAO();
            List<DetalleVenta> listaDetVenta = detDao.listAllDetallesVentasClientes(idVenta);

            for (DetalleVenta det : listaDetVenta) {
                String line = det.getIdProducto().getIdProducto() + ";" + det.getIdProducto().getNombre() + ";" + det.getValorUnitario() + ";" + det.getCantidad() + ";" + det.getDescuento() + ";" + det.getImporte() + ";" + det.getImpuestos() + "; - ;";

            }
        }

        try {
            //*****************************************/
            Emails e = new Emails();
            e.enviar("indicedepublicaciones@gmail.com", "Hola! Soy un mensaje SERVLET!", "ejemplo de email enviado con Jakarta Mail");
            //*****************************************/
        } catch (MessagingException e) {

        }
    }

}
