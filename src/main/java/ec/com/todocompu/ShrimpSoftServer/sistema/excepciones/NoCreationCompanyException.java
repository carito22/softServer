package ec.com.todocompu.ShrimpSoftServer.sistema.excepciones;

public class NoCreationCompanyException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoCreationCompanyException(String message) {
		super(message);
	}
}
