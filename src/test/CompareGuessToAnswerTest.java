package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import company.Company;
import stockle.Stockle;

class CompareGuessToAnswerTest {
	
	private Stockle game;
	private Company apple;
	private Company amazon;
	private Company penn;
	
	@BeforeEach
	void setup() {
		game = new Stockle();
		game.loadData();
		apple = game.getAllCompanies().get("AAPL");
		amazon = game.getAllCompanies().get("AMZN");
		penn = game.getAllCompanies().get("PENN");
	}

	@Test
	void testCompareSectorsSame() {
		boolean sameSectors = game.compareSectors(apple, apple);
		assertTrue(sameSectors);
	}
	
	@Test
	void testCompareSectorsDifferent() {
		boolean sameSectors = game.compareSectors(apple, amazon);
		assertFalse(sameSectors);
	}
	
	@Test
	void testCompareIndustriesSame() {
		boolean sameIndustries = game.compareIndustries(apple, apple);
		assertTrue(sameIndustries);
	}
	
	@Test
	void testCompareIndustriesDifferent() {
		boolean sameIndustries = game.compareIndustries(apple, amazon);
		assertFalse(sameIndustries);
	}
	
	@Test
	void testCompareMarketCapsSame() {
		boolean sameMarketCaps = game.compareMarketCaps(apple, apple);
		assertTrue(sameMarketCaps);
	}
	
	@Test
	void testCompareMarketCapsDifferent() {
		boolean sameMarketCaps = game.compareMarketCaps(apple, amazon);
		assertFalse(sameMarketCaps);
	}
	
	@Test
	void testCompareSizesSame() {
		boolean sameSize = game.compareSizes(apple, apple);
		assertTrue(sameSize);
	}
	
	@Test
	void testCompareSizesDifferent() {
		boolean sameSize = game.compareSizes(apple, penn);
		assertFalse(sameSize);
	}
	
	@Test
	void testCompareHeadquartersSame() {
		boolean sameHeadquarters = game.compareHeadquarters(apple, apple);
		assertTrue(sameHeadquarters);
	}
	
	@Test
	void testCompareHeadquartersDifferent() {
		boolean sameHeadquarters = game.compareHeadquarters(apple, amazon);
		assertFalse(sameHeadquarters);
	}
	
	@Test
	void testCompareYearsFoundedSame() {
		boolean sameYearFounded = game.compareYearsFounded(apple, apple);
		assertTrue(sameYearFounded);
	}
	
	@Test
	void testCompareYearsFoundedDifferent() {
		boolean sameYearFounded = game.compareYearsFounded(apple, amazon);
		assertFalse(sameYearFounded);
	}
	
	@Test
	void testCompareOneYearReturnsSame() {
		boolean sameOneYearReturns = game.compareOneYearReturns(apple, apple);
		assertTrue(sameOneYearReturns);
	}
	
	@Test
	void testCompareOneYearReturnsDifferent() {
		boolean sameOneYearReturns = game.compareOneYearReturns(apple, amazon);
		assertFalse(sameOneYearReturns);
	}
	
	@Test
	void testIsCorrectAnswer() {
		boolean isCorrect = game.isCorrectAnswer(apple, apple);
		assertTrue(isCorrect);
	}
	
	@Test
	void testIsNotCorrectAnswer() {
		boolean isCorrect = game.isCorrectAnswer(apple, amazon);
		assertFalse(isCorrect);
	}
	
	@Test
	void testCompareGuessToAnswerSame() {
		game.setAnswer(apple);
		boolean isCorrect = game.compareGuessToAnswer(apple);
		assertTrue(isCorrect);
	}
	
	@Test
	void testCompareGuessToAnswerDifferent() {
		game.setAnswer(apple);
		boolean isCorrect = game.compareGuessToAnswer(amazon);
		assertFalse(isCorrect);
	}

}
