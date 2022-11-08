package pe.com.opticaCoral.interfaces;

import pe.com.opticaCoral.beans.Persona;

public interface IPersona {

    public abstract String insertCliente(Persona p);

    public abstract Persona findById(int idPersona);

    public abstract String updateClave(Persona p);

    public abstract String updateDatos(Persona p);
    
    public abstract String updateDatos2(Persona p);

}
