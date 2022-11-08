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
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.Direccion;
import pe.com.opticaCoral.beans.Estado;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.beans.Pais;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.dao.ClienteJDBCDAO;
import pe.com.opticaCoral.dao.DireccionJDBCDAO;
import pe.com.opticaCoral.dao.EstadoJDBCDAO;
import pe.com.opticaCoral.dao.LoginJDBCDAO;
import pe.com.opticaCoral.dao.PaisJDBCDAO;
import pe.com.opticaCoral.dao.PersonaJDBCDAO;
import pe.com.opticaCoral.excepciones.ErrorRegistroException;
import pe.com.opticaCoral.interfaces.ILogin;

public class PersonaController extends HttpServlet {

    private String rutaCompletoImagen = null;
    private String rutaImagen = null;
    private InputStream imagenBy = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {

            String accion = (String) request.getParameter("accion");

            switch (accion) {

                case "editar":
                    formEditar(request, response);
                    break;
                case "estado":
                    //System.out.println("idPais: " + request.getParameter("idPais"));
                    int idPais = Integer.parseInt(request.getParameter("idPais"));

                    EstadoJDBCDAO estDao = new EstadoJDBCDAO();
                    List<Estado> listEstados = estDao.listAll(idPais);

                    request.setAttribute("estados", listEstados);
                    request.setAttribute("idPais", idPais);
                    request.getRequestDispatcher("/WEB-INF/login/estados.jsp").forward(request, response);
                    break;
            }
        } else {
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        recibirDatos(request);
        String accion = request.getAttribute("accion").toString();

        if (accion != null) {

            switch (accion) {
                case "crearCliente":
                    formPersonaRegistrar(request, response);

                    break;
                case "ActualizarDireccion":
                    formRegistrarDireccion(request, response);
                    break;
                case "ActualizarPassword":
                    ActualizarPassword(request, response);
                    break;
                case "ActualizarDatos":
                    ActualizarDatos(request, response);
                    break;
            }
        } else {
            System.out.println("accion es NULL...");
        }
    }

    private void ActualizarDatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        Login login = (Login) sesion.getAttribute("login");
        int idPersona = login.getIdPersona();

        Persona p = new Persona();
        Cliente c = new Cliente();

        p.setIdPersona(idPersona);

        String nombre = request.getAttribute("nombre").toString();
        p.setNombres(nombre);
        String apellidop = request.getAttribute("apellidop").toString();
        p.setApellidop(apellidop);
        String apellidom = request.getAttribute("apellidom").toString();
        p.setApellidom(apellidom);
        String sexo = request.getAttribute("sexo").toString();
        p.setSexo(sexo);
        String telefono = request.getAttribute("telefono").toString();
        p.setTelefono(telefono);
        String foto = null;
        try {
            foto = request.getAttribute("foto").toString();

        } catch (Exception e) {
            System.out.println("imagen en null: " + e.getMessage());
        }
        p.setFoto(foto);

        String perfil = request.getAttribute("perfil").toString();
        c.setPerfil(perfil);

        p.getCliente().add(c);

        PersonaJDBCDAO perDao = new PersonaJDBCDAO();
        String mensaje = "";
        if (foto != null) {
            mensaje = perDao.updateDatos(p);
            guardaArchivo(rutaImagen, rutaCompletoImagen, imagenBy);
        } else {
            mensaje = perDao.updateDatos2(p);
        }

