package pe.com.opticaCoral.excepciones;

public class ErrorLoginException extends UserException{
    public ErrorLoginException(String mensaje){
        super(mensaje);
    }
}
