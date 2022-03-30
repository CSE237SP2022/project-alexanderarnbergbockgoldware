package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import company.Company;
import stockle.Stockle;

class PrintStatementsTest {
	
	// Print statement unit testing inspired by and modified from https://stackoverflow.com/a/1119559
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream ogOut = System.out;
	private final PrintStream ogErr = System.err;
	
	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	
	@Test
	public void out() {
		System.out.print("AAPL");
		assertEquals("AAPL",outContent.toString());
	}
	
	public void err() {
		System.err.print("APPL");
		assertEquals("APPL",errContent.toString());
	}
	
	@AfterEach
	public void restore() {
		System.setOut(ogOut);
		System.setErr(ogErr);
	}
	
}
