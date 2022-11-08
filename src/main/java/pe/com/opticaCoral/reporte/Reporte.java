package pe.com.opticaCoral.reporte;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.com.opticaCoral.beans.Cliente;
import pe.com.opticaCoral.beans.DetalleVenta;
import pe.com.opticaCoral.beans.Envio;
import pe.com.opticaCoral.beans.Item;
import pe.com.opticaCoral.beans.Login;
import pe.com.opticaCoral.beans.Persona;
import pe.com.opticaCoral.beans.Venta;
import pe.com.opticaCoral.dao.DetalleJDBCDAO;
import pe.com.opticaCoral.dao.EnvioJDBCDAO;
import pe.com.opticaCoral.dao.VentaJDBCDAO;

@WebServlet(name = "Reporte", urlPatterns = {"/reporte"})
public class Reporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        // Get the text that will be added to the PDF
        String text = request.getParameter("text");
        if (text == null || text.trim().length() == 0) {
            text = "You didn't enter any text.";
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph(String.format(
                "You have submitted the following text using the %s method:",
                request.getMethod())));
        doc.add(new Paragraph(text));
        doc.close();

        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "imprimirPDF":
                    imprimirDetalleVentaCliente(request, response);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void vistaCompraPDF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

            //****************************************
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
            Document document = new Document(pdfDoc);

            //Agregar encabezado y pie de pagina
            EventoPagina evento = new EventoPagina(document, "Reportes 2021", "", "","");
            //Indicamos que el manejador se encargara del evento END_PAGE
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            String columnas[] = {"ID", "Nombre", "Precio", "IGV", "Cantidad", "Descuento", "Importe", "Impuesto", "Suma"};

            document.setMargins(75, 36, 75, 36);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

            Table table = new Table(new float[]{1, 4, 4, 4, 2, 2, 2, 2, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            for (String columna : columnas) {
                process(table, columna, bold, true);
            }

            double sumaImporte = 0;
            double sumaImpuesto = 0;
            double sumaDescuento = 0;

            HttpSession sesion2 = request.getSession();
            List<Item> cartList = (ArrayList<Item>) sesion2.getAttribute("cart");

            for (Item dv : cartList) {

                String line = dv.getP().getIdProducto() + ";" + dv.getP().getNombre() + ";" + dv.getP().getPrecio() + ";" + dv.getP().getIgv() + ";" + dv.getCantidad() + ";" + dv.getP().getPrecionuevo() + ";" + dv.getImporte() + ";" + dv.getImpuesto() + "; -;";

                process(table, line, font, false);

                sumaImporte += dv.getImporte();
                dv.getBase();
                sumaImpuesto += dv.getImpuesto();
                sumaDescuento += dv.getP().getPrecionuevo();
            }

            double total1 = sumaImporte - sumaDescuento + sumaImpuesto;
            double total = 0;
            double envio = 0;
            if (env.isEnvio()) {
                envio = 0;
                total = sumaImporte - sumaDescuento + sumaImpuesto + 0;
            } else {
                envio = env.getCosto();
                total = sumaImporte - sumaDescuento + sumaImpuesto + env.getCosto();
            }

            Cell cell3 = new Cell(1, 5)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(""));
            table.addCell(cell3);//
            table.addCell(String.valueOf(sumaDescuento));//
            table.addCell(String.valueOf(sumaImporte)); //
            table.addCell(String.valueOf(sumaImpuesto)); //
            table.addCell(String.valueOf(total1)); //

            Cell cell = new Cell(1, 7)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(""));
            table.addCell(cell);//
            table.addCell("Envio (" + env.getIdPais().getNombre() + ")");//
            table.addCell(String.valueOf(envio)); //

            Cell cell2 = new Cell(1, 7)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(""));
            table.addCell(cell2);//
            table.addCell("Total a Pagar");//
            table.addCell(String.valueOf(total)); //
            document.add(table);
            document.close();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();

            //****************************************
        }
    }

    private void imprimirDetalleVentaCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idVenta = Integer.parseInt(request.getParameter("idVenta"));

        VentaJDBCDAO ventDao = new VentaJDBCDAO();
        Venta venta = ventDao.findById(idVenta);//

        if (venta != null) {
            //****************************************
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
            Document document = new Document(pdfDoc);

            //Agregar encabezado y pie de pagina
            EventoPagina evento = new EventoPagina(document, venta.getClienteidPersona().getIdPersona().getNombresCompletos(), "ID:"+String.valueOf(venta.getIdVenta()), String.valueOf(venta.getFecha()), "Fecha de Compra:");
            //Indicamos que el manejador se encargara del evento END_PAGE
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            String columnas[] = {"ID", "Nombre", "Precio", "Cantidad", "Descuento", "Importe", "Impuesto", "Suma"};

            document.setMargins(75, 36, 75, 36);
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

            Table table = new Table(new float[]{1, 4, 4, 4, 2, 2, 2, 2});
            table.setWidth(UnitValue.createPercentValue(100));

            for (String columna : columnas) {
                process(table, columna, bold, true);
            }

            DetalleJDBCDAO detDao = new DetalleJDBCDAO();
            List<DetalleVenta> listaDetVenta = detDao.listAllDetallesVentasClientes(idVenta);

            for (DetalleVenta det : listaDetVenta) {
                String line = det.getIdProducto().getIdProducto() + ";" + det.getIdProducto().getNombre() + ";" + det.getValorUnitario() + ";" + det.getCantidad() + ";" + det.getDescuento() + ";" + det.getImporte() + ";" + det.getImpuestos() + "; - ;";
                process(table, line, font, false);
            }

            Cell cell3 = new Cell(1, 4)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(""));
            table.addCell(cell3);//
            table.addCell(String.valueOf(venta.getTotalDescuento()));//
            table.addCell(String.valueOf(venta.getTotalImporte())); //
            table.addCell(String.valueOf(venta.getIgv())); //
            table.addCell(String.valueOf(venta.getTotalImporte() - venta.getTotalDescuento() + venta.getIgv())); //

            Cell cell = new Cell(1, 6)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(""));
            table.addCell(cell);//
            table.addCell("Envio");//
            table.addCell(String.valueOf(venta.getCostoEnvio())); //

            Cell cell2 = new Cell(1, 6)
                    .setTextAlignment(TextAlignment.CENTER)
                    .add(new Paragraph(""));
            table.addCell(cell2);//
            table.addCell("Total a Pagar");//
            table.addCell(String.valueOf(venta.getTotalNeto())); //
            document.add(table);
            document.close();

            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();

            //****************************************
        }
    }

    private void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }

}
