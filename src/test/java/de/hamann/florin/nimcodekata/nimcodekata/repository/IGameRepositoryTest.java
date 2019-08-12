package de.hamann.florin.nimcodekata.nimcodekata.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.hamann.florin.nimcodekata.nimcodekata.model.Game;

@DataJpaTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IGameRepositoryTest {
	
	@Autowired
    private IGameRepository gameRepository;

	Game game = new Game();
	
	@Before
	public void beforeTestCreatGameAndSaveItToRepository() {
		game.setInitialGameFiguresCount(13);
		game.setGameEngine(1);
		game.setPlayer("Tester");
        
        
        gameRepository.save(game);
	}
	
    @Test
    public void test_1_checkIfGameStoredInRepositoryShouldBeNotNull(){
        Optional<Game> loadedGame = gameRepository.findById(game.getGameId());

        assertThat(loadedGame.get()).isEqualTo(game);
        assertThat(loadedGame.get().getGameId()).isNotNull();

    }

}
