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
import pe.com.opticaCoral.beans.Direccion;
import pe.com.opticaCoral.beans.Empleado;
import pe.com.opticaCoral.beans.Estado;
import pe.com.opticaCoral.beans.Pais;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.beans.TipoEmpleado;
import pe.com.opticaCoral.dao.DireccionJDBCDAO;
import pe.com.opticaCoral.dao.EmpleadoJDBCDAO;
import pe.com.opticaCoral.dao.EstadoJDBCDAO;
import pe.com.opticaCoral.dao.PaisJDBCDAO;
import pe.com.opticaCoral.dao.PersonaJDBCDAO;
import pe.com.opticaCoral.dao.TipoEmpleadoJDBCDAO;
import pe.com.opticaCoral.excepciones.ErrorRegistroException;

public class EmpleadoController extends HttpServlet {

    private String rutaCompletoImagen = null;
    private String rutaImagen = null;
    private InputStream imagenBy = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "editar":
                    formEditar(request, response);
                    break;
                case "nuevo":
                    formNuevo(request, response);
                    break;
                case "inicio":
                    request.getRequestDispatcher("/WEB-INF/empleados/inicio.jsp").forward(request, response);
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
            EmpleadoJDBCDAO empDao = new EmpleadoJDBCDAO();
            List<Empleado> listarEmpleados = empDao.listAll();

            request.setAttribute("empleados", listarEmpleados);
            request.getRequestDispatcher("/WEB-INF/empleados/index.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        recibirDatos(request);
        String accion = request.getAttribute("accion").toString();

        if (accion != null) {

            switch (accion) {
                case "nuevo":
                    nuevoEmpleado(request, response);

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

    private void nuevoEmpleado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Persona per = null;
        TipoEmpleado tp;
        EmpleadoJDBCDAO empDao = new EmpleadoJDBCDAO();

        String nombre = request.getAttribute("nombre").toString();
        String apellidop = request.getAttribute("apellidop").toString();
        String apellidom = request.getAttribute("apellidom").toString();
        String sexo = request.getAttribute("sexo").toString();
        String telefono = request.getAttribute("telefono").toString();
        String clave1 = request.getAttribute("clave1").toString();
        String clave2 = request.getAttribute("clave2").toString();
        String email = request.getAttribute("email").toString();
        int idTipoEmpleado = Integer.parseInt(request.getAttribute("idTipoEmpleado").toString());

        String foto = null;
        try {
            foto = request.getAttribute("foto").toString();

        } catch (Exception e) {
            System.out.println("imagen en null: " + e.getMessage());
        }

        if (clave1.equals(clave2)) {
            per = new Persona(nombre, apellidop, apellidom, sexo, telefono, email, foto, true, clave2);
            tp = new TipoEmpleado();
            tp.setIdTipoEmpleado(idTipoEmpleado);

            Empleado emp = new Empleado();
            emp.setIdPersona(per);
            emp.setIdTipoEmpleado(tp);

            boolean validaEmail = per.validarEmail();

            if (!validaEmail) {
                throw new ServletException(new ErrorRegistroException("Proporcione una dirección email válida"));
            }

            String mensaje = "";
            mensaje = empDao.insert(emp);

            if (foto != null) {
                guardaArchivo(rutaImagen, rutaCompletoImagen, imagenBy);
            }
        }

        List<Empleado> listarEmpleados = empDao.listAll();

        request.setAttribute("empleados", listarEmpleados);
        request.getRequestDispatcher("/WEB-INF/empleados/index.jsp").forward(request, response);
    }

    private void formNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TipoEmpleadoJDBCDAO tpDao = new TipoEmpleadoJDBCDAO();
        List<TipoEmpleado> listTipoEmpleado = tpDao.listAll();
        request.setAttribute("tipoempleado", listTipoEmpleado);

        request.setAttribute("tipoForm", "Actualizar");
        request.getRequestDispatcher("/WEB-INF/empleados/nuevo.jsp").forward(request, response);
    }

    private void formRegistrarDireccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idPersona = Integer.parseInt(request.getAttribute("idEmpleado").toString());

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

        dir = new Direccion(calle, numero, distrito, provincia, codigopostal, estado, per);

        String mensaje = null;
        //Si el cliente ha registrado una direccion
        if (dirDao.existe(idPersona)) {
            //se actualiza
            mensaje = dirDao.update(dir);

            HttpSession sesionE = request.getSession();
            sesionE.setAttribute("idEmpleado", per.getIdPersona());
            formEditar(request, response);
        } else {//Sino se inserta de nuevo
            mensaje = dirDao.insert(dir);

            HttpSession sesionE = request.getSession();
            sesionE.setAttribute("idEmpleado", per.getIdPersona());
            formEditar(request, response);
        }

    }

