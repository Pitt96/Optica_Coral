package pe.com.opticaCoral.interfaces;

import pe.com.opticaCoral.beans.Login;

public interface ILogin {

    public abstract Login validarLogin(String email, String clave);

    public abstract Login listAll(int idPersona);

}
