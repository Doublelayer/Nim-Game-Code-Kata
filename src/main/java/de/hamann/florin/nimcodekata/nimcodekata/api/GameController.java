package de.hamann.florin.nimcodekata.nimcodekata.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hamann.florin.nimcodekata.nimcodekata.exception.GameNotFoundException;
import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.repository.GameRepository;

@RestController
@RequestMapping("/api/v1/nim/")
public class GameController {

	private static final Logger LOG = LoggerFactory.getLogger(GameController.class);

	@Autowired
	private GameRepository gameRepository;

	@GetMapping("/get")
	List<Game> all() {
		return gameRepository.findAll();
	}

	@PostMapping("/post")
	Game newGame(@RequestBody Game newGame) {
		Game game = gameRepository.save(newGame);
		LOG.info("User: " + game.getPlayer() + " has created: " + game.getGameId());
		return game;
	}

	@GetMapping("/get/{id}")
	Game one(@PathVariable Long id) {
		return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
	}

	@PutMapping("/play/{id}")
	Game playMove(@RequestBody Game updateGame, @PathVariable Long id) {

		return gameRepository.findById(id).map(game -> {
			game.setFiguresCount(updateGame.getFiguresCount());
			LOG.info("User: " + game.getPlayer() + " has updatet FiguresCount to " + game.getFiguresCount());
			return gameRepository.save(game);
		}).orElseThrow(() -> new GameNotFoundException(id));
	}

	@DeleteMapping("/delete/{id}")
	void deletGame(@RequestBody Game deletGame, @PathVariable Long id) {
		gameRepository.deleteById(id);
		LOG.info("Deleted GameID: " + deletGame.getGameId());
	}

}
