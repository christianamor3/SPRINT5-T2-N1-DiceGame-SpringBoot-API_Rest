package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public PlayerAlreadyExistsException(String mensaje) {
		super(mensaje);
	}
}
