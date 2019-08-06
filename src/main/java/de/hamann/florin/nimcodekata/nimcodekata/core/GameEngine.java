package de.hamann.florin.nimcodekata.nimcodekata.core;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;

@Service
public class GameEngine {

	public int getCpuMove(Game game) {

		switch (game.getGameEngine()) {
		case 1:
			return normalEngine(game.getFiguresCount());
		case 2:
			return intelligentEngine();

		default:
			return 0;
		}
	}

	public int normalEngine(int limit) {
		if (limit <= 3 && limit > 0) {
			return limit;
		}
		if (limit > 3) {
			return ThreadLocalRandom.current().nextInt(1, 3);
		}

		return 0;
	}

	public int intelligentEngine() {
		return 0;

	}

}
