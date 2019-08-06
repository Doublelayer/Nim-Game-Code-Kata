package de.hamann.florin.nimcodekata.nimcodekata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hamann.florin.nimcodekata.nimcodekata.exception.GameNotFoundException;
import de.hamann.florin.nimcodekata.nimcodekata.model.Game;
import de.hamann.florin.nimcodekata.nimcodekata.model.GameAction;
import de.hamann.florin.nimcodekata.nimcodekata.repository.GameRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameProviderService {

	private static final Logger LOG = LoggerFactory.getLogger(GameProviderService.class);

	@Autowired
	private GameRepository gameRepository;

	public Game createAndSaveNewGame(Game newGame) {
		Game game = gameRepository.save(newGame);
		LOG.info("User: " + game.getPlayer() + " has created: " + game.getGameId());
		return game;
	}

	public void deleteGameId(Long id) {
		gameRepository.deleteById(id);
		LOG.info("Deleted GameID: " + id);
	}

	public Game findGameById(Long id) {
		return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
	}

	public Game playMove(GameAction gameAction, Long id) {
		Game gameFound = findGameById(id);
		gameFound.setFiguresCount(gameAction.getActionCount());

		LOG.info("User: " + gameFound.getPlayer() + " has updatet FiguresCount to " + gameFound.getFiguresCount());

		return gameRepository.save(gameFound);

	}
}
