package de.hamann.florin.nimcodekata.nimcodekata.exception;

public class GameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1029259629274424278L;

	public GameNotFoundException(Long id) {
		super("Could not find Game with ID: " + id);
	}
}