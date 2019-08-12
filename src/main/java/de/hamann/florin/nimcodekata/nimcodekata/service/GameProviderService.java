package de.hamann.florin.nimcodekata.nimcodekata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import de.hamann.florin.nimcodekata.nimcodekata.core.IGameEngine;
import de.hamann.florin.nimcodekata.nimcodekata.core.IntelligentEngine;
import de.hamann.florin.nimcodekata.nimcodekata.core.NormalEngine;
import de.hamann.florin.nimcodekata.nimcodekata.exception.GameNotFoundException;
import de.hamann.florin.nimcodekata.nimcodekata.model.EGameState;
import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;
import de.hamann.florin.nimcodekata.nimcodekata.repository.IGameRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameProviderService {

	private static final Logger LOG = LoggerFactory.getLogger(GameProviderService.class);

	@Autowired
	private IGameRepository gameRepository;
	private IGameEngine gameEngine;

	public Game createAndSaveNewGame(Game newGame) {
		newGame.setGameState(EGameState.PLAYER_TURN);
		newGame.setGameEngine(newGame.getGameEngine());
		Game game = gameRepository.save(newGame);

		if (newGame.getGameEngine() == 1)
			setEngine(new NormalEngine());
		if (newGame.getGameEngine() == 2)
			setEngine(new IntelligentEngine());

		LOG.info("User: {} has created new Game with ID: {} and with {}", game.getPlayer(), game.getGameId(), gameEngine.getEngineName());
		return game;
	}

	public void deleteGameId(Long id) throws GameNotFoundException {
		gameRepository.deleteById(findGameById(id).getGameId());
		LOG.info("Deleted GameID: " + id);
	}

	public Game findGameById(Long id) {
		return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
	}

	public Game playMove(GameAction playerMove, Long id) throws GameNotFoundException, ResponseStatusException {
		Game gameFound = findGameById(id);

		if (gameFound.getFiguresCount() < playerMove.getActionCount())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ActionCount is bigger then present figuresCount");

		if (gameFound.getGameState() == EGameState.END)
			return gameFound;

		gameEngine.setPlayerMove(gameFound, playerMove);

		if (gameFound.getGameState() != EGameState.END)
			gameEngine.setCpuMove(gameFound);

		return gameRepository.save(gameFound);

	}

	public IGameEngine getEngine() {
		return gameEngine;
	}

	public void setEngine(IGameEngine engine) {
		this.gameEngine = engine;
	}
}
