package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions;

public class UserNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String mensaje) {
		super(mensaje);
	}
}