        formEditar(request, response);

    }

    private void ActualizarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Login login = (Login) sesion.getAttribute("login");
        int idPersona = login.getIdPersona();

        Persona p;
        String clave1 = request.getAttribute("clave1").toString();
        String clave2 = request.getAttribute("clave2").toString();

        if (clave1.equals(clave2)) {
            //String passEncriptado = encriptarPassword(clave1);
            p = new Persona(idPersona, clave2);

            PersonaJDBCDAO pDao = new PersonaJDBCDAO();
            pDao.updateClave(p);

            formEditar(request, response);
        } else {
            throw new ServletException(
                    new ErrorRegistroException("Los password proporcionados no coinciden"));
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

        PaisJDBCDAO paisDao = new PaisJDBCDAO();
        List<Pais> listarPaises = paisDao.listAll();
        request.setAttribute("paises", listarPaises);

        EstadoJDBCDAO estDao = new EstadoJDBCDAO();
        List<Estado> listEstados = estDao.listAll();
        request.setAttribute("estados", listEstados);

        if (p != null) {
            request.setAttribute("persona", p);
        }

        if (cliente != null) {
            request.setAttribute("cliente", cliente);
        }

        if (dir != null) {
            request.setAttribute("direccion", dir);
        }

        ILogin loginDao = new LoginJDBCDAO();
        Login loginSesion = loginDao.listAll(idPersona);

        sesion.setAttribute("login", loginSesion);

        request.setAttribute("tipoForm", "Actualizar");
        request.getRequestDispatcher("/WEB-INF/login/formActualizar.jsp").forward(request, response);
    }

    private void formRegistrarDireccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Login login = (Login) sesion.getAttribute("login");
        int idPersona = login.getIdPersona();

        DireccionJDBCDAO dirDao = new DireccionJDBCDAO();
        Direccion dir = null;

        String calle = request.getAttribute("calle").toString();
        int numero = Integer.parseInt(request.getAttribute("numero").toString());
        String distrito = request.getAttribute("distrito").toString();
        String provincia = request.getAttribute("provincia").toString();
        int codigopostal = Integer.parseInt(request.getAttribute("codigopostal").toString());
        int idEstado = Integer.parseInt(request.getAttribute("idEstado").toString());

        EstadoJDBCDAO estDao = new EstadoJDBCDAO();
        Estado estado = estDao.findById(idEstado);

        PersonaJDBCDAO perDao = new PersonaJDBCDAO();
        Persona per = perDao.findById(idPersona);

        dirDao = new DireccionJDBCDAO();
        dir = new Direccion(calle, numero, distrito, provincia, codigopostal, estado, per);

        //Si el cliente ha registrado una direccion
        if (dirDao.existe(idPersona)) {
            //se actualiza
            String mensaje = dirDao.update(dir);
            formEditar(request, response);
        } else {//Sino se inserta de nuevo
            String mensaje = dirDao.insert(dir);
            formEditar(request, response);
        }

    }

    private void formPersonaRegistrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //String accion = request.getAttribute("accion").toString();
        Persona p = null;
        Cliente c = new Cliente();

        String nombre = request.getAttribute("nombre").toString();
        String apellidop = request.getAttribute("apellidop").toString();
        String apellidom = request.getAttribute("apellidom").toString();
        String sexo = request.getAttribute("sexo").toString();
        String telefono = request.getAttribute("telefono").toString();
        String email = request.getAttribute("email").toString();
        String foto = null;
        try {
            foto = request.getAttribute("foto").toString();

        } catch (Exception e) {
            System.out.println("imagen en null: " + e.getMessage());
        }
        String clave1 = request.getAttribute("clave1").toString();
        String clave2 = request.getAttribute("clave2").toString();
        String perfil = request.getAttribute("perfil").toString();

        if (clave1.equals(clave2)) {

            //String passEncriptado = encriptarPassword(clave1);
            p = new Persona(nombre, apellidop, apellidom, sexo, telefono, email, foto, true, clave2);
            c.setPerfil(perfil);

            boolean validaEmail = p.validarEmail();

            if (!validaEmail) {
                throw new ServletException(new ErrorRegistroException("Proporcione una dirección email válida"));
            }

            p.getCliente().add(c);

            PersonaJDBCDAO pDao = new PersonaJDBCDAO();
            pDao.insertCliente(p);

            if (p.getFoto() != null) {//Si imagen es diferente a null se guarda la imagen
                guardaArchivo(rutaImagen, rutaCompletoImagen, imagenBy);
            }
            response.sendRedirect("home");
        } else {
            throw new ServletException(
                    new ErrorRegistroException("Los password proporcionados no coinciden"));
        }

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

    private void recibirDatos(HttpServletRequest request) {
        try {
            FileItemFactory fileFactory = new DiskFileItemFactory();
            ServletFileUpload servletUpload = new ServletFileUpload(fileFactory);

            String nombreImagen = "";

            List items = servletUpload.parseRequest(request);
            for (int i = 0; i < items.size(); i++) {
                FileItem item = (FileItem) items.get(i);
                if (!item.isFormField()) {
                    if (item.getContentType().contains("image")) {
                        //Ruta para la imagen del producto
                        rutaImagen = request.getServletContext().getRealPath("\\") + "imagenes\\clientes\\";
                        nombreImagen = nombreArchivo() + item.getName();
                        rutaCompletoImagen = rutaImagen + nombreImagen;
                        imagenBy = item.getInputStream();
                        request.setAttribute(item.getFieldName(), nombreImagen);
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

    /*
    private void insertarCliente(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        //nombres, apellidoP, apellidoM, sexo, telefono, email, foto, clave, perfil
        String nombres = request.getParameter("nombre");
        String apellidop = request.getParameter("apellidop");
        String apellidom = request.getParameter("apellidom");
        String sexo = request.getParameter("sexo");
        String telefono = request.getParameter("telefono").trim();
        String email = request.getParameter("email");
        String foto = request.getParameter("foto");
        String clave1 = request.getParameter("clave1");
        String clave2 = request.getParameter("clave2");
        String perfil = request.getParameter("perfil");

        System.out.println("SEXO: " + sexo);

        if (clave1.equals(clave2)) {
            IPersona perDao = new PersonaJDBCDAO();
            Persona p = new Persona();
            Cliente c = new Cliente();

            String passEncriptado = encriptarPassword(clave1);
            p.setNombres(nombres);
            p.setApellidop(apellidop);
            p.setApellidom(apellidom);
            p.setSexo(sexo);
            p.setTelefono(telefono);
            p.setEmail(email);
            p.setFoto(foto);
            p.setClave(passEncriptado);
            c.setPerfil(perfil);

            boolean validaEmail = p.validarEmail();

            if (!validaEmail) {
                throw new ServletException(new ErrorRegistroException("Proporcione una dirección email válida"));
            }

            perDao.insertCliente(p, c);
            response.sendRedirect("home");

        } else {
            throw new ServletException(
                    new ErrorRegistroException("Los password proporcionados no coinciden"));
        }

    }
     */
 /*
    private String encriptarPassword(String pass) {
        return DigestUtils.md5Hex(pass);
    }*/
}
