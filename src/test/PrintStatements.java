package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Stockle.Stockle;
import company.Company;

class PrintStatements {
	
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
