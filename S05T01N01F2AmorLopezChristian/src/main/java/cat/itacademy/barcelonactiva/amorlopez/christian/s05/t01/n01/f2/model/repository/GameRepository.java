package cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.amorlopez.christian.s05.t01.n01.f2.model.domain.Game;

@Repository
public interface GameRepository extends MongoRepository<Game, String>{

}
