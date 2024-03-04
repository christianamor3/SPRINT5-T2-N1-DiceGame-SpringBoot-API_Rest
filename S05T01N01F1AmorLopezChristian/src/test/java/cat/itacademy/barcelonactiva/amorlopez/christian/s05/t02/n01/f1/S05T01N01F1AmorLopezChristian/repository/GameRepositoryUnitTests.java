package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Player;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.GameRepository;

@DataJpaTest // Solo podemos hacer test del repositorio con la entidad que toca
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class GameRepositoryUnitTests {
	
	@Autowired
	GameRepository gameRepo;
	
	private Player playerTest;
	
	private Game gameTest;
	
	@BeforeEach
	void setUp() {
		
		playerTest = Player.builder()
				.playerName("Christian")
				.registerDate(null)
				.gamesList(new ArrayList<>())
				.build();
		
		gameTest = Game.builder()
				.diceOne(1)
				.diceTwo(5)
				.won(false)
				.player(playerTest)
				.build();
	}
	
	@AfterEach
	void removeEntities() {
		playerTest = null;
		gameTest = null;
	}
	
	
	//GIVEN - Dada la situacion
	//WHEN - Cuando haga esto
	//THEN - Deberia pasar esto
	
	@Test
	@DisplayName("GameRepositoryUnitTest - Test for insert new Game")
	void save_should_insert_new_Game() {
		Game gameSaved = gameRepo.save(gameTest);
		
		assertThat(gameSaved).isNotNull();
		assertThat(gameSaved.getGameID()).isGreaterThan(0);
		assertThat(gameSaved.getDiceOne()==1);
		assertThat(gameSaved.getDiceTwo()==5);
		assertThat(gameSaved.isWon()==false);		
	}
	
	@Test
	@DisplayName("GameRepositoryUnitTest - Test for finding a Game")
	void findById_should_find_Game() {
		Game gameSaved = gameRepo.save(gameTest);
		
		Optional <Game> game = gameRepo.findById(gameTest.getGameID());
		
		assertThat(game.get().getGameID()).isEqualTo(gameSaved.getGameID()); // Comparo si lo que me devuelve es igual a lo que yo he guardado
	}
	
	@Test
	@DisplayName("GameRepositoryUnitTest - Test for finding a List of Game")
	void findAll_should_find_listOfGames() {
		
		List<Game> gameList = gameRepo.findAll();
		
		assertThat(gameList).isNotNull();
		assertThat(gameList.size()).isEqualTo(2);	// Coge los Game de los metodos anteriores.
	}
	
	@Test
	@DisplayName("PlayerRepositoryUnitTest - Test for deleting a Player")
	void deleteById_should_delete_Game() {
		gameRepo.save(gameTest);
		
		gameRepo.deleteById(gameTest.getGameID());
		Optional <Game> game = gameRepo.findById(gameTest.getGameID());
		
		assertThat(!game.isPresent());	
	}
}
