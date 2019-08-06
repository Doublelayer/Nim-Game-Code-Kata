package de.hamann.florin.nimcodekata.nimcodekata.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameId;

	@NotEmpty(message = "Player are mendatory!")
	private String player;

	@NotNull(message = "Count of Figures are mendatory!")
	@Min(value = 3, message = "Count of Figures have to be in range of 3 to 13")
	@Max(value = 13)
	private int figuresCount;

	@NotNull(message = "Variation of Game is mendatory!")
	@Min(value = 1, message = "Variation of Game has to be in range of 1 to 3")
	@Max(value = 3)
	private int gameVariation;

	private GameState gameState;

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

	public int getGameVariation() {
		return gameVariation;
	}

	public void setGameVariation(int gameVariation) {
		this.gameVariation = gameVariation;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Long getGameId() {
		return gameId;
	}

}
