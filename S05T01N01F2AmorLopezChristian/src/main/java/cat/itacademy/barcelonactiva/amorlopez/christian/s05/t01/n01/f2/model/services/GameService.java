package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.dto.GameDTO;


public interface GameService {
	
	GameDTO playerPlayGame(String id);
	
	String deleteGames(String id);
	
	List<GameDTO> listGamesByPlayer(String id);
	
	

}
