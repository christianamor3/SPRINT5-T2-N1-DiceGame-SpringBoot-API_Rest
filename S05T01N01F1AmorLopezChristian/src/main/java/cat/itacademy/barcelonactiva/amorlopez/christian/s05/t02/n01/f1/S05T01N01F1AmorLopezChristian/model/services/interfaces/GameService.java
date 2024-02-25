package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services.interfaces;

import java.util.List;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.GameDTO;

public interface GameService {
	
	GameDTO playerPlayGame(long id);
	
	String deleteGames(long id);
	
	List<GameDTO> listGamesByPlayer(long id);
	
	

}
