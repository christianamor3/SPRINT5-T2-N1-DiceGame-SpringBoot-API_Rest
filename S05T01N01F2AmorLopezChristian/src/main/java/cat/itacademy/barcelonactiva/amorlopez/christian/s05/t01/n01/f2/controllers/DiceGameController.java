package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions.DocumentNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions.GamesNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions.PlayersNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.services.GameServiceImpl;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.services.PlayerServiceImpl;


@RestController
@RequestMapping("/players")
public class DiceGameController {

	@Autowired
	private PlayerServiceImpl playerService;
	
	@Autowired
	private GameServiceImpl gameService;
	
	@PostMapping("")
	public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO playerDTO){
		try {
			PlayerDTO playerDTO2 = playerService.save(playerDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(playerDTO2);
		} catch (PlayerAlreadyExistsException paee) {
			System.out.println(paee.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un jugador con ese nombre");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PlayerDTO> updatePlayerName(@PathVariable String id, @RequestBody PlayerDTO playerDTO){
		try {
			PlayerDTO playerDTO2 = playerService.update(id, playerDTO);
			return ResponseEntity.ok(playerDTO2);
		} catch (DocumentNotFoundException dnfe) {
			System.out.println(dnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/{id}/games")
	public ResponseEntity<GameDTO> playerPlayGame(@PathVariable String id){
		try {
			GameDTO gameDTO = gameService.playerPlayGame(id);
			return ResponseEntity.ok(gameDTO);
		} catch (DocumentNotFoundException dnfe) {
			System.out.println(dnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{id}/games")
	public ResponseEntity<String> deletePlaysPlayer(@PathVariable String id){
		try {
			String mensaje = gameService.deleteGames(id);
			return ResponseEntity.ok(mensaje);
		} catch (GamesNotFoundException gnfe) {
			System.out.println(gnfe.getMessage());
			return ResponseEntity.notFound().build();
		} catch (DocumentNotFoundException dnfe) {
			System.out.println(dnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePlayer(@PathVariable String id){
		try {
			String mensaje = playerService.delete(id);
			return ResponseEntity.ok(mensaje);
		} catch (DocumentNotFoundException dnfe) {
			System.out.println(dnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
		try {
			List<PlayerDTO> players = playerService.findAll();
			return ResponseEntity.ok(players);
		} catch (PlayersNotFoundException pnfe) {
			System.out.println(pnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/{id}/games")
	public ResponseEntity<List<GameDTO>> getAllGamesForPlayer(@PathVariable String id){
		try {
			List<GameDTO> games = gameService.listGamesByPlayer(id);
			return ResponseEntity.ok(games);
		} catch (GamesNotFoundException gnfe) {
			System.out.println(gnfe.getMessage());
			return ResponseEntity.notFound().build();
		} catch (DocumentNotFoundException dnfe) {
			System.out.println(dnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/ranking")
	public ResponseEntity<Double> avgWinRate(){
		try {
			return ResponseEntity.ok(playerService.avgWinRate());
		} catch (PlayersNotFoundException pnfe) {
			System.out.println(pnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/ranking/loser")
	public ResponseEntity<PlayerDTO> loserPlayer(){
		try {
			return ResponseEntity.ok(playerService.lessWinRatePlayer());
		} catch (PlayersNotFoundException pnfe) {
			System.out.println(pnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/ranking/winner")
	public ResponseEntity<PlayerDTO> winnerPlayer(){
		try {
			return ResponseEntity.ok(playerService.maxWinRatePlayer());
		} catch (PlayersNotFoundException pnfe) {
			System.out.println(pnfe.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
}
