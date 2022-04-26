package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import company.Company;
import stockle.Stockle;

class HintTest {

private Stockle game;
	
	@BeforeEach
	void setup() {
		game = new Stockle();
		game.loadData();
		game.generateAnswer();
	}

	@Test
	void testGenerateHintsLength() {
		int numHints = game.generateHints().length;
		assertEquals(10, numHints, 0.1);
	}
	
	@Test
	void testGenerateHintsOneCorrectAnswer() {
		Company[] allHints = game.generateHints();
		boolean hintsContainAnswer = false;
		for(Company singleHint:allHints) {
			if (game.compareGuessToAnswer(singleHint)) {
				hintsContainAnswer = true;
			}
		}
		assertTrue(hintsContainAnswer);
	}
	
}
