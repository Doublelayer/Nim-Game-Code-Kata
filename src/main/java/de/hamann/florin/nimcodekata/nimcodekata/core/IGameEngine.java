package de.hamann.florin.nimcodekata.nimcodekata.core;

import org.springframework.stereotype.Service;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;

@Service
public interface IGameEngine {
	
	void setPlayerMove(Game game, GameAction playerMove);
	void setCpuMove(Game game);
	void determineWinner(Game game);
	String getEngineName();
}
