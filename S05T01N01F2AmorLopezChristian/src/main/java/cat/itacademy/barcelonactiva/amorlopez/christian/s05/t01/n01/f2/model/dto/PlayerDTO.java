package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.dto;

import java.time.LocalDateTime;

public class PlayerDTO {
	
	
	private String playerID;
	
	private String playerName;
	
	private LocalDateTime registerDate;
	
	private double winRate;
	
	
	public PlayerDTO() {
	}

	public PlayerDTO(String playerName, LocalDateTime registerDate) {
		this.playerName = playerName;
		this.registerDate = registerDate;
	}


	public PlayerDTO(String playerID, String playerName, LocalDateTime registerDate, double winRate) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.registerDate = registerDate;
		this.winRate = winRate;
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


	public double getWinRate() {
		return winRate;
	}


	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}
	
	
	
	

}
