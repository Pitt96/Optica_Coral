package pe.com.opticaCoral.beans;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private int idVenta;
    private Date fecha;
    private double totalNeto;
    private double igv;
    private double totalDescuento;
    private double totalImporte;
    private Cliente clienteidPersona;
    private String estado;
    private double costoEnvio;

    private List<DetalleVenta> detalles = new ArrayList<>();
    private List<Item> itemDetalles = new ArrayList<>();

    public Venta() {
    }

    public Venta(int idVenta, Date fecha, double totalNeto, double igv, double totalDescuento, double totalImporte, Cliente clienteidPersona) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.totalNeto = totalNeto;
        this.igv = igv;
        this.totalDescuento = totalDescuento;
        this.totalImporte = totalImporte;
        this.clienteidPersona = clienteidPersona;
    }

    public Venta(int idVenta, Date fecha, double totalNeto, double igv, double totalDescuento, double totalImporte, Cliente clienteidPersona, double costoEnvio) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.totalNeto = totalNeto;
        this.igv = igv;
        this.totalDescuento = totalDescuento;
        this.totalImporte = totalImporte;
        this.clienteidPersona = clienteidPersona;
        this.costoEnvio = costoEnvio;
    }

    public Venta(int idVenta, Date fecha, double totalNeto, double igv, double totalDescuento, double totalImporte, Cliente clienteidPersona, String estado, double costoEnvio) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.totalNeto = totalNeto;
        this.igv = igv;
        this.totalDescuento = totalDescuento;
        this.totalImporte = totalImporte;
        this.clienteidPersona = clienteidPersona;
        this.estado = estado;
        this.costoEnvio = costoEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Item> getItemDetalles() {
        return itemDetalles;
    }

    public void setItemDetalles(List<Item> itemDetalles) {
        this.itemDetalles = itemDetalles;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(double totalNeto) {
        this.totalNeto = totalNeto;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(double totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public double getTotalImporte() {
        return totalImporte;
    }

    public void setTotalImporte(double totalImporte) {
        this.totalImporte = totalImporte;
    }

    public Cliente getClienteidPersona() {
        return clienteidPersona;
    }

    public void setClienteidPersona(Cliente clienteidPersona) {
        this.clienteidPersona = clienteidPersona;
    }

}
