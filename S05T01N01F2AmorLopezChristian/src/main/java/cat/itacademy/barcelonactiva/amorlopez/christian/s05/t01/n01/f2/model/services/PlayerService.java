package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.dto.PlayerDTO;

public interface PlayerService {

	PlayerDTO save (PlayerDTO playerDTO);
	
	PlayerDTO update (String id, PlayerDTO playerDTO);
	
	String delete (String id);
	
	PlayerDTO findById (String id);
	
	List<PlayerDTO> findAll ();
	
	double avgWinRate();
	
	PlayerDTO lessWinRatePlayer();
	
	PlayerDTO maxWinRatePlayer();
}
