package pe.com.opticaCoral.beans;

public class Categoria {

    private int idCategoria;
    private String nombre;
    private boolean visible;
    private Categoria categoriaId;
    private boolean principal;

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre, boolean visible, Categoria categoriaId) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.visible = visible;
        this.categoriaId = categoriaId;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Categoria getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Categoria categoriaId) {
        this.categoriaId = categoriaId;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

}
