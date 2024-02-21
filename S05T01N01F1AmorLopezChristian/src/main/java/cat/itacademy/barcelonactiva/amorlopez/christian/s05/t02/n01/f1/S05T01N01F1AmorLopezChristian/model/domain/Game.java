package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Game") // Entidad por que quiero guardar el registro de jugadas.
public class Game {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long gameID;
	
	@Column(name="Dice_One")
	private int diceOne;
	
	@Column(name="Dice_Two")
	private int diceTwo;
	
	@Column(name="Result")
	private boolean won;
	
	@ManyToOne
	@JoinColumn(name="playerID")
	private Player player;
	
	
	public Game() {
	}

	public Game(long gameID, int diceOne, int diceTwo, boolean won, Player player) {
		this.gameID = gameID;
		this.diceOne = diceOne;
		this.diceTwo = diceTwo;
		this.won = wonOrNot();
		this.player = player;
	}
	
	public boolean wonOrNot() {
		return diceOne + diceTwo == 7;
	}

	public long getGameID() {
		return gameID;
	}

	public void setGameID(long gameID) {
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
