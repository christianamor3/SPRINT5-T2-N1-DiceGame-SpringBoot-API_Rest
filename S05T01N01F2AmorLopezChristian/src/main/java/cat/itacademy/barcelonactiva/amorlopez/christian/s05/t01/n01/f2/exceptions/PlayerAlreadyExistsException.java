package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public PlayerAlreadyExistsException(String mensaje) {
		super(mensaje);
	}
}
