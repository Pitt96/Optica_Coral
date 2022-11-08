package pe.com.opticaCoral.beans;

public class Item {

    private Producto p;
    private int cantidad;
    private double importe;
    private double base;
    private double impuesto;
    private Envio costoEnvio;

    public double getImporte() {
        return this.importe = this.cantidad * this.p.getPrecio();
    }

    public double getBase() {
        return this.base = this.importe - this.p.getPrecionuevo();
    }

    public double getImpuesto() {
        return this.impuesto = (this.base * p.getIgv()) / 100;
    }

    public Item() {
    }

    public Item(Producto p, int cantidad) {
        this.p = p;
        this.cantidad = cantidad;
    }

    public Envio getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Envio costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public Producto getP() {
        return p;
    }

    public void setP(Producto p) {
        this.p = p;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
