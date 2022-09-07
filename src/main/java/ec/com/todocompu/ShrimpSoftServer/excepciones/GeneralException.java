package ec.com.todocompu.ShrimpSoftServer.excepciones;

public final class GeneralException extends RuntimeException {

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralException(final String mensaje, final String causa) {
        super(mensaje);
    }
    
    public GeneralException(final String mensaje) {
        super(mensaje);
    }

}
