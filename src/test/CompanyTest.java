package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import company.Company;

class CompanyTest {
	
	private Company apple;
	
	@BeforeEach
	void setup() {
		String[] appleData = {"AAPL", "Large cap", "Information Technology", "Computers, Phones & Household Electronics", "2698910000000", "Cupertino", "1977", "-4.93", "United States", "California"};
		apple = new Company(appleData);
	}
	
	@Test
	void testGetSymbol() {
		String symbol = apple.getSymbol();
		assertTrue("AAPL".equals(symbol));
	}
	
	@Test
	void testGetSize() {
		String size = apple.getSize();
		assertTrue("Large cap".equals(size));
	}
	
	@Test
	void testGetSector() {
		String sector = apple.getSector();
		assertTrue("Information Technology".equals(sector));
	}
	
	@Test
	void testGetIndustry() {
		String industry = apple.getIndustry();
		assertTrue("Computers, Phones & Household Electronics".equals(industry));
	}
	
	@Test
	void testGetMarketCap() {
		long marketCap = apple.getMarketCap();
		assertEquals(2698910000000L, marketCap, 1);
	}
	
	@Test
	void testGetHeadquarters() {
		String headquarters = apple.getHeadquarters();
		assertTrue("Cupertino".equals(headquarters));
	}
	
	@Test
	void testGetYearFounded() {
		int yearFounded = apple.getYearFounded();
		assertEquals(1977, yearFounded, 0.1);
	}
	
	@Test
	void testGetOneYearReturn() {
		double oneYearReturn = apple.getOneYearReturn();
		assertEquals(-4.93, oneYearReturn, 0.01);
	}
	
	@Test
	void testGetCountry() {
		String country = apple.getCountry();
		assertTrue("United States".equals(country));
	}
	
	@Test
	void testGetState() {
		String state = apple.getState();
		assertTrue("California".equals(state));
	}

}