    private void ActualizarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idPersona = Integer.parseInt(request.getAttribute("idEmpleado").toString());

        Persona p;
        String clave1 = request.getAttribute("clave1").toString();
        String clave2 = request.getAttribute("clave2").toString();

        if (clave1.equals(clave2)) {
            //String passEncriptado = encriptarPassword(clave1);
            p = new Persona(idPersona, clave2);

            EmpleadoJDBCDAO empDao = new EmpleadoJDBCDAO();
            Empleado emp = new Empleado();
            emp.setIdPersona(p);

            empDao.updatePassword(emp);

            HttpSession sesionE = request.getSession();
            sesionE.setAttribute("idEmpleado", p.getIdPersona());
            formEditar(request, response);
        } else {
            throw new ServletException(
                    new ErrorRegistroException("Los password proporcionados no coinciden"));
        }

    }

    private void ActualizarDatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Persona per = null;
        TipoEmpleado tp;

        int idPersona = Integer.parseInt(request.getAttribute("idEmpleado").toString());

        String nombre = request.getAttribute("nombre").toString();
        String apellidop = request.getAttribute("apellidop").toString();
        String apellidom = request.getAttribute("apellidom").toString();
        String sexo = request.getAttribute("sexo").toString();
        String telefono = request.getAttribute("telefono").toString();
        int idTipoEmpleado = Integer.parseInt(request.getAttribute("idTipoEmpleado").toString());

        String foto = null;
        try {
            foto = request.getAttribute("foto").toString();

        } catch (Exception e) {
            System.out.println("imagen en null: " + e.getMessage());
        }
        per = new Persona(idPersona, nombre, apellidop, apellidom, sexo, telefono, foto);
        tp = new TipoEmpleado();
        tp.setIdTipoEmpleado(idTipoEmpleado);

        EmpleadoJDBCDAO empDao = new EmpleadoJDBCDAO();
        Empleado emp = new Empleado();
        emp.setIdPersona(per);
        emp.setIdTipoEmpleado(tp);

        String mensaje = "";
        mensaje = empDao.updateDatos(emp);
        if (foto != null) {
            guardaArchivo(rutaImagen, rutaCompletoImagen, imagenBy);
        }

        HttpSession sesionE = request.getSession();
        sesionE.setAttribute("idEmpleado", per.getIdPersona());
        formEditar(request, response);
    }

    private void formEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        int idPersona = 0;
        try {
            idPersona = Integer.parseInt(request.getParameter("idEmpleado"));
        } catch (NumberFormatException e) {
            idPersona = Integer.parseInt(sesion.getAttribute("idEmpleado").toString());
        }

        EmpleadoJDBCDAO empDao = new EmpleadoJDBCDAO();
        Empleado emp = empDao.findById(idPersona);

        DireccionJDBCDAO dirDao = new DireccionJDBCDAO();
        Direccion dir = dirDao.findById(idPersona);

        EstadoJDBCDAO estDao = new EstadoJDBCDAO();
        List<Estado> listEstados = estDao.listAll();
        request.setAttribute("estados", listEstados);

        TipoEmpleadoJDBCDAO tpDao = new TipoEmpleadoJDBCDAO();
        List<TipoEmpleado> listTipoEmpleado = tpDao.listAll();
        request.setAttribute("tipoempleado", listTipoEmpleado);

        PaisJDBCDAO paisDao = new PaisJDBCDAO();
        List<Pais> listarPaises = paisDao.listAll();
        request.setAttribute("paises", listarPaises);

        if (emp != null) {
            request.setAttribute("empleado", emp);
        }

        if (dir != null) {
            request.setAttribute("direccion", dir);
        }

        sesion.removeAttribute("idEmpleado");

        request.setAttribute("tipoForm", "Actualizar");
        request.getRequestDispatcher("/WEB-INF/empleados/form.jsp").forward(request, response);
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
                        rutaImagen = request.getServletContext().getRealPath("\\") + "imagenes\\empleados\\";
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

}
