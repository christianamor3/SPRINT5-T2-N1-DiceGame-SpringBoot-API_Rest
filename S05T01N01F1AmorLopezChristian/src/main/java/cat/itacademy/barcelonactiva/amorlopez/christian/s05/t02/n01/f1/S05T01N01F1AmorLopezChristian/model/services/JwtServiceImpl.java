package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	private static final String SECRET_KEY = "413F4428472B4B6250654368566D5970337336763979244226452948404D6351";
	
	@Override
	public String tokenGenerator(UserDetails userDetails) {
		return tokenGenerator(new HashMap<>(), userDetails);
	}
	
	public String tokenGenerator(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder().claims().add(claims).subject(userDetails.getUsername()) // Le indicamos el usuario que habrá en el token.
				.issuedAt(new Date(System.currentTimeMillis())) // Fecha en que se genera el token
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Fecha en que expira el token (24h)
				.and()
				.signWith(getSigningKey(), Jwts.SIG.HS256).compact(); // Firmamos con la SecretKey.
	}
	
	@Override
	public String getUserName(String token) {
		return getClaim(token, Claims::getSubject); // Nos permitirá obtener el nombre del usuario a partir del token. 
	}

	private <T> T getClaim(String token, Function<Claims, T> claimsResolvers){ // Nos permite obtener un dato concreto del token a partir de todos los datos.
		final Claims claims = getAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	private Claims getAllClaims(String token) { // Nos devuelve todos los datos del token.
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
	
	private SecretKey getSigningKey() { // Decodificamos la clave secreta en bytes para obtener una SecretKey con la que firmaremos y verificaremos tokens. 
		byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBites);
	}
	
	@Override
	public boolean tokenValidation(String token, UserDetails userDetails) {
		final String userName = getUserName(token); // Sacamos el usuario del token.
		return (userName.equals(userDetails.getUsername()) && !tokenIsExpired(token)); // Comprobamos que coincide con el de UserDetails y que la fecha del token no ha expirado.
		
	}
	
	private boolean tokenIsExpired(String token) {
		return getExpiration(token).before(new Date()); // Comprobamos si ha expirado o no.
	}
	
	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration); // Obtenemos la fecha de expiracion del token.
	}

}
