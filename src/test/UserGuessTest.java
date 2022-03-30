package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stockle.Stockle;

class UserGuessTest {
	
private Stockle game;
	
	@BeforeEach
	void setup() {
		game = new Stockle();
		game.loadData();
		game.generateAnswer();
	}

	@Test
	void testValidGuess() {
		String userGuess = "AAPL";
		boolean isValidGuess = game.printResult(userGuess);
		assertTrue(isValidGuess);
	}
	
	@Test
	void testInvalidGuess() {
		String userGuess = "This is not a valid stock ticker string.";
		boolean isValidGuess = game.printResult(userGuess);
		assertFalse(isValidGuess);
	}

}
