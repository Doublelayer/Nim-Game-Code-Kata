package de.hamann.florin.nimcodekata.nimcodekata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
