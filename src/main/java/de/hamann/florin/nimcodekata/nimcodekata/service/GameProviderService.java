package de.hamann.florin.nimcodekata.nimcodekata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hamann.florin.nimcodekata.nimcodekata.core.GameEngine;
import de.hamann.florin.nimcodekata.nimcodekata.exception.GameNotFoundException;
import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameState;
import de.hamann.florin.nimcodekata.nimcodekata.repository.GameRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameProviderService {

	private static final Logger LOG = LoggerFactory.getLogger(GameProviderService.class);

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private GameEngine gameEngine;

	public Game createAndSaveNewGame(Game newGame) {
		newGame.setGameState(GameState.PLAYER_TURN);
		newGame.setGameEngine(1);
		Game game = gameRepository.save(newGame);
		LOG.info("User: " + game.getPlayer() + " has created new game with ID: " + game.getGameId());
		return game;
	}

	public void deleteGameId(Long id) {
		gameRepository.deleteById(id);
		LOG.info("Deleted GameID: " + id);
	}

	public Game findGameById(Long id) {
		return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
	}

	public Game playMove(GameAction playerMove, Long id) {
		Game gameFound = findGameById(id);
		if (gameFound.getGameState() == GameState.END)
			return gameFound;

		if (gameFound.getGameState() != GameState.END) {
			playerTurn(gameFound, playerMove);
			determineWinner(gameFound);
		}

		if (gameFound.getGameState() != GameState.END) {
			cpuTurn(gameFound);
			determineWinner(gameFound);
		}

		return gameRepository.save(gameFound);

	}

	private void playerTurn(Game game, GameAction action) {
		game.setFiguresCount(game.getFiguresCount() - action.getActionCount());
		game.setGameState(GameState.CPU_TURN);
		gameRepository.save(game);
		LOG.info("User reduce by {}. New FiguresCount: {}.", action.getActionCount(), game.getFiguresCount());

	}

	private void cpuTurn(Game game) {
		int cpu = gameEngine.getCpuMove(game);
		game.setFiguresCount(game.getFiguresCount() - cpu);
		game.setGameState(GameState.PLAYER_TURN);
		gameRepository.save(game);
		LOG.info("CPU reduce by {}. New FiguresCount: {}.", cpu, game.getFiguresCount());
	}

	private void determineWinner(Game game) {
		if (game.getFiguresCount() == 0)
			game.setGameState(GameState.END);

	}

}
