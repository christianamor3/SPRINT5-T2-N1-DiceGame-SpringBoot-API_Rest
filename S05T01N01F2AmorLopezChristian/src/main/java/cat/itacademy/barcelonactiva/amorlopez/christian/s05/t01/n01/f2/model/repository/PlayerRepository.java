package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.domain.Player;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String>{
	
	boolean existsByPlayerName(String name);
}
