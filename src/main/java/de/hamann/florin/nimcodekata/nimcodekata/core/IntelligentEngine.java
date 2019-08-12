package de.hamann.florin.nimcodekata.nimcodekata.core;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;

public class IntelligentEngine implements IGameEngine {

	private static final Logger LOG = LoggerFactory.getLogger(NormalEngine.class);

	@Override
	public void setPlayerMove(Game game, GameAction playerMove) {
		game.setCurrentGameFiguresCount(game.getCurrentGameFiguresCount() - playerMove.getActionCount());

		LOG.info("User reduce by {}. New FiguresCount: {}.", playerMove.getActionCount(), game.getCurrentGameFiguresCount());

		determineWinner(game);
	}

	@Override
	public void setCpuMove(Game game) {
		int move = ThreadLocalRandom.current().nextInt(2, 3);
		if (move != 1 && game.getCurrentGameFiguresCount() > 1)
			game.setCurrentGameFiguresCount(game.getCurrentGameFiguresCount() - move - 1);
		else
			game.setCurrentGameFiguresCount(game.getCurrentGameFiguresCount() - 1);

		LOG.info("CPU reduce by {}. New FiguresCount: {}.", move, game.getCurrentGameFiguresCount());

		determineWinner(game);

	}

	@Override
	public void determineWinner(Game game) {
		if (game.getCurrentGameFiguresCount() == 0)
			game.setOpponentAsWinnerAndGameStateToEnd();
		else
			game.togglePlayer();
	}

	@Override
	public String getEngineName() {
		return this.getClass().getSimpleName();
	}

}
