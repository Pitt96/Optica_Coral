package pe.com.opticaCoral.beans;

import java.util.ArrayList;
import java.util.List;

public class Producto {

    private int idProducto;
    private String nombre;
    private double precio;
    private double precionuevo;
    private String descripcion;
    private String imagen;
    private String video;
    private boolean recomendado;
    private int stock;
    private boolean visible;
    private Categoria idCategoria;
    private Marca idMarca;
    private Proveedor idProveedor;
    private Empleado empleadoidPersona;
    private double igv;
    private List<ProductoMoneda> productoMoneda = new ArrayList<>();
    private List<Kardex> kardex = new ArrayList<>();

    public Producto() {
    }

    public Producto(int idProducto, int stock) {
        this.idProducto = idProducto;
        this.stock = stock;
    }

    public Producto(int idProducto, String nombre, double precio, double precionuevo, String descripcion, String imagen, String video, boolean recomendado, int stock, boolean visible, Categoria idCategoria, Marca idMarca, Proveedor idProveedor, Empleado empleadoidPersona) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.precionuevo = precionuevo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.video = video;
        this.recomendado = recomendado;
        this.stock = stock;
        this.visible = visible;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.idProveedor = idProveedor;
        this.empleadoidPersona = empleadoidPersona;
    }

    public Producto(String nombre, double precio, double precionuevo, String descripcion, String imagen, String video, boolean recomendado, int stock, boolean visible, Categoria idCategoria, Marca idMarca, Proveedor idProveedor, Empleado empleadoidPersona) {
        this.nombre = nombre;
        this.precio = precio;
        this.precionuevo = precionuevo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.video = video;
        this.recomendado = recomendado;
        this.stock = stock;
        this.visible = visible;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.idProveedor = idProveedor;
        this.empleadoidPersona = empleadoidPersona;
    }

    public Producto(int idProducto, String nombre, double precio, double precionuevo, String descripcion, String imagen, String video, boolean recomendado, int stock, boolean visible, Categoria idCategoria, Marca idMarca, Proveedor idProveedor, Empleado empleadoidPersona, double igv) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.precionuevo = precionuevo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.video = video;
        this.recomendado = recomendado;
        this.stock = stock;
        this.visible = visible;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
        this.idProveedor = idProveedor;
        this.empleadoidPersona = empleadoidPersona;
        this.igv = igv;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public List<Kardex> getKardex() {
        return kardex;
    }

    public void setKardex(List<Kardex> kardex) {
        this.kardex = kardex;
    }

    public List<ProductoMoneda> getProductoMoneda() {
        return productoMoneda;
    }

    public void setProductoMoneda(List<ProductoMoneda> productoMoneda) {
        this.productoMoneda = productoMoneda;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Marca getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Marca idMarca) {
        this.idMarca = idMarca;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Empleado getEmpleadoidPersona() {
        return empleadoidPersona;
    }

    public void setEmpleadoidPersona(Empleado empleadoidPersona) {
        this.empleadoidPersona = empleadoidPersona;
    }

}
