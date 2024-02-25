package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.EmailOrPasswordNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.UserAlreadyExistsException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.User;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.AuthenticationResponse;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.SignInDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.SignUpDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.enums.Role;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.interfaces.AuthenticationService;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.interfaces.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager; 
	
	
	@Override
	public AuthenticationResponse signUp(SignUpDTO request) { // Devolvemos el token al registrarse o iniciar sesion.
		if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
			throw new EmailOrPasswordNotFoundException("No se ha encotrado el usuario o la contraseña");
		}
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException("Este email ya está registrado");
		}
		
		User user = User.builder() // Creamos nuestro objeto usuario
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())) // Encriptamos la clave
				.role(Role.USER).build();
		
		userRepo.save(user);
		String jwt = jwtService.tokenGenerator(user);
		
		return AuthenticationResponse.builder().token(jwt).build();
	}

	@Override
	public AuthenticationResponse signIn(SignInDTO request) {
		authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(), 
							request.getPassword())); 
		User user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("No se ha encontrado al usuario"));
		String jwt = jwtService.tokenGenerator(user);
		
		return AuthenticationResponse.builder().token(jwt).build();
	}

}

