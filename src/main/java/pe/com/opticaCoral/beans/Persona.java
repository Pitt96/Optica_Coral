package pe.com.opticaCoral.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Persona {

    private int idPersona;
    private String nombres;
    private String apellidop;
    private String apellidom;
    private String sexo;
    private String telefono;
    private String email;
    private String foto;
    private boolean visible;
    private String clave;
    private List<Direccion> direccion = new ArrayList<>();
    private List<Cliente> cliente = new ArrayList<>();

    public List<Direccion> getDireccion() {
        return direccion;
    }

    public void setDireccion(List<Direccion> direccion) {
        this.direccion = direccion;
    }

    public List<Cliente> getCliente() {
        return cliente;
    }

    public void setCliente(List<Cliente> cliente) {
        this.cliente = cliente;
    }

    public Persona() {
    }

    public Persona(int idPersona, String clave) {
        this.idPersona = idPersona;
        this.clave = clave;
    }

    public Persona(int idPersona, String nombres, String apellidop, String apellidom, String sexo, String telefono, String foto) {
        this.idPersona = idPersona;
        this.nombres = nombres;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.sexo = sexo;
        this.telefono = telefono;
        this.foto = foto;
    }

    public Persona(String nombres, String apellidop, String apellidom, String sexo, String telefono, String foto) {
        this.nombres = nombres;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.sexo = sexo;
        this.telefono = telefono;
        this.foto = foto;
    }

    public Persona(String nombres, String apellidop, String apellidom, String sexo, String telefono, String email, String foto, boolean visible, String clave) {
        this.nombres = nombres;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.sexo = sexo;
        this.telefono = telefono;
        this.email = email;
        this.foto = foto;
        this.visible = visible;
        this.clave = clave;
    }

    public Persona(int idPersona, String nombres, String apellidop, String apellidom, String sexo, String telefono, String email, String foto, boolean visible, String clave) {
        this.idPersona = idPersona;
        this.nombres = nombres;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.sexo = sexo;
        this.telefono = telefono;
        this.email = email;
        this.foto = foto;
        this.visible = visible;
        this.clave = clave;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidop() {
        return apellidop;
    }

    public void setApellidop(String apellidop) {
        this.apellidop = apellidop;
    }

    public String getApellidom() {
        return apellidom;
    }

    public void setApellidom(String apellidom) {
        this.apellidom = apellidom;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getNombresCompletos(){
        return this.nombres + " "+this.apellidop+" "+this.apellidom;
    }

    public boolean validarEmail() {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher matcher = pattern.matcher(this.email);

        return matcher.find();
    }
}
