package de.hamann.florin.nimcodekata.nimcodekata.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Validated
@ApiModel(description = "All details about the Game. ")
public class Game {

	private static final Logger LOG = LoggerFactory.getLogger(Game.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated game ID")
	private Long gameId;
	private String player;
	private int currentGameFiguresCount;
	private int initialGameFiguresCount;
	private int gameEngine;
	private EGameState gameState;
	private EWinner winner = EWinner.NOBODY;

	public Game(String player, int initialGameFiguresCount, int gameEngine) {
		super();
		this.player = player;
		this.initialGameFiguresCount = initialGameFiguresCount;
		this.gameEngine = gameEngine;
	}

	public Game() {
		super();
	}

	@PrePersist
	public void preUpdate() {
		this.currentGameFiguresCount = initialGameFiguresCount;
	}

	public void togglePlayer() {
		if (this.getGameState() == EGameState.PLAYER_TURN)
			this.setGameState(EGameState.CPU_TURN);
		else
			this.setGameState(EGameState.PLAYER_TURN);

		LOG.info("Wait for next {}", this.getGameState());
	}

	public void setOpponentAsWinnerAndGameStateToEnd() {
		if (this.getGameState() == EGameState.PLAYER_TURN)
			this.setWinner(EWinner.CPU);
		else
			this.setWinner(EWinner.PLAYER);

		this.setGameState(EGameState.END);
		LOG.info("{} has won the Game!", this.getWinner());
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getGameEngine() {
		return gameEngine;
	}

	public void setGameEngine(int gameEngine) {
		this.gameEngine = gameEngine;
	}

	public EGameState getGameState() {
		return gameState;
	}

	public void setGameState(EGameState gameState) {
		this.gameState = gameState;
	}

	public EWinner getWinner() {
		return winner;
	}

	public void setWinner(EWinner winner) {
		this.winner = winner;
	}

	public static Logger getLog() {
		return LOG;
	}

	public Long getGameId() {
		return gameId;
	}

	public int getCurrentGameFiguresCount() {
		return currentGameFiguresCount;
	}

	public void setCurrentGameFiguresCount(int currentGameFiguresCount) {
		this.currentGameFiguresCount = currentGameFiguresCount;
	}

	public int getInitialGameFiguresCount() {
		return initialGameFiguresCount;
	}

	public void setInitialGameFiguresCount(int initialGameFiguresCount) {
		this.initialGameFiguresCount = initialGameFiguresCount;
	}

}
