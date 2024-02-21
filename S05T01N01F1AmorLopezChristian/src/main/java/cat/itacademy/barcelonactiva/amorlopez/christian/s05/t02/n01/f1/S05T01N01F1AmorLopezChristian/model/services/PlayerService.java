package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t02.n01.f1.S05T01N01F1AmorLopezChristian.model.dto.PlayerDTO;

public interface PlayerService {

	PlayerDTO save (PlayerDTO playerDTO);
	
	PlayerDTO update (long id, PlayerDTO playerDTO);
	
	String delete (long id);
	
	PlayerDTO findById (long id);
	
	List<PlayerDTO> findAll ();
	
	double avgWinRate();
	
	PlayerDTO lessWinRatePlayer();
	
	PlayerDTO maxWinRatePlayer();
}
