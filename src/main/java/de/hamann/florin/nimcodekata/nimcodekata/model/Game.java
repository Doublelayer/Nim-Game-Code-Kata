package de.hamann.florin.nimcodekata.nimcodekata.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@ApiModel(description = "All details about the Employee. ")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated employee ID")
	private Long gameId;

	@NotEmpty(message = "Player are mendatory!")
	@ApiModelProperty(notes = "The player name (mendatory)")
	private String player;

	@NotNull(message = "Count of Figures are mendatory!")
	@Min(value = 0, message = "Count of Figures have to be in range of 0 to 99999")
	@Max(value = 99999)
	@ApiModelProperty(notes = "The Count of allowed figures for the game")
	private int figuresCount;

	@NotNull(message = "GameEngine is mendatory!")
	@Min(value = 1, message = "GameEngine has to be in range of 1 to 2")
	@Max(value = 2)
	@ApiModelProperty(notes = "GameEngine: Select '1' for the normal Version (no intelligent cpu) | Select '2' for the intelligent Version (intelligent cpu)")
	private int gameEngine;

	private EGameState gameState;
	private EWinner winner = EWinner.DRAW;

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getFiguresCount() {
		return figuresCount;
	}

	public void setFiguresCount(int figuresCount) {
		this.figuresCount = figuresCount;
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

	public Long getGameId() {
		return gameId;
	}

	public EWinner getWinner() {
		return winner;
	}

	public void setWinner(EWinner winner) {
		this.winner = winner;
	}
	

}
