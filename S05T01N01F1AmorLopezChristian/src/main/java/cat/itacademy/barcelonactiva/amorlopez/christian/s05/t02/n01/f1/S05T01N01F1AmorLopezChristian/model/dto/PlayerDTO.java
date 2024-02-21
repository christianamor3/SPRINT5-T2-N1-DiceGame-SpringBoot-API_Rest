package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain.Game;

public class PlayerDTO {
	
	
	private long playerID;
	
	private String playerName;
	
	private LocalDateTime registerDate;
	
	private double winRate;
	
	
	public PlayerDTO() {
	}

	public PlayerDTO(String playerName, LocalDateTime registerDate) {
		super();
		this.playerName = playerName;
		this.registerDate = registerDate;
	}


	public PlayerDTO(long playerID, String playerName, LocalDateTime registerDate, double winRate) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.registerDate = registerDate;
		this.winRate = winRate;
	}


	public long getPlayerID() {
		return playerID;
	}


	public void setPlayerID(long playerID) {
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
