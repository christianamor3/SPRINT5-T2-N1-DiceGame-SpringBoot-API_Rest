package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Player")
public class Player {

	@Id
	private String playerID;
	
	@Field("Player_Name")
	private String playerName;
	
	@Field(name="Register_Date")
	private LocalDateTime registerDate;
	
	@DBRef
	private List<Game> gamesList;

	
	public Player() {
	}

	public Player(String playerID, String playerName, LocalDateTime registerDate, List<Game> gamesList) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.registerDate = registerDate;
		this.gamesList = gamesList;
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	public List<Game> getGamesList() {
		return gamesList;
	}

	public void setGamesList(List<Game> gamesList) {
		this.gamesList = gamesList;
	}
	
	public void addingGame(Game game) {
		if (gamesList==null) {
			gamesList = new ArrayList<>();
		} 
			gamesList.add(game);
	}
	
	public double winRate() {
		if (gamesList!=null && !gamesList.isEmpty()) { // si no pongo el Empty me sigue calculando el winRate con una lista vacia.
			long totalGames = gamesList.size();
			long totalWins = gamesList.stream().filter(Game::isWon).count();
			return (double) totalWins/totalGames * 100;
		} else {
			return 0.0; // 0 para que no me de error al crear player.
		}
	}
	
	
	
	
	
}
