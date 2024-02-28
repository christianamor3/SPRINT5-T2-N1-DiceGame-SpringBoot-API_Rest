package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

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
	

	
	public boolean wonOrNot() {
		return diceOne + diceTwo == 7;
	}

}
