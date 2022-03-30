package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import company.Company;
import stockle.Stockle;

class GenerateAnswerTest {
	
	private Stockle game;
	
	@BeforeEach
	void setup() {
		game = new Stockle();
		game.loadData();
		
	}

	@Test
	void testGeneratedAnswerExists() {
		Company answer = game.generateAnswer();
		assertNotNull(answer);
	}
	
	@Test
	void testGeneratedAnswerIsValid() {
		Company answer = game.generateAnswer();
		HashMap<String, Company> allCompanies = game.getAllCompanies();
		boolean companyExists = allCompanies.containsKey(answer.getSymbol());
		boolean companyIsValid = allCompanies.get(answer.getSymbol()).equals(answer);
		assertTrue(companyExists && companyIsValid);
	}
	
}