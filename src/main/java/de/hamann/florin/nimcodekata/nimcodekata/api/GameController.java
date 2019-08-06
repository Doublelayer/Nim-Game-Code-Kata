package de.hamann.florin.nimcodekata.nimcodekata.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;
import de.hamann.florin.nimcodekata.nimcodekata.service.GameProviderService;

@RestController
@RequestMapping("/api/v1/nim/")
public class GameController {

	@Autowired
	private GameProviderService gameProviderService;

//	@GetMapping("/get")
//	List<Game> all() {
//		return gameProviderService.findAll();
//	}

	@PostMapping("/post")
	Game newGame(@Valid @RequestBody Game newGame) {
		return gameProviderService.createAndSaveNewGame(newGame);
	}

	@GetMapping("/get/{id}")
	Game one(@PathVariable Long id) {
		return gameProviderService.findGameById(id);
	}

	@PutMapping("/play/{id}")
	Game playMove(@RequestBody GameAction gameAction, @PathVariable Long id) {
		return gameProviderService.playMove(gameAction, id);
	}

	@DeleteMapping("/delete/{id}")
	void deletGame(@RequestBody Game deletGame, @PathVariable Long id) {
		gameProviderService.deleteGameId(id);
	}

}
