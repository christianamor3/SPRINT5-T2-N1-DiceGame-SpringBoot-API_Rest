package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.EmailOrPasswordNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.UserAlreadyExistsException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.AuthenticationResponse;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.SignInDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.SignUpDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authService;

	@PostMapping("/singUp")
	public ResponseEntity<AuthenticationResponse> signUp (@RequestBody SignUpDTO request){
		try {
			return ResponseEntity.ok(authService.signUp(request));
		} catch (EmailOrPasswordNotFoundException epnfw) {
			System.out.println(epnfw.getMessage());
			return ResponseEntity.notFound().build();
		} catch (UserAlreadyExistsException uaee) {
			System.out.println(uaee.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<AuthenticationResponse> signIn (@RequestBody SignInDTO request){
		try {
			return ResponseEntity.ok(authService.signIn(request));
		} catch (UserNotFoundException unfw){
			System.out.println(unfw.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
