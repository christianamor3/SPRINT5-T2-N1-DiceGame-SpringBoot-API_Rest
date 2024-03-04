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

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Player;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.PlayerRepository;

@DataJpaTest // Solo podemos hacer test del repositorio con la entidad que toca
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class PlayerRepositoryUnitTests {
	
	@Autowired
	PlayerRepository playerRepo;
	
	private Player playerTest;
	
	private Game gameTest;
	
	@BeforeEach
	void setUp() {
		
		playerTest = Player.builder()
				.playerName("Christian")
				.registerDate(null)
				.gamesList(new ArrayList<>())
				.build();
	}
	
	@AfterEach
	void removeEntities() {
		playerTest = null;
	}
	
	
	//GIVEN - Dada la situacion
	//WHEN - Cuando haga esto
	//THEN - Deberia pasar esto
	
	@Test
	@DisplayName("PlayerRepositoryUnitTest - Test for insert new Player")
	void save_should_insert_new_Player() {
		Player playerSaved = playerRepo.save(playerTest);
		
		assertThat(playerSaved).isNotNull();
		assertThat(playerSaved.getPlayerID()).isGreaterThan(0);
		assertThat(playerSaved.getPlayerName()).isEqualTo("Christian");
	}
	
	@Test
	@DisplayName("PlayerRepositoryUnitTest - Test for finding a Player")
	void findById_should_find_Player() {
		Player playerSaved = playerRepo.save(playerTest);
		
		Optional <Player> player = playerRepo.findById(playerTest.getPlayerID());
		
		assertThat(player.get().getPlayerID()).isEqualTo(playerSaved.getPlayerID());
	}
	
	@Test
	@DisplayName("PlayerRepositoryUnitTest - Test for finding a List of Players")
	void findAll_should_find_listOfPlayers() {
		
		List<Player> playerList = playerRepo.findAll();
		
		assertThat(playerList).isNotNull();
		assertThat(playerList.size()).isEqualTo(2);	// Coge los Player que he guardado en los tests anteriores.
	}
	
	@Test
	@DisplayName("PlayerRepositoryUnitTest - Test for deleting a Player")
	void deleteById_should_delete_Player() {
		playerRepo.save(playerTest);
		
		playerRepo.deleteById(playerTest.getPlayerID());
		Optional <Player> player = playerRepo.findById(playerTest.getPlayerID());
		
		assertThat(!player.isPresent());
		
	}
}
