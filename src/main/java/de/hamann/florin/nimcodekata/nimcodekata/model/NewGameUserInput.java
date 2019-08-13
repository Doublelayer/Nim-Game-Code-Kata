package de.hamann.florin.nimcodekata.nimcodekata.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class NewGameUserInput {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long newGameId;

	@NotEmpty(message = "Player are mendatory!")
	@ApiModelProperty(notes = "The player name (mendatory)")
	private String player;
	
	@NotNull(message = "GameEngine is mendatory!")
	@Min(value = 1, message = "GameEngine has to be in range of 1 to 2")
	@Max(value = 2)
	@ApiModelProperty(notes = "GameEngine: Select '1' for the normal Version (no intelligent cpu) | Select '2' for the intelligent Version (intelligent cpu)")
	private int gameEngine;
	
	@NotNull(message = "Count of Figures are mendatory!")
	@Min(value = 2, message = "Count of Figures have to be in range of 2 to 99999")
	@Max(value = 99999)
	@ApiModelProperty(notes = "The initial count of Game figures")
	private int figuresCount;

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

	public int getFiguresCount() {
		return figuresCount;
	}

	public void setFiguresCount(int figuresCount) {
		this.figuresCount = figuresCount;
	}

}
