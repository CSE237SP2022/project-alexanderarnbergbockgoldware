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
	private Company disney;
	private Company costco;
	private Company microsoft;
	
	@BeforeEach
	void setup() {
		game = new Stockle();
		game.loadData();
		apple = game.getAllCompanies().get("AAPL");
		amazon = game.getAllCompanies().get("AMZN");
		penn = game.getAllCompanies().get("PENN");
		disney = game.getAllCompanies().get("DIS");
		costco = game.getAllCompanies().get("COST");
		microsoft = game.getAllCompanies().get("MSFT");
	}

	@Test
	void testCompareIndustriesSame() {
		boolean sameIndustries = game.compareIndustry(apple, apple);
		assertTrue(sameIndustries);
	}
	
	@Test
	void testCompareIndustriesDifferent() {
		boolean sameIndustries = game.compareIndustry(apple, amazon);
		assertFalse(sameIndustries);
	}
	
	@Test
	void testCompareMarketCapsSame() {
		boolean sameMarketCaps = game.compareMarketCaps(apple, apple);
		assertTrue(sameMarketCaps);
	}
	
	@Test
	void testCompareMarketsCapsFivePercent() {
		boolean withinFive = game.compareMarketCaps(disney, costco);
		assertTrue(withinFive);
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
	void testCompareYearsFoundedWithin10() {
		boolean withinTen = game.compareYearsFounded(apple, microsoft);
		assertTrue(withinTen);
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
