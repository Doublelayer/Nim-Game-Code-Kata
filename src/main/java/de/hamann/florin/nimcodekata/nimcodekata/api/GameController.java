package de.hamann.florin.nimcodekata.nimcodekata.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;
import de.hamann.florin.nimcodekata.nimcodekata.service.GameProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

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
	
	@PostMapping("/post")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "create and save new init game to jpa repositry", response = ResponseEntity.class)
	Resource<Game> newGame(@Valid @RequestBody Game newGame) {

	  Game game = gameProviderService.createAndSaveNewGame(newGame);

	  return new Resource<>(game,
	    linkTo(methodOn(GameController.class).newGame(game)).withRel("_self"),
	    linkTo(methodOn(GameController.class).playMove(new GameAction(), game.getGameId())).withRel("play_move"));
	}

	@GetMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "find and search stored game by given id", response = ResponseEntity.class)
	Resource<Game> find(@PathVariable Long id) {
		Game game = gameProviderService.findGameById(id);
		return new Resource<Game>(game,
				linkTo(methodOn(GameController.class).find(game.getGameId())).withRel("_self"),
				linkTo(methodOn(GameController.class).playMove(new GameAction(), game.getGameId())).withRel("play_move"));
	}

	@PutMapping("/play/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "find, search and update stored game by given gameAction", response = ResponseEntity.class)
	Resource<Game> playMove(@RequestBody @Valid GameAction gameAction, @PathVariable Long id) {
		Game game = gameProviderService.playMove(gameAction, id);
		return new Resource<>(game,
				linkTo(methodOn(GameController.class).playMove(new GameAction(), game.getGameId())).withRel("_self"),
				linkTo(methodOn(GameController.class).find(game.getGameId())).withRel("get_game"));
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value = "find and delete stored game by given id", response = ResponseEntity.class)
	Resource<HttpStatus> deletGame(@PathVariable Long id) {
		gameProviderService.deleteGameId(id);
		return new Resource<> (HttpStatus.ACCEPTED);
	}

}
