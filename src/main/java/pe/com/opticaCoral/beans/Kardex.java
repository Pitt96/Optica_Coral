package pe.com.opticaCoral.beans;

import java.sql.Date;

public class Kardex {

    private int idKardex;
    private Date fecha;
    private int stockAnterior;
    private int entrada;
    private int salida;
    private int stockAtual;
    private Producto idProducto;

    public Kardex() {
    }

    public Kardex(int idKardex, Date fecha, int stockAnterior, int entrada, int salida, int stockAtual, Producto idProducto) {
        this.idKardex = idKardex;
        this.fecha = fecha;
        this.stockAnterior = stockAnterior;
        this.entrada = entrada;
        this.salida = salida;
        this.stockAtual = stockAtual;
        this.idProducto = idProducto;
    }

    public Kardex(Date fecha, int stockAnterior, int entrada, int salida, int stockAtual, Producto idProducto) {
        this.fecha = fecha;
        this.stockAnterior = stockAnterior;
        this.entrada = entrada;
        this.salida = salida;
        this.stockAtual = stockAtual;
        this.idProducto = idProducto;
    }

    public int getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(int idKardex) {
        this.idKardex = idKardex;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(int stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    public int getSalida() {
        return salida;
    }

    public void setSalida(int salida) {
        this.salida = salida;
    }

    public int getStockAtual() {
        return stockAtual;
    }

    public void setStockAtual(int stockAtual) {
        this.stockAtual = stockAtual;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

}
