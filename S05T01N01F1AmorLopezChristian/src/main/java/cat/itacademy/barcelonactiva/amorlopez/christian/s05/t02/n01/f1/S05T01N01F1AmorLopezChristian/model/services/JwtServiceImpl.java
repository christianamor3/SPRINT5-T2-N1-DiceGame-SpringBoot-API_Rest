package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtServiceImpl implements JwtService{

	private static final String SECRET_KEY = "";
	
	
	@Override
	public String getUserName(String token) {
		return null;
	}

	@Override
	public boolean tokenValidation(String token, UserDetails userDetails) {
		return false;
	}

}
