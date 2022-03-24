package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Stockle.Stockle;
import company.Company;

class LoadDataTest {
	
	private Stockle game;
	
	@BeforeEach
	void setup() {
		game = new Stockle();
		game.loadData();
	}

	/**
	 * Loading data from the CSV correctly & accurately creates Company objects
	 */
	@Test
	void testCompanyCreation() {
		String[] appleData = {"AAPL", "Large cap", "Information Technology", "Computers, Phones & Household Electronics", "2698909152580", "Cupertino, California", "1977", "-4.9300"};
		Company apple = new Company(appleData);
		
		Company appleGeneratedFromCSV = game.getAllCompanies().get("AAPL");
		
		assertTrue(apple.equals(appleGeneratedFromCSV));
	}
	
	/**
	 * Loading data of the S&P 500 (505 stocks) should create 505 Company objects
	 */
	@Test
	void testCorrectNumberOfCompaniesCreated() {
		int numCompaniesCreated = game.getAllCompanies().size();
		assertEquals(505,numCompaniesCreated, 1);
	}

}
