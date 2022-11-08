package pe.com.opticaCoral.correo;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import pe.com.opticaCoral.beans.Item;
import pe.com.opticaCoral.beans.Venta;

public class Emails {

    private final Properties props;

    public Emails() {
        props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        props.put("from", "davidpachecoask@gmail.com");
        props.put("username", "davidpachecoask@gmail.com");
        props.put("password", "Uatr1ceaT");
    }

    public void enviar(String to, String subject, String content) throws MessagingException {

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);
        Transport.send(message);

        System.out.println("﹐ensaje enviado!");
    }

    public void enviarCorreo(String to, String subject, Venta v) throws MessagingException {

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
            }
        });

        double importe = 0;
        double base = 0;
        double impuesto = 0;
        //double tasa = 18.0;

        double sumaImporte = 0;
        double sumaImpuesto = 0;
        double sumaDescuento = 0;

        String mensaje2 = "";
        String mensaje3 = "";
        String mensaje4 = "";
        String mensaje5 = "";

        String mensaje1 = "<table class=\"table\">\n"
                + "            <thead class=\"thead-dark\">\n"
                + "                <tr>\n"
                + "                    <th scope=\"row\">idProducto</th>\n"
                + "                    <th scope=\"col\">Nombre</th>\n"
                + "                    <th scope=\"col\">Precio</th>\n"
                + "                    <th scope=\"col\">IGV</th>\n"
                + "                    <th scope=\"col\">Cantidad</th>\n"
                + "                    <th scope=\"col\">Descuento</th>\n"
                + "                    <th scope=\"col\">Importe</th>\n"
                + "                    <th scope=\"col\">Impuesto</th>\n"
                + "                    <th scope=\"col\">Suma</th>\n"
                + "                </tr>\n"
                + "            </thead>\n"
                + "            <tbody>";

        for (Item dv : v.getItemDetalles()) {

            importe = dv.getCantidad() * dv.getP().getPrecio();
            base = importe - dv.getP().getPrecionuevo();
            impuesto = (base * dv.getP().getIgv()) / 100;

            sumaImporte += importe;
            sumaImpuesto += impuesto;
            sumaDescuento += dv.getP().getPrecionuevo();

            mensaje2 += "<tr>"
                    + "<td>" + dv.getP().getIdProducto() + "</td>"
                    + "<td>" + dv.getP().getNombre() + "</td>"
                    + "<td>" + dv.getP().getPrecio() + "</td>"
                    + "<td>" + dv.getP().getIgv() + "</td>"
                    + "<td>" + dv.getCantidad() + "</td>"
                    + "<td>" + dv.getP().getPrecionuevo() + "</td>"
                    + "<td>" + importe + "</td>"
                    + "<td>" + impuesto + "</td></tr>";
        }

        double total1 = sumaImporte - sumaDescuento + sumaImpuesto;
        mensaje3 += "<tr><td colspan=\"5\"></td>"
                + "<td>" + sumaDescuento + "</td>"
                + "<td>" + sumaImporte + "</td>"
                + "<td>" + sumaImpuesto + "</td>"
                + "<th>" + total1 + "</th></tr>";

        mensaje4 += "<tr><td colspan=\"8\" align=\"right\">Envio: </td>"
                + "<th>" + v.getCostoEnvio() + "</th></tr>";

        double total = sumaImporte - sumaDescuento + sumaImpuesto + v.getCostoEnvio();
        mensaje5 += "<tr><td colspan=\"8\" align=\"right\">Total a Pagar: </td>"
                + "<th>" + total + "</th></tr></tbody></table>";

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(mensaje1 + mensaje2 + mensaje3 + mensaje4 + mensaje5);
        Transport.send(message);

        System.out.println("﹐ensaje enviado!");
    }

    public static void main(String[] args) throws MessagingException {
        Emails e = new Emails();
        e.enviar("indicedepublicaciones@gmail.com", "Hola! Soy un mensajeAA!", "ejemplo de email enviado con Jakarta Mail");
    }
}
