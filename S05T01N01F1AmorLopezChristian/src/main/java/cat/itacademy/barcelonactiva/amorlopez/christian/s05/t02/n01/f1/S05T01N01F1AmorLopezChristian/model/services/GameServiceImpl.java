package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services;

import java.util.List;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.GamesNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Player;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepo;
	
	@Autowired
	private PlayerRepository playerRepo;
	
	@Override
	public GameDTO playerPlayGame(long id) {
		
		Optional<Player> player = playerRepo.findById(id); // Buscamos el jugador que queremos que juegue
		
		Game game = new Game(); // Creamos el juego
		
		if (player.isPresent()) { // Si encontramos el jugador, jugamos.
			Player playerNew = player.get();
					
			dicePlay(game); // Le damos un valor a los dados y jugamos 

			playerNew.addingGame(game); // Le añadimos el juego al jugador.
			
			gameRepo.save(game); // Guardamos el juego, con todos sus datos (dados, por ej) en la base de datos.
			
			return toDTO(game);
			
		} else {
			throw new EntityNotFoundException("No se ha encontrado el jugador");
		}
		
	}

	@Override
	public String deleteGames(long id) {
		Optional<Player> player = playerRepo.findById(id);
		List<Game> games = player.get().getGamesList();
		
		if (player.isPresent() && !games.isEmpty()) {
			gameRepo.deleteAll(games);
			return "Se han eliminado las partidas del jugador con id " + id;
		} else if (games.isEmpty()){
			throw new GamesNotFoundException("No se han encontrado partidas en el jugador");
		} else {
			throw new EntityNotFoundException("No se ha encontrado el jugador");
		}
	}

	@Override
	public List<GameDTO> listGamesByPlayer(long id) {
		Optional<Player> player = playerRepo.findById(id);
		List<Game> games = player.get().getGamesList();
		
		if (player.isPresent() && !games.isEmpty()) {
			return games.stream().map(GameServiceImpl::toDTO).collect(Collectors.toList()); 
		} else if (games.isEmpty()){
			throw new GamesNotFoundException("No se han encontrado partidas en el jugador");
		} else {
			throw new EntityNotFoundException("No se ha encontrado el jugador");
		}
	}
	
	public void dicePlay(Game game) {
		Random random = new Random();
		int diceOne = random.nextInt(6)+1;
		int diceTwo = random.nextInt(6)+1;
		game.setDiceOne(diceOne); // Hay que guardar el valor de los dados por que los necesitamos para la base de datos.
		game.setDiceTwo(diceTwo);
		game.setWon(game.wonOrNot());
		
	}

	public static GameDTO toDTO(Game game) {
		GameDTO gameDTO = new GameDTO();
		
		gameDTO.setGameID(game.getGameID());
		gameDTO.setDiceOne(game.getDiceOne());
		gameDTO.setDiceTwo(game.getDiceTwo());
		gameDTO.setWon(game.wonOrNot());
		
		return gameDTO;
	}
	
}
