package pe.com.opticaCoral.reporte;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class EventoPagina implements IEventHandler {

    private final Document documento;
    private final String nombre;
    private final String text;
    private final String fecha;
    private final String fef;
    //public static final String IMG = "C:\\Users\\TDAVI\\Pictures\\q.png";

    public EventoPagina(Document doc, String n, String t, String fecha, String fef) {
        documento = doc;
        this.nombre = n;
        this.text = t;
        this.fecha = fecha;
        this.fef = fef;
    }

    /**
     * Crea el rectangulo donde pondremos el encabezado
     *
     * @param docEvent Evento de documento
     * @return Area donde colocaremos el encabezado
     */
    private Rectangle crearRectanguloEncabezado(PdfDocumentEvent docEvent) {
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();

        float xEncabezado = pdfDoc.getDefaultPageSize().getX() + documento.getLeftMargin();
        float yEncabezado = pdfDoc.getDefaultPageSize().getTop() - documento.getTopMargin();
        float anchoEncabezado = page.getPageSize().getWidth() - 72;
        float altoEncabezado = 50F;

        Rectangle rectanguloEncabezado = new Rectangle(xEncabezado, yEncabezado, anchoEncabezado, altoEncabezado);

        return rectanguloEncabezado;
    }

    /**
     * Crea el rectangulo donde pondremos el pie de pagina
     *
     * @param docEvent Evento del documento
     * @return Area donde colocaremos el pie de pagina
     */
    private Rectangle crearRectanguloPie(PdfDocumentEvent docEvent) {
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();

        float xPie = pdfDoc.getDefaultPageSize().getX() + documento.getLeftMargin();
        float yPie = pdfDoc.getDefaultPageSize().getBottom();
        float anchoPie = page.getPageSize().getWidth() - 72;
        float altoPie = 50F;

        Rectangle rectanguloPie = new Rectangle(xPie, yPie, anchoPie, altoPie);

        return rectanguloPie;
    }

    /**
     * Crea la tabla que contendra el mensaje del encabezado
     *
     * @param mensaje Mensaje que desplegaremos
     * @return Tabla con el mensaje de encabezado
     */
    private Table crearTablaEncabezado(String mensaje, String text) throws IOException {
        Table tablaEncabezado = new Table(UnitValue.createPercentArray(new float[]{10, 90}));
        tablaEncabezado.setWidth(527F);
        Cell cell1, cell2;

        //Image img = new Image(ImageDataFactory.create(text));
        cell2 = new Cell();
        //cell2.add(img.setAutoScale(true));
        cell2.add(new Paragraph(text));
        cell2.setBorder(Border.NO_BORDER);
        tablaEncabezado.addCell(cell2);

        cell1 = new Cell();
        cell1.add(new Paragraph(mensaje));
        cell1.setBorder(Border.NO_BORDER);
        tablaEncabezado.addCell(cell1);

        Color fondo = new DeviceCmyk(0, 20, 81, 0);
        tablaEncabezado.setBackgroundColor(fondo);
        tablaEncabezado.setTextAlignment(TextAlignment.CENTER);
        tablaEncabezado.setWidth(UnitValue.createPercentValue(100));
        tablaEncabezado.setFontSize(25);
        PdfFont fuente = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        tablaEncabezado.setFont(fuente);
        tablaEncabezado.setUnderline();
        return tablaEncabezado;
    }

    /**
     * Crea la tabla de pie de pagina, con el numero de pagina
     *
     * @param docEvent Evento del documento
     * @return Pie de pagina con el numero de pagina
     */
    private Table crearTablaPie(PdfDocumentEvent docEvent, String fecha, String fef) {
        PdfPage page = docEvent.getPage();
        float[] anchos = {4F, 4F};
        Table tablaPie = new Table(anchos);
        tablaPie.setWidth(527F);
        Integer pageNum = docEvent.getDocument().getPageNumber(page);

        Color fondo = new DeviceCmyk(0, 11, 50, 0);
        tablaPie.setBackgroundColor(fondo);

        Calendar g = new GregorianCalendar();
        int ann = g.get(Calendar.YEAR);
        int mes = g.get(Calendar.MONTH) + 1;
        int dia = g.get(Calendar.DATE);
        //String fecha = String.valueOf(dia + "/" + mes + "/" + ann);

        Cell cell;

        cell = new Cell();
        cell.add(new Paragraph(fef + " " + fecha).setTextAlignment(TextAlignment.LEFT));
        cell.setBorder(Border.NO_BORDER);
        tablaPie.addFooterCell(cell);

        cell = new Cell();
        cell.add(new Paragraph("Pagina: " + pageNum).setTextAlignment(TextAlignment.RIGHT).setMarginRight(20));
        cell.setBorder(Border.NO_BORDER);
        tablaPie.addFooterCell(cell);

        tablaPie.setWidth(UnitValue.createPercentValue(100));

        return tablaPie;
    }

    /**
     * Manejador del evento de cambio de pagina, agrega el encabezado y pie de
     * pagina
     *
     * @param event Evento de pagina
     */
    @Override
    public void handleEvent(Event event) {
        try {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();

            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            Table tablaEncabezado = this.crearTablaEncabezado(nombre, text);
            Rectangle rectanguloEncabezado = this.crearRectanguloEncabezado(docEvent);
            Canvas canvasEncabezado = new Canvas(canvas, pdfDoc, rectanguloEncabezado);
            canvasEncabezado.add(tablaEncabezado);

            Table tablaNumeracion = this.crearTablaPie(docEvent, fecha, fef);
            Rectangle rectanguloPie = this.crearRectanguloPie(docEvent);
            Canvas canvasPie = new Canvas(canvas, pdfDoc, rectanguloPie);
            canvasPie.add(tablaNumeracion);
        } catch (IOException ex) {
            Logger.getLogger(EventoPagina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
