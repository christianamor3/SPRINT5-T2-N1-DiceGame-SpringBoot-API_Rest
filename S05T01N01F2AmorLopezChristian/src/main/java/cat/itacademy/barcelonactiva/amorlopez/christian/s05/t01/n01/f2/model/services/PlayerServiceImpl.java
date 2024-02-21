package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions.DocumentNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.exceptions.PlayersNotFoundException;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.domain.Player;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Autowired
	private PlayerRepository playerRepo;

	@Override
	public PlayerDTO save(PlayerDTO playerDTO) {
		
		if (playerDTO.getPlayerName().isBlank() || playerDTO.getPlayerName().equalsIgnoreCase("ANONIM")) {
			playerDTO.setPlayerName("ANONIM");
		} else if (playerRepo.existsByPlayerName(playerDTO.getPlayerName())) {
			throw new PlayerAlreadyExistsException("Ya existe un jugador con este nombre");
		} 
			return toDTO(playerRepo.save(toEntity(playerDTO)));
	}

	@Override
	public PlayerDTO update(String id, PlayerDTO playerDTO) {
		Optional<Player> player = playerRepo.findById(id);
		
		if (player.isPresent()) {
			player.get().setPlayerName(playerDTO.getPlayerName());
			playerRepo.save(player.get());
			return toDTO(player.get());
		} else {
			throw new DocumentNotFoundException("No se ha encontrado el jugador");
		}
	}
	
	@Override
	public String delete(String id) {
		Optional<Player> player = playerRepo.findById(id);
		
		if (player.isPresent()) {
			playerRepo.deleteById(id);		
			return "Se ha eliminado el jugador correctamente";
		} else {
			throw new DocumentNotFoundException("No se ha encontrado el jugador");
		}	
	}

	@Override
	public PlayerDTO findById(String id) {
		Optional<Player> player = playerRepo.findById(id);
		
		if (player.isPresent()) {
			return toDTO(player.get());
		} else {
			throw new DocumentNotFoundException("No se ha encontrado el jugador");
		}
		
	}

	@Override
	public List<PlayerDTO> findAll() {
		List<PlayerDTO> players = playerRepo.findAll().stream().map(PlayerServiceImpl::toDTO).collect(Collectors.toList());
		
		if (!players.isEmpty()) {
			return players;
		} else {
			throw new PlayersNotFoundException("No se han encontrado jugadores");
		}
	}
	
	@Override
	public double avgWinRate() {
		List<Player> players = playerRepo.findAll();
		
		if (!players.isEmpty()) {
			double totalWinRate = players.stream().mapToDouble(Player::winRate).sum(); //mapToDouble devuelve un flujo de "doubles". Por cada jugador, calcula su winRate.
			return totalWinRate/players.size();
		} else {
			throw new PlayersNotFoundException("No se han encontrado jugadores");
		}
	}

	@Override
	public PlayerDTO lessWinRatePlayer() {
		List<Player> players = playerRepo.findAll();
		Optional<Player> playerLessWinRate;
		
		if (!players.isEmpty()) {
			playerLessWinRate = players.stream().sorted(Comparator.comparing(Player::winRate)).findFirst();
			return toDTO(playerLessWinRate.get());
		} else {
			throw new PlayersNotFoundException("No se han encontrado jugadores");
		}
	}

	@Override
	public PlayerDTO maxWinRatePlayer() {
		List<Player> players = playerRepo.findAll();
		Optional<Player> playerMaxWinRate;
		
		if (!players.isEmpty()) {
			playerMaxWinRate = players.stream().sorted(Comparator.comparing(Player::winRate).reversed()).findFirst();
			return toDTO(playerMaxWinRate.get());
		} else {
			throw new PlayersNotFoundException("No se han encontrado jugadores");
		}
	}
	
	public static PlayerDTO toDTO(Player player) {
		PlayerDTO playerDTO = new PlayerDTO();
		
		playerDTO.setPlayerID(player.getPlayerID());
		playerDTO.setPlayerName(player.getPlayerName());
		playerDTO.setRegisterDate(player.getRegisterDate());
		playerDTO.setWinRate(player.winRate());
		
		return playerDTO;
	}
	
	public static Player toEntity(PlayerDTO playerDTO) {
		Player player = new Player();
		
		player.setPlayerName(playerDTO.getPlayerName());
		player.setRegisterDate(playerDTO.getRegisterDate());
		
		return player;
	}



}
