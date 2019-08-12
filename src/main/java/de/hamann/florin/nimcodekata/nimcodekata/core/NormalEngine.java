package de.hamann.florin.nimcodekata.nimcodekata.core;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hamann.florin.nimcodekata.nimcodekata.model.EGameState;
import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;

public class NormalEngine implements IGameEngine {

	private static final Logger LOG = LoggerFactory.getLogger(NormalEngine.class);

	@Override
	public void setPlayerMove(Game game, GameAction playerMove) {
		game.setFiguresCount(game.getFiguresCount() - playerMove.getActionCount());
		
		LOG.info("User reduce by {}. New FiguresCount: {}.", playerMove.getActionCount(), game.getFiguresCount());

		determineWinner(game);
	}

	@Override
	public void setCpuMove(Game game) {
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

		determineWinner(game);
	}

	@Override
	public void determineWinner(Game game) {
		if (game.getFiguresCount() == 0) {
			game.setOpponentAsWinner();
			game.setGameState(EGameState.END);
		} else {
			game.togglePlayer();
		}
	}

	@Override
	public String getEngineName() {
		return this.getClass().getSimpleName();
	}
}
