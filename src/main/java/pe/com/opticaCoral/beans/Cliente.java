package pe.com.opticaCoral.beans;

public class Cliente {

    private Persona idPersona;
    private String perfil;

    public Cliente() {
    }

    public Cliente(Persona idPersona, String perfil) {
        this.idPersona = idPersona;
        this.perfil = perfil;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

}
