package de.hamann.florin.nimcodekata.nimcodekata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;

@Repository
public interface IGameRepository extends JpaRepository<Game, Long> {

}
