package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions;

public class DocumentNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public DocumentNotFoundException(String mensaje) {
		super(mensaje);
	}
}
