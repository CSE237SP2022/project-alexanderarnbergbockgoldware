package Stockle;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import support.cse131.ArgsProcessor;

import company.Company;

public class Stockle {
	
	private HashMap<String, Company> allCompanies;
	
	private static Company answer; //steve new
	
	
	public Stockle() {
		this.allCompanies = new HashMap<String, Company>();
		this.answer = null;
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
		userGuess.toUpperCase();
		print(userGuess);
	}
	
	public static void print(String userGuess) {
		Stockle game = new Stockle();
		
		game.loadData();
		
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
		
		
		Company userGuessCompany = game.allCompanies.get(userGuess);
		
		Comparison(userGuessCompany);
		
		System.out.println(" ");
		
//		System.out.println("Industry: " + game.allCompanies.get(userGuess).getIndustry());
//		System.out.println("Headquarters: " + game.allCompanies.get(userGuess).getHeadquarters());
//		System.out.println("Market Cap: " + currencyFormatter.format(game.allCompanies.get(userGuess).getMarketCap()));
//		System.out.println("Size: " + game.allCompanies.get(userGuess).getSize());
//		System.out.println("Year Founded: " + game.allCompanies.get(userGuess).getYearFounded());
//		System.out.println("One Year Return: " + game.allCompanies.get(userGuess).getOneYearReturn() + "%");

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
	
	public Company answerGenerator() {
		// random HashMap element selection via https://crunchify.com/java-how-to-get-random-key-value-element-from-hashmap/
		Object[] allKeys = allCompanies.keySet().toArray();
		Object randomKey = allKeys[new Random().nextInt(allKeys.length)];
		answer = allCompanies.get(randomKey);
		System.out.println("Answer: " + answer.getSymbol());
		
		return answer;
	}
	
	public static void Comparison(Company userGuessCompany) {
		//compare each attribute of the guess to the attribute of the correct answer
		//if they are the same then bold? Not sure if we can bold in Java sysout
		
		if (userGuessCompany.getSymbol() == answer.getSymbol()) {
			System.out.println(answer.getSymbol());
			
		}
		else {
			System.out.println(userGuessCompany.getSymbol());
		}
		
		if (userGuessCompany.getSector() == answer.getSector()) {
			System.out.println("Sector: "+ answer.getSector());
			
			System.out.println("Correct Sector!");
		}
		else {
			System.out.println("Sector: "+ userGuessCompany.getSector());
		}
		
		if (userGuessCompany.getIndustry() == answer.getIndustry()) {
			System.out.println("Industry: "+ answer.getIndustry());
			System.out.println("Correct Industry!");
		}
		else {
			System.out.println("Industry: "+ userGuessCompany.getIndustry());
		}
		if (userGuessCompany.getMarketCap() == answer.getMarketCap()) {
			//sys print, if within a billion then bold if within 10 billion italicize; maybe percentage instead?
			
			System.out.println("Market Cap: "+ answer.getMarketCap());
			
			System.out.println("Correct Market Cap!");
		}
		else {
			System.out.println("Market Cap: "+ userGuessCompany.getMarketCap());
		}
		if (userGuessCompany.getSize() == answer.getSize()) {
			System.out.println("Size: "+ answer.getSize());
			
			System.out.println("Correct Size!");
		}
		else {
			System.out.println("Size: "+ userGuessCompany.getSize());
		}
		if (userGuessCompany.getHeadquarters() == answer.getHeadquarters()) {
			System.out.println("HQ Location: "+ answer.getHeadquarters());
			
			System.out.println("Correct Headquarters!");
		}
		else {
			System.out.println("HQ Location: "+ userGuessCompany.getHeadquarters());
		}
		if (userGuessCompany.getYearFounded() == answer.getYearFounded()) {
			System.out.println("Year Founded: "+ answer.getYearFounded());
			
			System.out.println("Correct Year Founded!");
		}
		else {
			System.out.println("Year Founded: "+ userGuessCompany.getYearFounded());
		}
		if (userGuessCompany.getOneYearReturn() == answer.getOneYearReturn()) {
			System.out.println("One Year Return: "+ answer.getOneYearReturn());
			System.out.println("Correct One Year Return!");
		}
		else {
			System.out.println("One Year Return: "+ userGuessCompany.getOneYearReturn());
		}
		
		if (userGuessCompany.getSymbol() == answer.getSymbol() && userGuessCompany.getSector() == answer.getSector()) {
			System.out.println("Congratulations! You guessed the correct stock!");
		}
	}
	
	
	
	

}
