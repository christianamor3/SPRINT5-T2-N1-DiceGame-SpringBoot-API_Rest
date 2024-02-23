package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PlayerDTO {
	
	
	private long playerID;
	private String playerName;
	private LocalDateTime registerDate;
	private double winRate;
	
}
