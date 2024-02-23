package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.JwtService;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
	
	private final UserDetailsService userDetailsService;
	
	@Autowired
	private JwtServiceImpl jwtService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, // Lo que el cliente envia
										@NonNull HttpServletResponse response, // Lo que devolvemos al cliente
											@NonNull FilterChain filterChain ) throws ServletException, // FilterChain nos permite continuar con la solicitud una vez hayamos filtrado.
																				IOException {
		final String authHeader = request.getHeader("Authorization"); // Es el header de Postman. Valido si mandan algo en el header al hacer la solicitud.
		final String jwt;
		final String userEmail;
		if (authHeader==null || !authHeader.startsWith("Bearer")) { // Valido si el usuario me esta mandando un JSON WEB TOKEN. Si no lo manda, error, si no es de tipo Bearer, error.
			filterChain.doFilter(request, response);
		}
		jwt = authHeader.substring(7); // Saltamos hasta donde empieza el token.
		userEmail = jwtService.getUserName(jwt); // Queremos extraer el email del JWT para comprobar si esta en la base de datos.
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Valido si el token trae usuario.
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); // Compruebo si el usuario esta en la base de datos
			if (jwtService.tokenValidation(jwt, userDetails)) { // Por ultimo, compruebo que el token sea valido, una vez se que el usuario esta en la base de datos.
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, 
																											null, 
																												userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Especifico que los datos de autenticacion vienen en el request.
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}	
		}
		filterChain.doFilter(request, response);
	}
		

}
