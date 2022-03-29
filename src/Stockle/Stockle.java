package Stockle;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import support.cse131.ArgsProcessor;

import company.Company;

public class Stockle {
	
	private HashMap<String, Company> allCompanies;
	
	public Stockle() {
		this.allCompanies = new HashMap<String, Company>();
	}
	
	public static void main(String[] args) {
		Stockle game = new Stockle();
		game.loadData();
		instructions();
		play(args);
		
	}
	
	public static void instructions() {
		System.out.println("How to Play: "
				+ "Guess any company in the S&P 500 by their stock ticker (Apple = AAPL)");
		System.out.println("After guessing, you'll see the following characteristics with hints:");
		System.out.println("Industry: bold if same as answer, italics if in same sector but different industry");
		System.out.println("Headquarters: bold if same city, italics if same state");
		System.out.println("Market Cap: bold if within 5%, italics if within 15%");
		System.out.println("Size: bold if same, nothing if different");
		System.out.println("Year Founded: bold if same decade, italics if within three decades");
		System.out.println("One Year Return: bold if within 2%, italics if same sign (negative or positive)");
		
	}
	
	public static void play(String[] args) {
		ArgsProcessor ap = new ArgsProcessor (args);
		String userGuess = ap.nextString("Guess the Company's Ticker (All Caps)");
		print(userGuess);
	}
	
	public static void print(String userGuess) {
		Stockle game = new Stockle();
		
		game.loadData();
		
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
		
		System.out.println(" ");
		
		System.out.println("Industry: " + game.allCompanies.get(userGuess).getIndustry());
		System.out.println("Headquarters: " + game.allCompanies.get(userGuess).getHeadquarters());
		System.out.println("Market Cap: " + currencyFormatter.format(game.allCompanies.get(userGuess).getMarketCap()));
		System.out.println("Size: " + game.allCompanies.get(userGuess).getSize());
		System.out.println("Year Founded: " + game.allCompanies.get(userGuess).getYearFounded());
		System.out.println("One Year Return: " + game.allCompanies.get(userGuess).getOneYearReturn() + "%");

	}
	
	/**
	 * Load data of all companies from 'Stockle Data.csv'
	 */
	public void loadData() {
		File dataFile = new File("Stockle Data.csv");
		try {
			Scanner fileIn = new Scanner(dataFile, "UTF-8");
			fileIn.nextLine(); // skip first line of column labels
			while (fileIn.hasNextLine()) {
				createCompanyFromCsvLine(fileIn.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the Stockle Data CSV file...");
			e.printStackTrace();
		}
	}
	
	/**
	 * Convert one line of CSV company data into a Company object & store it within allCompanies
	 * @param line
	 */
	public void createCompanyFromCsvLine(String line) {
		// regex to split CSV lines comma-delimited array via https://stackoverflow.com/a/15979727
		String[] companyData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		removeExcessQuotatonMarks(companyData);
		Company newCompany = new Company(companyData);
		allCompanies.put(newCompany.getSymbol(), newCompany);
	}
	
	/**
	 * Remove any excess quotation marks from an array of CSV String data fields
	 * @param companyData
	 * @return
	 */
	public String[] removeExcessQuotatonMarks(String[] companyData) {
		for (int i=0; i<companyData.length; i++) { 
			companyData[i] = companyData[i].replace("\"", "");
		}
		return companyData;
	}
	
	public HashMap<String, Company> getAllCompanies() {
		return allCompanies;
	}

}
