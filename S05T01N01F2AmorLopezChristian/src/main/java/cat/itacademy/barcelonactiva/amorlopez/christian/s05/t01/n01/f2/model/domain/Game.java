package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Game") // Entidad por que quiero guardar el registro de jugadas.
public class Game {

	@Id
	private String gameID;
	
	@Field("Dice_One")
	private int diceOne;
	
	@Field(name="Dice_Two")
	private int diceTwo;
	
	@Field(name="Result")
	private boolean won;
	
	@DBRef
	private Player player;
	
	public Game() {
	}

	public Game(String gameID, int diceOne, int diceTwo, boolean won, Player player) {
		this.gameID = gameID;
		this.diceOne = diceOne;
		this.diceTwo = diceTwo;
		this.player = player;
		this.won = wonOrNot();
	}
	
	public boolean wonOrNot() {
		return diceOne + diceTwo == 7;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

	public int getDiceOne() {
		return diceOne;
	}

	public void setDiceOne(int diceOne) {
		this.diceOne = diceOne;
	}

	public int getDiceTwo() {
		return diceTwo;
	}

	public void setDiceTwo(int diceTwo) {
		this.diceTwo = diceTwo;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
}
