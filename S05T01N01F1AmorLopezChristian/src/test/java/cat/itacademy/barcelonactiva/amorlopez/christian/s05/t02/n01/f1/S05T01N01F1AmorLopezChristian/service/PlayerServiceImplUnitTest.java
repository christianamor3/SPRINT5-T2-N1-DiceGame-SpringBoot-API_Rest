package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Player;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.PlayerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceImplUnitTest {

	@Mock
	PlayerRepository playerRepo;
	
	@Mock
	GameRepository gameRepo;
	
	@InjectMocks
	PlayerServiceImpl playerService;
	
	private Player playerTest;
	private PlayerDTO playerDTOTest;
	
	private Player playerTest2;
	private PlayerDTO playerDTOTest2;

	@BeforeEach
	void setUp() {
		playerTest = Player.builder()
					.playerID(1)
					.playerName("Christian")
					.build();
		
		playerDTOTest = PlayerDTO.builder()
					.playerID(1L)
					.playerName("Christian").build();
		
		playerTest2 = Player.builder()
				.playerID(2)
				.playerName("Claudia").build();
	}
	
	 @AfterEach
	 void reset() {
		 playerTest = null;
		 playerDTOTest = null;
	 }
	 
	 @Test
	 @DisplayName("PlayerServiceImplUnitTest - Save should insert a player ")
	 void save_should_insert_player() {
		 when(playerRepo.save(any(Player.class))).thenReturn(playerTest); // Con el mock damos forma al metodo del repo que usara el metodo de la capa service al ejecutarse.
		 
		 PlayerDTO dtoTest = playerService.save(playerDTOTest);
		 
		 assertThat(playerTest.getPlayerName()).isEqualTo(dtoTest.getPlayerName());
	 }
	 
	 @Test
	 @DisplayName("PlayerServiceImplUnitTest - Used Name should return an Exception ")
	 void save_should_return_exception_when_usedName() {
		 when(playerRepo.existsByPlayerName(playerTest.getPlayerName())).thenReturn(true);
		 
		 assertThrows(PlayerAlreadyExistsException.class, () -> playerService.save(playerDTOTest));
	 }
	 
	 @Test
	 @DisplayName("PlayerServiceImplUnitTest - Update should update player ")
	 void update_should_update_player() {
		 when(playerRepo.findById(playerTest.getPlayerID())).thenReturn(Optional.of(playerTest));
		 when(playerRepo.save(any(Player.class))).thenReturn(playerTest);
		 
		 playerDTOTest.setPlayerName("Claudia"); // Claudia es el nombre del DTO que le pondrá al Entity que encuentre con id 1, que es Christian.
		 
		 PlayerDTO dtoTest = playerService.update(1, playerDTOTest);
		 
		 assertThat("Claudia").isEqualTo(dtoTest.getPlayerName());
	 }
	 
	 @Test
	 @DisplayName("PlayerServiceImplUnitTest - findById should return a player ")
	 void findById_should_return_playerDTO() {
		 when(playerRepo.findById(playerTest.getPlayerID())).thenReturn(Optional.of(playerTest));
		 		 
		 PlayerDTO dtoTest = playerService.findById(1); // Cuando acceda a este metodo, pasara el playerTest a DTO, por lo que el nombre del playerTest deberia ser igual que el del DTO que me devuelve
		 
		 assertThat(playerTest.getPlayerName()).isEqualTo(dtoTest.getPlayerName());
	 }
	 
	 @Test
	 @DisplayName("PlayerServiceImplUnitTest - findAll should return list of PlayerDTO ")
	 void findAll_should_return_list_of_PlayerDTO() {
		 when(playerRepo.findAll()).thenReturn(List.of(playerTest, playerTest2));
		 		 
		 List<PlayerDTO> players = playerService.findAll();
		 
		 assertThat(2).isEqualTo(players.size());
	 }
	 
	 @Test
	 @DisplayName("PlayerServiceImplUnitTest - avgWinrate should return 50 ")
	 void avgwinrate_should_return_50() {
		 
		// Necesito crear los juegos y añadirlos a los jugadores, por que el metodo avgWinRate coge la lista de jugadores 
		 // y de cada uno calcula su winRate a partir de sus juegos
		 
		 
		 Game game1 = Game.builder().diceOne(2).diceTwo(5).won(true).build(); 
		 Game game2 = Game.builder().diceOne(3).diceTwo(5).won(false).build();
		 
		 playerTest.addingGame(game1);
		 playerTest2.addingGame(game2);

		 when(playerRepo.findAll()).thenReturn(List.of(playerTest, playerTest2));
		 		 
		 double resultAvgWinRate = playerService.avgWinRate();
		 
		 assertThat(50.0).isEqualTo(resultAvgWinRate);
	 }
	 
}
