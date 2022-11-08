package pe.com.opticaCoral.interfaces;

import pe.com.opticaCoral.beans.Cliente;

public interface ICliente {

    public abstract Cliente findByIdPerfil(int idPersona);
}
