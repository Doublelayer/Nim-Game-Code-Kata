package de.hamann.florin.nimcodekata.nimcodekata.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/nim")
@Api(value = "Nim-Game Crud System", consumes = "application/json", produces = "application/json")
public class GameController {

	@Autowired
	private GameProviderService gameProviderService;

	@ApiOperation(value = "a simple welcome screeen")
	@ApiResponses(@ApiResponse(code = 200, message = "OK"))
	@GetMapping(value = "/")
	public String welcome() {
		return "Willkomen zum Nim-Game. <a href='http://localhost:8080/swagger-ui.html'>Hier finden Sie eine Dokumentation für diese API</a>";
	}

	@ApiOperation(value = "create and save new init game to jpa repositry", response = Game.class)
	@ApiResponses(@ApiResponse(code = 201, message = "CREATED"))
	@PostMapping(value = "/post")
	ResponseEntity<Game> newGame(@Valid @RequestBody Game newGame) {
		return new ResponseEntity<Game>(gameProviderService.createAndSaveNewGame(newGame), HttpStatus.CREATED);
	}

	@GetMapping("/get/{id}")
	@ApiOperation(value = "find and search stored game by given id", response = Game.class)
	ResponseEntity<Game> find(@PathVariable Long id) {
		return new ResponseEntity<Game>(gameProviderService.findGameById(id), HttpStatus.OK);
	}

	@PutMapping("/play/{id}")
	@ApiOperation(value = "find, search and update stored game by given gameAction", response = Game.class)
	ResponseEntity<Game> playMove(@RequestBody @Valid GameAction gameAction, @PathVariable Long id) {
		return new ResponseEntity<Game>(gameProviderService.playMove(gameAction, id), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "find and delete stored game by given id", response = void.class)
	ResponseEntity<HttpStatus> deletGame(@PathVariable Long id) {
		gameProviderService.deleteGameId(id);
		return new ResponseEntity<HttpStatus> (HttpStatus.ACCEPTED);
	}

}
