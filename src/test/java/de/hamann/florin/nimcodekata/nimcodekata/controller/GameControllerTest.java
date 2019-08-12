package de.hamann.florin.nimcodekata.nimcodekata.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hamann.florin.nimcodekata.nimcodekata.model.EGameState;
import de.hamann.florin.nimcodekata.nimcodekata.model.EWinner;
import de.hamann.florin.nimcodekata.nimcodekata.model.Game;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameControllerTest {
	private final String BASE_URL = "/api/v1/nim";
	private final String PLAYER = "TESTER";
	private final String GAME_ENGINE = "1";
	private final String FIGURES_COUNT = "13";

	private final String JSON_NEW_GAME = "{\"player\":\"" + PLAYER + "\",\"gameEngine\":\"" + GAME_ENGINE + "\",\"figuresCount\":\"" + FIGURES_COUNT + "\"}";
	private final String JSON_NEW_GAME_FIGURES_COUNT_2 = "{\"player\":\"" + PLAYER + "\",\"gameEngine\":\"" + GAME_ENGINE + "\",\"figuresCount\":\"" + 2 + "\"}";
	private final String JSON_PLAY_GAME_WITH_ACTION_COUNT_1 =  "{\"actionCount\":\"1\"}";
	private final String JSON_PLAY_GAME_WITH_ACTION_COUNT_2 =  "{\"actionCount\":\"2\"}";
	private final String JSON_PLAY_GAME_WITH_TO_HIGHT_ACTION_COUNT =  "{\"actionCount\":\"4\"}";
	private final String JSON_PLAY_GAME_WITH_TO_LOW_ACTION_COUNT =  "{\"actionCount\":\"0\"}";

	@Autowired
	private GameController gameController;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void contexLoads() throws Exception {
		assertThat(gameController).isNotNull();
	}

	@Test
	public void test_1_createNewGameShouldReturnWithGameObjectAsJson() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post(BASE_URL + "/post")
				.content(JSON_NEW_GAME)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gameId").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.player").value(PLAYER))
				.andExpect(MockMvcResultMatchers.jsonPath("$.figuresCount").value(FIGURES_COUNT))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gameEngine").value(GAME_ENGINE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gameState").value("PLAYER_TURN"))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void test_2_playMoveWithGameID1ShouldReturnOkStatus() throws Exception
	{
	  mockMvc.perform( MockMvcRequestBuilders
		      .put(BASE_URL  + "/play/{id}", 1)
		      .content(JSON_PLAY_GAME_WITH_ACTION_COUNT_1)
		      .contentType(MediaType.APPLICATION_JSON)
		      .accept(MediaType.APPLICATION_JSON))
			  .andExpect(MockMvcResultMatchers.jsonPath("$.gameId").value("1"))
			  .andExpect(MockMvcResultMatchers.jsonPath("$.player").value(PLAYER))
			  .andExpect(MockMvcResultMatchers.jsonPath("$.gameEngine").value(GAME_ENGINE))
			  .andExpect(MockMvcResultMatchers.jsonPath("$.gameState").value("PLAYER_TURN"))
			  .andExpect(status().isOk());
	}
	
	@Test
	public void test_3_gameMoveShouldSetThePlayerAsWinnerAndGameStateToEndAccordingToDefinedGameRules() throws Exception
	{	
		Game newGame  =  getMappedGameObject(
				mockMvc.perform(MockMvcRequestBuilders
				.post(BASE_URL + "/post")
				.content(JSON_NEW_GAME_FIGURES_COUNT_2)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		  		.andReturn());
				
		Game updatedGame  =  getMappedGameObject(
				mockMvc.perform( MockMvcRequestBuilders
			    .put(BASE_URL  + "/play/{id}", newGame.getGameId())
			    .content(JSON_PLAY_GAME_WITH_ACTION_COUNT_1)
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
		  		.andReturn());

		assertThat(updatedGame.getWinner()).isEqualTo(EWinner.PLAYER);
		assertThat(updatedGame.getGameState()).isEqualTo(EGameState.END);
	}
	
	@Test
	public void test_4_gameMoveShouldSetTheCPUAsWinnerAndGameStateToEndAccordingToDefinedGameRules() throws Exception
	{
		Game newGame  =  getMappedGameObject(
				mockMvc.perform(MockMvcRequestBuilders
				.post(BASE_URL + "/post")
				.content(JSON_NEW_GAME_FIGURES_COUNT_2)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		  		.andReturn());
				
		Game updatedGame  =  getMappedGameObject(
				mockMvc.perform( MockMvcRequestBuilders
			    .put(BASE_URL  + "/play/{id}", newGame.getGameId())
			    .content(JSON_PLAY_GAME_WITH_ACTION_COUNT_2)
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
		  		.andReturn());

		assertThat(updatedGame.getWinner()).isEqualTo(EWinner.CPU);
		assertThat(updatedGame.getGameState()).isEqualTo(EGameState.END);
	}

	@Test
	public void test_5_playMoveWithGameIDAndWithToHighActionCountShouldReturnBadRequestStatus() throws Exception
	{
	  mockMvc.perform( MockMvcRequestBuilders
		      .put(BASE_URL  + "/play/{id}", 1)
		      .content(JSON_PLAY_GAME_WITH_TO_HIGHT_ACTION_COUNT)
		      .contentType(MediaType.APPLICATION_JSON)
		      .accept(MediaType.APPLICATION_JSON))
		      .andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_6_playMoveWithGameIDAndWithToLowActionCountShouldReturnBadRequestStatus() throws Exception
	{
	  mockMvc.perform( MockMvcRequestBuilders
		      .put(BASE_URL  + "/play/{id}", 1)
		      .content(JSON_PLAY_GAME_WITH_TO_LOW_ACTION_COUNT)
		      .contentType(MediaType.APPLICATION_JSON)
		      .accept(MediaType.APPLICATION_JSON))
		      .andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_7_deleteGameFromRepositorySholdReturnAcceptedStatus() throws Exception
	{
		  mockMvc.perform( MockMvcRequestBuilders
			      .delete(BASE_URL  + "/delete/1")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isAccepted());
	}
	
	@Test
	public void test_8_deleteGameFromRepositoryWithIdThatShouldNotExistShouldReturnBadRequest() throws Exception
	{
		  mockMvc.perform( MockMvcRequestBuilders
			      .delete(BASE_URL  + "/delete/1")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound());
	}


	
	private Game getMappedGameObject(MvcResult game) throws JsonParseException, JsonMappingException, IOException {
		String contentAsString = game.getResponse().getContentAsString();
		return objectMapper.readValue(contentAsString, Game.class);
	}
}
