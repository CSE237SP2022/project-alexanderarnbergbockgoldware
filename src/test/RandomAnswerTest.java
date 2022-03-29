package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Stockle.Stockle;
import company.Company;

class LoadRandomAnswer {
	
	private Stockle game;
	
	@BeforeEach
	void setup() {
		game = new Stockle();
		game.loadData();
		
	}

	@Test
	void testRandomAnswer() {
		Company answer = game.answerGenerator();
		assertNotNull(answer);
	}
	
}


