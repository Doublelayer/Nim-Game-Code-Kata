package de.hamann.florin.nimcodekata.nimcodekata.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GameAction {

	@NotNull()
	@Min(value = 1, message = "Game move has to be in range between 1 to 3")
	@Max(value = 3)
	private int actionCount;

	public int getActionCount() {
		return actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

}
