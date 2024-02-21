package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.dto;

public class GameDTO {

	private String gameID;
	private int diceOne;
	private int diceTwo;
	private boolean won;
	
	public GameDTO() {
	}
		
	public GameDTO(String gameID, int diceOne, int diceTwo, boolean won) {
		this.gameID = gameID;
		this.diceOne = diceOne;
		this.diceTwo = diceTwo;
		this.won = won;
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
	
	
	
	
}
