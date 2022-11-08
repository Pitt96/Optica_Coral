package pe.com.opticaCoral.controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import pe.com.opticaCoral.beans.Categoria;
import pe.com.opticaCoral.beans.Empleado;
import pe.com.opticaCoral.beans.Marca;
import pe.com.opticaCoral.beans.Producto;
import pe.com.opticaCoral.beans.ProductoMoneda;
import pe.com.opticaCoral.beans.Proveedor;
import pe.com.opticaCoral.dao.CategoriaJDBCDAO;
import pe.com.opticaCoral.dao.EmpleadoJDBCDAO;
import pe.com.opticaCoral.dao.MarcaJDBCDAO;
import pe.com.opticaCoral.dao.ProductoJDBCDAO;
import pe.com.opticaCoral.dao.ProductoMonedaJDBCDAO;
import pe.com.opticaCoral.dao.ProveedorJDBCDAO;
import pe.com.opticaCoral.interfaces.IProducto;

public class ProductoController extends HttpServlet {

    private String rutaCompletoImagen = null;
    private String rutaCompletoVideo = null;
    private String rutaImagen = null;
    private String rutaVideo = null;
    private InputStream imagenBy = null;
    private InputStream videoBy = null;

    private Producto p;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        IProducto daoProducto = new ProductoJDBCDAO();
        if (request.getParameter("accion") != null) {

            String accion = (String) request.getParameter("accion");
            switch (accion) {
                case "nuevo":
                    formNuevo(request, response);
                    break;
                case "editar":
                    formEditar(request, response);
                    break;
                case "editarDivisas":
                    formEditarDivisas(request, response);
                    break;
                case "buscar":
                    //System.out.println(request.getParameter("accion"));
                    break;
                case "productoDetalles":
                    int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                    Producto p = daoProducto.findById(idProducto);

                    request.setAttribute("producto", p);
                    request.getRequestDispatcher("/WEB-INF/productos/descripcion.jsp").forward(request, response);
                    break;
            }

        } else {
            List<Producto> listarProductos = daoProducto.listAll();

            request.setAttribute("productos", listarProductos);
            request.getRequestDispatcher("/WEB-INF/productos/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Recibe los datos del formulario, se cambia por cada solicitud que llega al post
        recibirDatosProductos(request);

        //accion es el parametro que puede venir de cualquier formulario
        request.setAttribute("accion", request.getAttribute("accion").toString());

        //Comprobamos en el if que accion no sea nulo
        if (request.getAttribute("accion").toString() != null) {
            //Obtenemos el valor de accion
            String accion = request.getAttribute("accion").toString();

            //Si accion tiene un valor lo recuperamos aqui con el switch
            switch (accion) {
                case "Crear":
                    //Aqui guardamos la informacion del primer formulario, de lo contrario se perderia
                    formProducto(request);
                    //Vamos a la pagina precios para traer más información
                    request.setAttribute("tipoForm", "CrearconPrecio");
                    request.getRequestDispatcher("/WEB-INF/productos/precios.jsp").forward(request, response);
                    break;
                case "CrearconPrecio":
                    //Aquí se obtiene la información del formularios precios y se debe guardar en la BD * infor completo + anterior
                    insertarProducto(request, response);

                    if (p.getImagen() != null) {//Si imagen es diferente a null se guarda la imagen
                        guardaArchivo(rutaImagen, rutaCompletoImagen, imagenBy);
                    }
                    if (p.getVideo() != null) {//Si video es diferente a null se guarda el video
                        guardaArchivo(rutaVideo, rutaCompletoVideo, videoBy);
                    }
                    p = null;

                    break;
                case "borrar":
                    //borrarProducto(request, response);
                    break;
                case "Actualizar":
                    actualizarProducto(request, response);
                    if (p.getImagen() != null) {//Si imagen es diferente a null se guarda la imagen
                        guardaArchivo(rutaImagen, rutaCompletoImagen, imagenBy);
                    }
                    if (p.getVideo() != null) {//Si video es diferente a null se guarda el video
                        guardaArchivo(rutaVideo, rutaCompletoVideo, videoBy);
                    }
                    p = null;
                    break;
                case "stockUpdateProducto":
                    actualizarStock(request, response);
                    break;
                case "divisaUdateProducto":
                    actualizarDivisa(request, response);
                    break;
                default:
                    System.out.println("Ninguna accion corresponde");
            }
        } else {
            System.out.println("accion es NULL");
        }
    }

    //Cuando vamos a registrar un nuevo producto este metodo recibe la información
    //lo guarda en el objeto Producto
    private void formProducto(HttpServletRequest request) {
        int idProducto = 0;
        try {
            idProducto = Integer.parseInt(request.getAttribute("idProducto").toString());
        } catch (NumberFormatException e) {
            System.out.println("null idProducto: " + e.getMessage());
        }
        String nombre = request.getAttribute("nombre").toString();
        String descripcion = request.getAttribute("descripcion").toString();
        String imagen = null;
        String video = null;
        try {
            imagen = request.getAttribute("imagen").toString();

        } catch (Exception e) {
            System.out.println("imagen en null: " + e.getMessage());
        }
        try {
            video = request.getAttribute("video").toString();
        } catch (Exception e) {
            System.out.println("video en null: " + e.getMessage());
        }
        int stock = 0;
        try {
            stock = Integer.parseInt(request.getAttribute("stock").toString());
        } catch (Exception e) {
            System.out.println("stock: " + e.getMessage());
        }
        double precio = Double.parseDouble(request.getAttribute("precio").toString());
        double precion = Double.parseDouble(request.getAttribute("precion").toString());
        double igv = Double.parseDouble(request.getAttribute("igv").toString());
        boolean recomendado;
        boolean visible;
        try {
            recomendado = request.getAttribute("recomendado").toString().equalsIgnoreCase("on");
        } catch (Exception e) {
            recomendado = false;
            //System.out.println("boolean " + e.getMessage());
        }
        try {
            visible = request.getAttribute("visible").toString().equalsIgnoreCase("on");
        } catch (Exception e) {
            visible = false;
            //System.out.println("boolean " + e.getMessage());
        }
        int idCategoria = Integer.parseInt(request.getAttribute("idCategoria").toString());
        int idMarca = Integer.parseInt(request.getAttribute("idMarca").toString());
        int idProveedor = Integer.parseInt(request.getAttribute("idProveedor").toString());

        HttpSession sesion = request.getSession();
        Empleado login = (Empleado) sesion.getAttribute("loginE");

        int idEmpleado = login.getIdPersona().getIdPersona();

        //int idEmpleado = Integer.parseInt(request.getAttribute("idEmpleado").toString());
        Categoria cat = new CategoriaJDBCDAO().findById(idCategoria);
        Marca mar = new MarcaJDBCDAO().findById(idMarca);
        Proveedor prov = new ProveedorJDBCDAO().findById(idProveedor);
        Empleado emp = new EmpleadoJDBCDAO().findById(idEmpleado);
        p = new Producto(idProducto, nombre, precio, precion, descripcion, imagen, video, recomendado, stock, visible, cat, mar, prov, emp, igv);
    }

    //Este método es GENERAL recibe la información del formulario, incluye archivos
    private void recibirDatosProductos(HttpServletRequest request) {
        try {
            FileItemFactory fileFactory = new DiskFileItemFactory();
            ServletFileUpload servletUpload = new ServletFileUpload(fileFactory);

            String nombreImagen = "";
            String nombreVideo = "";
            List items = servletUpload.parseRequest(request);
            for (int i = 0; i < items.size(); i++) {
                FileItem item = (FileItem) items.get(i);
                if (!item.isFormField()) {
                    if (item.getContentType().contains("image")) {
                        //Ruta para la imagen del producto
                        rutaImagen = request.getServletContext().getRealPath("\\") + "imagenes\\productos\\imagen\\";
                        nombreImagen = nombreArchivo() + item.getName();
                        rutaCompletoImagen = rutaImagen + nombreImagen;
                        imagenBy = item.getInputStream();
                        request.setAttribute(item.getFieldName(), nombreImagen);
                        System.out.println("Ruta Imagen: "+rutaImagen);
                    }
                    if (item.getContentType().contains("video")) {
                        //Ruta para el video del producto
                        rutaVideo = request.getServletContext().getRealPath("\\") + "imagenes\\productos\\video\\";
                        nombreVideo = nombreArchivo() + item.getName();
                        rutaCompletoVideo = rutaVideo + nombreVideo;
                        videoBy = item.getInputStream();
                        request.setAttribute(item.getFieldName(), nombreVideo);
                    }
                } else {
                    request.setAttribute(item.getFieldName(), item.getString());
                }
            }
        } catch (FileUploadException | IOException ex) {
            System.out.println("Error en subida Producto: " + ex.getMessage());
            request.setAttribute("Error en subida Producto", false);
        }
    }

    //Genera un nombre a partir de la fecha y un numero aleatorio
    private String nombreArchivo() {
        String nombre;
        //Se obtiene la fecha; dia, mes, año, hora, minuto, segundo
        SimpleDateFormat sdf = new SimpleDateFormat("ddMyyyyhmmss");
        //Se convierte a String
        String fecha = sdf.format(new Date());
        //Se crea un nombreImagen unico con estos valores
        return nombre = fecha + new Random().nextLong();
    }

    //Guarda el archivo en el disco duro
    private void guardaArchivo(String rutaG, String rutaC, InputStream input) {
        try {
            //para la carpeta si no existe crea la carpeta
            File folder = new File(rutaG);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File targetFile = new File(rutaC);
            FileUtils.copyInputStreamToFile(input, targetFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Cuando vamos agregar un nuevo producto
    //se le envia información para el SELECT option
    private void formNuevo(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        List<Categoria> cat = new CategoriaJDBCDAO().listAll();
        request.setAttribute("categorias", cat);

        List<Marca> mar = new MarcaJDBCDAO().listAll();
        request.setAttribute("marcas", mar);

        List<Proveedor> prov = new ProveedorJDBCDAO().listAll();
        request.setAttribute("proveedores", prov);

        request.setAttribute("tipoForm", "Crear");
        request.getRequestDispatcher("/WEB-INF/productos/form.jsp").forward(request, response);
    }

    //Obtiene la informacion para guardar en la base de datos.
    private void insertarProducto(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        recibirDatosProductos(request);

        //Creamos las monedas
        ProductoMoneda pm1;
        double preciousd = Double.parseDouble(request.getAttribute("preciousd").toString());
        double precionusd = Double.parseDouble(request.getAttribute("precionusd").toString());
        pm1 = new ProductoMoneda("USD", preciousd, precionusd);
        p.getProductoMoneda().add(pm1);

        ProductoMoneda pm2;
        double precioeur = Double.parseDouble(request.getAttribute("precioeur").toString());
        double precioneur = Double.parseDouble(request.getAttribute("precioneur").toString());
        pm2 = new ProductoMoneda("EUR", precioeur, precioneur);
        p.getProductoMoneda().add(pm2);

        ProductoMoneda pm3;
        double preciocny = Double.parseDouble(request.getAttribute("preciocny").toString());
        double precioncny = Double.parseDouble(request.getAttribute("precioncny").toString());
        pm3 = new ProductoMoneda("CNY", preciocny, precioncny);
        p.getProductoMoneda().add(pm3);

        //Registramos
        ProductoJDBCDAO pDao = new ProductoJDBCDAO();
        String mensaje = pDao.insert(p);

        request.getSession().setAttribute("opArt", mensaje);
        response.sendRedirect("productos");

    }

    //Actualiza información de producto en la base de datos
    private void actualizarProducto(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        recibirDatosProductos(request);
        formProducto(request);

        ProductoJDBCDAO pDao = new ProductoJDBCDAO();
        String mensaje = pDao.update(p);

        request.getSession().setAttribute("opArt", mensaje);
        response.sendRedirect("productos");

    }

    private void formStockProducto(HttpServletRequest request) {
        int idProducto = 0;
        try {
            idProducto = Integer.parseInt(request.getAttribute("idProducto").toString());
        } catch (NumberFormatException e) {
            System.out.println("null idProducto stock: " + e.getMessage());
        }
        int stockNuevo = 0;
        try {
            stockNuevo = Integer.parseInt(request.getAttribute("stockNuevo").toString());
        } catch (NumberFormatException e) {
            System.out.println("null stockNuevo: " + e.getMessage());
        }
        p = new Producto(idProducto, stockNuevo);
    }

    //Actualiza el stock del producto en la base de datos
    private void actualizarStock(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        recibirDatosProductos(request);
        formStockProducto(request);

        ProductoJDBCDAO pDao = new ProductoJDBCDAO();
        String mensaje = pDao.updateStock(p);

        request.getSession().setAttribute("opArt", mensaje);
        response.sendRedirect("productos");
    }

    //Envia la información al formulario para Editar
    private void formEditar(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        ProductoJDBCDAO daoProducto = new ProductoJDBCDAO();
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        Producto prod = daoProducto.findById(idProducto);

        if (prod != null) {
            List<Categoria> cat = new CategoriaJDBCDAO().listAll();
            request.setAttribute("categorias", cat);

            List<Marca> mar = new MarcaJDBCDAO().listAll();
            request.setAttribute("marcas", mar);

            List<Proveedor> prov = new ProveedorJDBCDAO().listAll();
            request.setAttribute("proveedores", prov);

            request.setAttribute("productos", prod);

            request.setAttribute("tipoForm", "Actualizar");
            request.getRequestDispatcher("/WEB-INF/productos/form.jsp").forward(request, response);
        }
    }

    private void borrarProducto(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        /*
        long idArticulo = Long.parseLong(request.getParameter("idArticulo"));

        ArticuloJDBCDAO daoArticulo = new ArticuloJDBCDAO();
        String mensaje = daoArticulo.delete(new Articulo(idArticulo));

        request.getSession().setAttribute("opArt", mensaje);

        response.sendRedirect("/programaregalos/articulos");
         */
    }

    private void formEditarDivisas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductoMonedaJDBCDAO pmDao = new ProductoMonedaJDBCDAO();

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        List<ProductoMoneda> listaProductoMoneda = pmDao.findById(idProducto);

        request.setAttribute("monedas", listaProductoMoneda);

        request.setAttribute("tipoForm", "Actualizar");
        request.getRequestDispatcher("/WEB-INF/productos/precios.jsp").forward(request, response);
    }

    private void actualizarDivisa(HttpServletRequest request, HttpServletResponse response) throws IOException {
        recibirDatosProductos(request);
        int idProductoMonedaUSD = 0;
        try {
            idProductoMonedaUSD = Integer.parseInt(request.getAttribute("idProductoMoneda1").toString());
        } catch (NumberFormatException e) {
            System.out.println("null idProductoMonedaUSD: " + e.getMessage());
        }
        int idProductoMonedaEUR = 0;
        try {
            idProductoMonedaEUR = Integer.parseInt(request.getAttribute("idProductoMoneda2").toString());
        } catch (NumberFormatException e) {
            System.out.println("null idProductoMonedaEUR: " + e.getMessage());
        }
        int idProductoMonedaCNY = 0;
        try {
            idProductoMonedaCNY = Integer.parseInt(request.getAttribute("idProductoMoneda3").toString());
        } catch (NumberFormatException e) {
            System.out.println("null idProductoMonedaCNY: " + e.getMessage());
        }
        double precioUSD = Double.parseDouble(request.getAttribute("precioUSD").toString());
        double precionuevoUSD = Double.parseDouble(request.getAttribute("precionuevoUSD").toString());

        double precioEUR = Double.parseDouble(request.getAttribute("precioEUR").toString());
        double precionuevoEUR = Double.parseDouble(request.getAttribute("precionuevoEUR").toString());

        double precioCNY = Double.parseDouble(request.getAttribute("precioCNY").toString());
        double precionuevoCNY = Double.parseDouble(request.getAttribute("precionuevoCNY").toString());

        ProductoMoneda pm1 = new ProductoMoneda(idProductoMonedaUSD, precioUSD, precionuevoUSD);
        ProductoMoneda pm2 = new ProductoMoneda(idProductoMonedaEUR, precioEUR, precionuevoEUR);
        ProductoMoneda pm3 = new ProductoMoneda(idProductoMonedaCNY, precioCNY, precionuevoCNY);

        ProductoMonedaJDBCDAO pm = new ProductoMonedaJDBCDAO();
        String mensaje = null;
        mensaje = pm.update(pm1);
        mensaje = pm.update(pm2);
        mensaje = pm.update(pm3);

        request.getSession().setAttribute("opArt", mensaje);
        response.sendRedirect("productos");
    }

}
