package pe.com.opticaCoral.beans;

public class DetalleVenta {

    private int idDetalleVenta;
    private Producto idProducto;
    private Venta idVenta;
    private int cantidad;
    private double valorUnitario;
    private double importe;
    private double descuento;
    private double base;
    private double impuestos;

    public DetalleVenta() {
    }

    public DetalleVenta(int idDetalleVenta, Producto idProducto, Venta idVenta, int cantidad, double valorUnitario, double importe, double descuento, double base, double impuestos) {
        this.idDetalleVenta = idDetalleVenta;
        this.idProducto = idProducto;
        this.idVenta = idVenta;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.importe = importe;
        this.descuento = descuento;
        this.base = base;
        this.impuestos = impuestos;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }

}
