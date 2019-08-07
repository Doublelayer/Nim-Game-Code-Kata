package de.hamann.florin.nimcodekata.nimcodekata.core;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;
import de.hamann.florin.nimcodekata.nimcodekata.model.EGameState;
import de.hamann.florin.nimcodekata.nimcodekata.model.EWinner;
import de.hamann.florin.nimcodekata.nimcodekata.service.GameProviderService;

@Service
public class GameEngine {

	private static final Logger LOG = LoggerFactory.getLogger(GameProviderService.class);

	public void setCpuMove(Game game) {
		switch (game.getGameEngine()) {
		case 1:
			normalEngine(game);
			determineWinner(game, EWinner.CPU);
			break;
		case 2:
			intelligentEngine(game);
			determineWinner(game, EWinner.CPU);
			break;
		default:
			break;
		}
		
		game.setGameState(EGameState.PLAYER_TURN);
	}

	private void determineWinner(Game game, EWinner winner) {
		if (game.getFiguresCount() == 0) {
			game.setWinner(winner);
			game.setGameState(EGameState.END);
		}
	}

	public void setPlayerMove(Game game, GameAction playerMove) {
		game.setFiguresCount(game.getFiguresCount() - playerMove.getActionCount());
		game.setGameState(EGameState.CPU_TURN);
		LOG.info("User reduce by {}. New FiguresCount: {}.", playerMove.getActionCount(), game.getFiguresCount());

		determineWinner(game, EWinner.PLAYER);
	}

	public void normalEngine(Game game) {
		int limit = game.getFiguresCount();
		if (limit <= 3 && limit > 0) {
			game.setFiguresCount(game.getFiguresCount() - limit);
			LOG.info("CPU reduce by {}. New FiguresCount: {}.", limit, game.getFiguresCount());
		}
		if (limit > 3) {
			int move = ThreadLocalRandom.current().nextInt(1, 3);
			game.setFiguresCount(game.getFiguresCount() - move);
			LOG.info("CPU reduce by {}. New FiguresCount: {}.", move, game.getFiguresCount());
		}

	}
	
	/*
	 * this is a pseudo implementation of an intelligent game engine.
	 * Feel free to contribute :-)	
	 */
	public void intelligentEngine(Game game) {
		int move = ThreadLocalRandom.current().nextInt(2, 3);
		if (move != 1 && game.getFiguresCount() > 1)
			game.setFiguresCount(game.getFiguresCount() - move - 1);
		else
			game.setFiguresCount(game.getFiguresCount() - 1);
		
		LOG.info("CPU reduce by {}. New FiguresCount: {}.", move, game.getFiguresCount());
	}

}
