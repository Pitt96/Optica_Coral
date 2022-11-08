package pe.com.opticaCoral.beans;

public class ProductoMoneda {

    private int idProductoMoneda;
    private String moneda;
    private double precio;
    private double precionuevo;
    private Producto idProducto;

    public ProductoMoneda() {
    }

    public ProductoMoneda(int idProductoMoneda, double precio, double precionuevo) {
        this.idProductoMoneda = idProductoMoneda;
        this.precio = precio;
        this.precionuevo = precionuevo;
    }

    public ProductoMoneda(int idProductoMoneda, String moneda, double precio, double precionuevo, Producto idProducto) {
        this.idProductoMoneda = idProductoMoneda;
        this.moneda = moneda;
        this.precio = precio;
        this.precionuevo = precionuevo;
        this.idProducto = idProducto;
    }

    public ProductoMoneda(String moneda, double precio, double precionuevo) {
        this.moneda = moneda;
        this.precio = precio;
        this.precionuevo = precionuevo;
        this.idProducto = idProducto;
    }

    public int getIdProductoMoneda() {
        return idProductoMoneda;
    }

    public void setIdProductoMoneda(int idProductoMoneda) {
        this.idProductoMoneda = idProductoMoneda;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecionuevo() {
        return precionuevo;
    }

    public void setPrecionuevo(double precionuevo) {
        this.precionuevo = precionuevo;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

}
