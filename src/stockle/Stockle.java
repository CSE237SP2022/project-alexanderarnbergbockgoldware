package stockle;

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
	private Company answer;
	
	public Stockle() {
		this.allCompanies = new HashMap<String, Company>();
		this.answer = null;
	}
	
	public static void main(String[] args) {
		Stockle game = new Stockle();
		game.loadData();
		game.generateAnswer();
		game.displayInstructions();
		game.play(args);
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
	
	/**
	 * 
	 * @return Saved data of allCompanies
	 */
	public HashMap<String, Company> getAllCompanies() {
		return allCompanies;
	}
	
	public void setAnswer(Company answer) {
		this.answer = answer;
	}
	
	/**
	 * 	
	 * @return Randomly generated answer/Company
	 */
	public Company generateAnswer() {
		// random HashMap element selection via https://crunchify.com/java-how-to-get-random-key-value-element-from-hashmap/
		Object[] allKeys = allCompanies.keySet().toArray();
		Object randomKey = allKeys[new Random().nextInt(allKeys.length)];
		answer = allCompanies.get(randomKey);
		return answer;
	}
	
	/**
	 * Print out game instructions
	 */
	public void displayInstructions() {
		System.out.println("How to Play: "
				+ "Guess any company in the S&P 500 by their stock ticker (ex: Apple = AAPL)");
//		Future instructions for once we eventually format the terminal output and need to provide more details:
//		System.out.println("After guessing, you'll see the following characteristics with hints:");
//		System.out.println("Industry: bold if same as answer, italics if in same sector but different industry");
//		System.out.println("Headquarters: bold if same city, italics if same state");
//		System.out.println("Market Cap: bold if within 5%, italics if within 15%");
//		System.out.println("Size: bold if same, nothing if different");
//		System.out.println("Year Founded: bold if same decade, italics if within three decades");
//		System.out.println("One Year Return: bold if within 2%, italics if same sign (negative or positive)");
	}
	
	/**
	 * Prompt user for guess and initiate display of results
	 * @param args
	 */
	public void play(String[] args) {
		Stockle game = this;
		ArgsProcessor ap = new ArgsProcessor(args);
		String userGuess = ap.nextString("Guess any company in the S&P 500 by their stock ticker (ex: Apple = AAPL)");
		userGuess = userGuess.toUpperCase();
		game.printResult(userGuess);
	}
	
	/**
	 * Print out results of the user's guess based on whether it's valid or not
	 * @param userGuess
	 * @return if this guess is valid or not
	 */
	public boolean printResult(String userGuess) {
		Stockle game = this;
		if (allCompanies.containsKey(userGuess)) {
			Company userGuessCompany = allCompanies.get(userGuess);
			game.compareGuessToAnswer(userGuessCompany);
			return true;
		}
		else {
			System.out.println("'" + userGuess + "' is not a valid S&P 500 stock ticker.");
			System.out.println("Stuck? Try playing again with AAPL, MSFT, or AMZN.");
			return false;
		}
		
	}
	
	/**
	 * Print out a full comparison of the guess to the answer across all data attributes
	 * @param userGuessCompany
	 * @return if this guess was correct or incorrect
	 */
	public boolean compareGuessToAnswer(Company userGuessCompany) {
		//compare each attribute of the guess to the attribute of the correct answer
		System.out.println("You guessed " + userGuessCompany.getSymbol() + ".");
		System.out.println("————————————————————");
		compareSectors(userGuessCompany, answer);
		System.out.println("————————————————————");
		compareIndustries(userGuessCompany, answer);
		System.out.println("————————————————————");
		compareMarketCaps(userGuessCompany, answer);
		System.out.println("————————————————————");
		compareSizes(userGuessCompany, answer);
		System.out.println("————————————————————");
		compareHeadquarters(userGuessCompany, answer);
		System.out.println("————————————————————");
		compareYearsFounded(userGuessCompany, answer);
		System.out.println("————————————————————");
		compareOneYearReturns(userGuessCompany, answer);
		System.out.println("————————————————————");
		return isCorrectAnswer(userGuessCompany, answer);
	}
	public boolean compareSectors(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getSector().equals(answer.getSector())) {
			System.out.println("Sector: "+ answer.getSector());
			System.out.println("Correct sector!");
			return true;
		}
		else {
			System.out.println("Sector: "+ userGuessCompany.getSector());
			System.out.println("Incorrect sector...");
			return false;
		}
	}
	public boolean compareIndustries(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getIndustry().equals(answer.getIndustry())) {
			System.out.println("Industry: "+ answer.getIndustry());
			System.out.println("Correct industry!");
			return true;
		}
		else {
			System.out.println("Industry: "+ userGuessCompany.getIndustry());
			System.out.println("Incorrect industry...");
			return false;
		}
	}
	public boolean compareMarketCaps(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getMarketCap() == answer.getMarketCap()) {
			System.out.println("Market Cap: "+ answer.getMarketCap());
			System.out.println("Correct market cap!");
			return true;
		}
		else {
			System.out.println("Market Cap: "+ userGuessCompany.getMarketCap());
			System.out.println("Incorrect market cap...");
			return false;
		}
	}
	public boolean compareSizes(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getSize().equals(answer.getSize())) {
			System.out.println("Size: "+ answer.getSize());
			System.out.println("Correct size!");
			return true;
		}
		else {
			System.out.println("Size: "+ userGuessCompany.getSize());
			System.out.println("Incorrect size...");
			return false;
		}
	}
	public boolean compareHeadquarters(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getHeadquarters().equals(answer.getHeadquarters())) {
			System.out.println("HQ Location: "+ answer.getHeadquarters());
			System.out.println("Correct headquarters!");
			return true;
		}
		else {
			System.out.println("HQ Location: "+ userGuessCompany.getHeadquarters());
			System.out.println("Incorrect headquarters...");
			return false;
		}
	}
	public boolean compareYearsFounded(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getYearFounded() == answer.getYearFounded()) {
			System.out.println("Year Founded: "+ answer.getYearFounded());
			System.out.println("Correct year founded!");
			return true;
		}
		else {
			System.out.println("Year Founded: "+ userGuessCompany.getYearFounded());
			System.out.println("Incorrect year founded...");
			return false;
		}
	}
	public boolean compareOneYearReturns(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getOneYearReturn() == answer.getOneYearReturn()) {
			System.out.println("One Year Return: "+ answer.getOneYearReturn());
			System.out.println("Correct one year return!");
			return true;
		}
		else {
			System.out.println("One Year Return: "+ userGuessCompany.getOneYearReturn());
			System.out.println("Incorrect one year return...");
			return false;
		}
	}
	public boolean isCorrectAnswer(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getSymbol().equals(answer.getSymbol())) {
			System.out.println("Congratulations! You guessed the correct stock!");
			return true;
		}
		else {
			System.out.println(userGuessCompany.getSymbol() + " was not correct...");
			System.out.println("The correct answer was " + answer.getSymbol() + ".");
			return false;
		}
	}
}
