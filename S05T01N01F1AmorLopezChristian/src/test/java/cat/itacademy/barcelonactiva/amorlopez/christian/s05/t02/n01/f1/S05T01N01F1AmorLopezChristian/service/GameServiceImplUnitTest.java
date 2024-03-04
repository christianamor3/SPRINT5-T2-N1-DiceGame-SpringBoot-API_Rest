package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.exceptions.GamesNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Game;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Player;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.GameServiceImpl;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplUnitTest {
	
	@Mock
	GameRepository gameRepo;
	
	@Mock
	PlayerRepository playerRepo;
	
	@InjectMocks
	GameServiceImpl gameService;
	
	private Game gameTest1;
	private Game gameTest2;
	private Player playerTest;
	
	@BeforeEach
	void setUp() {
		playerTest = Player.builder().playerID(1L).playerName("Christian").build();
		gameTest1 = Game.builder().gameID(1).diceOne(2).diceTwo(5).won(true).player(playerTest).build();
		gameTest2 = Game.builder().gameID(2).diceOne(3).diceTwo(5).won(false).player(playerTest).build();

	}
	
	@AfterEach
	void reset() {
		gameTest1 = null;
		gameTest2 = null;
		playerTest = null;
	}
	
	 @Test
	 @DisplayName("GameServiceImplUnitTest - playerPlayGame player should return GameDTO ")
	 void playerPlayGame_should_return_GameDTO() {
		 
		 when(playerRepo.findById(1L)).thenReturn(Optional.of(playerTest));
		 when(gameRepo.save(any(Game.class))).thenReturn(gameTest1); // Especifico que cuando haga save, se guardara el gameTest1.
		 
		 GameDTO gameDTO = gameService.playerPlayGame(1L);
		 
		 ArgumentCaptor<Game> gameArgCaptor = ArgumentCaptor.forClass(Game.class); // Creo un objeto que captura.
	     verify(gameRepo, times(1)).save(gameArgCaptor.capture()); // Capturo el objeto game (gameTest1) que se esta guardando en el metodo save.

	     Game gameTest1 = gameArgCaptor.getValue(); // Saco el objeto game que he capturado.
		 
		 assertThat(gameTest1.getDiceOne()).isEqualTo(gameDTO.getDiceOne());
		 assertThat(gameTest1.getDiceTwo()).isEqualTo(gameDTO.getDiceTwo());

	 }
	 
	 @Test
	 @DisplayName("GameServiceImplUnitTest - playerPlayGame player should return Exception if no player ")
	 void playerPlayGame_should_return_Exception_if_no_player() {
		 
		 when(playerRepo.findById(1L)).thenReturn(Optional.empty());
		 
		 assertThrows(EntityNotFoundException.class, () -> gameService.playerPlayGame(1));
		 
	 }
	 
	 @Test
	 @DisplayName("GameServiceImplUnitTest - deleteGames player should delete all player games ")
	 void deleteGames_should_delete_all_player_games() {
		 
		 playerTest.addingGame(gameTest1);
		 playerTest.addingGame(gameTest2);
		 
		 when(playerRepo.findById(1L)).thenReturn(Optional.of(playerTest));
		 
		 assertThat("Se han eliminado las partidas del jugador con id " + playerTest.getPlayerID()).isEqualTo(gameService.deleteGames(1L));
		 
	 }
	 
	 @Test
	 @DisplayName("GameServiceImplUnitTest - listGamesByPlayer player should return a list of GameDTO ")
	 void listGamesByPlayer_should_retur_GameDTO_list() {
		 
		 playerTest.addingGame(gameTest1);
		 playerTest.addingGame(gameTest2);
		 
		 when(playerRepo.findById(1L)).thenReturn(Optional.of(playerTest));
		 
		 assertThat(2).isEqualTo(gameService.listGamesByPlayer(1L).size());
		 
	 }
	 
	 @Test
	 @DisplayName("GameServiceImplUnitTest - listGamesByPlayer player should return Exception if no Games")
	 void listGamesByPlayer_should_retur_Exception_if_no_Game() {
		 
		 when(playerRepo.findById(1L)).thenReturn(Optional.of(playerTest));
		 
		 playerTest.setGamesList(new ArrayList<>()); // Tiene una lista de Game pero vacia.
		 
		 assertThrows(GamesNotFoundException.class, () -> gameService.listGamesByPlayer(1L));
		 
	 }
	 
	 
	

}
