package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services;

public interface AuthenticationService {

	AuthenticationResponse signUp (SignUpRequest request);
	
	AuthenticationResponse signIn (SignInRequest request);
	
}
