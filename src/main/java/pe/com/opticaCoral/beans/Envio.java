package pe.com.opticaCoral.beans;

public class Envio {

    private Pais idPais;
    private double costo;
    private boolean envio;

    public Envio() {
    }

    public Envio(Pais idPais, double costo, boolean envio) {
        this.idPais = idPais;
        this.costo = costo;
        this.envio = envio;
    }

    public boolean isEnvio() {
        return envio;
    }

    public void setEnvio(boolean envio) {
        this.envio = envio;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

}
