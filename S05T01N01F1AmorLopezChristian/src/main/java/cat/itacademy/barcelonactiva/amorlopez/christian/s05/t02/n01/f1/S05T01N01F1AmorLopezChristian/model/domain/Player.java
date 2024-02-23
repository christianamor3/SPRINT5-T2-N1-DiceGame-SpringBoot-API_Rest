package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name="Player")
public class Player {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long playerID;
	
	@Column(name="Player_Name")
	private String playerName;
	
	@Column(name="Register_Date")
	private LocalDateTime registerDate;
	
	@OneToMany(mappedBy = "player")
	private List<Game> gamesList;


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
