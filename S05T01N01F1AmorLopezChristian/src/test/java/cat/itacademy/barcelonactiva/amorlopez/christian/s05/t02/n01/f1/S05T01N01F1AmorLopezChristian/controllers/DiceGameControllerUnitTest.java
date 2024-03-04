package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.config.JwtFilter;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.GameServiceImpl;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.PlayerServiceImpl;

@WebMvcTest (controllers = DiceGameController.class) // Nos permite probar los controladores.
@AutoConfigureMockMvc(addFilters = false) // Para que nos deje comprobar sin seguridad.
public class DiceGameControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean // Agregamos objetos simulados al contexto de la aplicacion
	private PlayerServiceImpl playerService;
	
	@MockBean
	private GameServiceImpl gameService;
	
	@MockBean
	private JwtFilter jwtFilter;
	
	@Autowired
	private ObjectMapper objectMapper;
		
	private PlayerDTO playerDTOTest;
	private PlayerDTO playerDTOTestReturned;
	
	private GameDTO gameDTOTest;
	private GameDTO gameDTOTestReturned;
	
	private PlayerDTO playerDTOTest2;
	
	@BeforeEach
	void setUp() {
		playerDTOTest = PlayerDTO.builder().playerID(1L).playerName("Christian").build();
		playerDTOTestReturned = PlayerDTO.builder().playerID(1L).playerName("Christian").winRate(50.0).build();
		gameDTOTest = GameDTO.builder().gameID(1).diceOne(2).diceTwo(5).won(true).build();
		gameDTOTestReturned = GameDTO.builder().gameID(1).diceOne(2).diceTwo(5).won(true).build();
		playerDTOTest2 = PlayerDTO.builder().playerID(2L).playerName("Claudia").build();
	}
	
	@AfterEach
	void reset() {
		playerDTOTest = null;
		playerDTOTestReturned = null;
		gameDTOTest = null;
		gameDTOTestReturned = null;
		playerDTOTest2 = null;

	}
	
	
	@Test
	@DisplayName("DiceGameControllerUnitTest - createPlayer should add new player")
	void createPlayer_should_add_new_player() throws JsonProcessingException, Exception {
		 
		when(playerService.save(any(PlayerDTO.class))).thenReturn(playerDTOTestReturned);
		 
		mockMvc.perform(post("/players")
				.contentType(MediaType.APPLICATION_JSON) // Envio un JSON
				.content(objectMapper.writeValueAsString(playerDTOTest))) // Me pasa el objeto a JSON.
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.playerID", is(1)))
				.andExpect(jsonPath("$.playerName", is("Christian")))
				.andExpect(jsonPath("$.winRate", is(50.0)));
		 
	 }
	
	@Test
	@DisplayName("DiceGameControllerUnitTest - updatePlayerName should add update and save player")
	void updatePlayerName_should_update_and_save_player() throws JsonProcessingException, Exception {
		 
		playerDTOTestReturned.setPlayerName("Claudia");
		
		when(playerService.update(anyLong(), any(PlayerDTO.class))).thenReturn(playerDTOTestReturned);
		 
		mockMvc.perform(put("/players/{id}", playerDTOTest.getPlayerID())
				.contentType(MediaType.APPLICATION_JSON) // Envio un JSON
				.content(objectMapper.writeValueAsString(playerDTOTest))) // Me pasa el objeto a JSON.
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.playerID", is(1)))
				.andExpect(jsonPath("$.playerName", is("Claudia")))
				.andExpect(jsonPath("$.winRate", is(50.0)));
		 
	 }
	
	@Test
	@DisplayName("DiceGameControllerUnitTest - playerPlayGame should return GameDTO")
	void playerPlayGame_should_return_GameDTO() throws JsonProcessingException, Exception {
			 		
		when(gameService.playerPlayGame(anyLong())).thenReturn(gameDTOTestReturned);
			 
		mockMvc.perform(post("/players/{id}/games", playerDTOTest.getPlayerID()))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.gameID", is(1)))
					.andExpect(jsonPath("$.diceOne", is(2)))
					.andExpect(jsonPath("$.diceTwo", is(5)))
					.andExpect(jsonPath("$.won", is(true)));
			 
	 }
	
	@Test
	@DisplayName("DiceGameControllerUnitTest - deletePlaysPlayer should delete all players games")
	void deletePlaysPlayer_should_delete_player_games() throws JsonProcessingException, Exception {
			 		
		when(gameService.deleteGames(anyLong())).thenReturn("Se han eliminado las partidas correctamente");
			 
		mockMvc.perform(delete("/players/{id}/games", playerDTOTest.getPlayerID()))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string("Se han eliminado las partidas correctamente"));
			 
	 }
	
	@Test
	@DisplayName("DiceGameControllerUnitTest - getAllPlayers should return list of all players")
	void getAllPlayers_should_return_list_of_players() throws JsonProcessingException, Exception {
		
		when(playerService.findAll()).thenReturn(List.of(playerDTOTest, playerDTOTest2));
		
		mockMvc.perform(get("/players/"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))) // Debe retornar una array de 2 objetos.
				.andExpect(jsonPath("$").isArray());
	}
	
	@Test
	@DisplayName("DiceGameControllerUnitTest - getAllGamesForPlayer should return list of all players game")
	void getAllGamesForPlayer_should_return_list_of_players_games() throws JsonProcessingException, Exception {
		
		when(gameService.listGamesByPlayer(anyLong())).thenReturn(List.of(gameDTOTest));
		
		mockMvc.perform(get("/players/{id}/games", playerDTOTest.getPlayerID()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1))) // Debe retornar una array de 1 objetos Game.
				.andExpect(jsonPath("$").isArray());
	}
	
	
		
}
	
