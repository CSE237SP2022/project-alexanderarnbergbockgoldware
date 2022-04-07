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
	int guesses = 0;
	
	public Stockle() {
		this.allCompanies = new HashMap<String, Company>();
		this.answer = null;
	}
	
	
	public static void main(String[] args) {
		Stockle game = new Stockle();
		game.loadData();
		game.generateAnswer();
		game.displayInstructions();
		int gamesPlayed = 0;
		
		while (game.guesses < 6) {
			game.play(args);
		}
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
		
		System.out.println();
		
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
		
//		game.guesses += 1;
		
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
			game.guesses += 1;
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
		System.out.println("--------------------------------------------------------");
		System.out.println("You guessed " + userGuessCompany.getSymbol() + ".");
		System.out.println();
		compareIndustry(userGuessCompany, answer);
		compareMarketCaps(userGuessCompany, answer);
		compareSizes(userGuessCompany, answer);
		compareHeadquarters(userGuessCompany, answer);
		compareYearsFounded(userGuessCompany, answer);
		compareOneYearReturns(userGuessCompany, answer);
		return isCorrectAnswer(userGuessCompany, answer);
	}
	
//	public boolean compareSectors(Company userGuessCompany, Company answer) {
//		if (userGuessCompany.getSector().equals(answer.getSector())) {
//			System.out.println("Sector: "+ answer.getSector());
//			System.out.println("Correct sector!");
//			return true;
//		}
//		else {
//			System.out.println("Sector: "+ userGuessCompany.getSector());
//			System.out.println("Incorrect sector...");
//			return false;
//		}
//	}
//	public boolean compareIndustries(Company userGuessCompany, Company answer) {
//		if (userGuessCompany.getIndustry().equals(answer.getIndustry())) {
//			System.out.println("Industry: "+ answer.getIndustry());
//			System.out.println("Correct industry!");
//			return true;
//		}
//		else {
//			System.out.println("Industry: "+ userGuessCompany.getIndustry());
//			System.out.println("Incorrect industry...");
//			return false;
//		}
//	}
	
	public boolean compareIndustry(Company userGuessCompany, Company answer) {
		System.out.print("Industry: ");
		if (userGuessCompany.getSector().equals(answer.getSector())) {
			if (userGuessCompany.getIndustry().equals(answer.getIndustry())) {
				System.out.println("Your guess is in the same industry as the target");
				return true;
			} else {
				System.out.println("Getting there! You've got the right sector but the wrong industry");
				return true;
			}
			}
		else {
			System.out.println("Keep trying! Wrong sector");
			return false;
		}
	}
	
//	public boolean compareMarketCaps(Company userGuessCompany, Company answer) {
//		if (userGuessCompany.getMarketCap() == answer.getMarketCap()) {
//			System.out.println("Market Cap: $"+ answer.getMarketCap());
//			System.out.println("Correct market cap!");
//			return true;
//		}
//		else {
//			System.out.println("Market Cap: "+ userGuessCompany.getMarketCap());
//			System.out.println("Incorrect market cap...");
//			return false;
//		}
//	}
	
	public boolean compareMarketCaps(Company userGuessCompany, Company answer) {
		System.out.print("Market Cap: ");
		long fivePercentUp = Math.multiplyExact(answer.getMarketCap(), (int) 1.05);
		long fivePercentDown = Math.multiplyExact(answer.getMarketCap(), (int) 0.95);
		if (userGuessCompany.getMarketCap() == answer.getMarketCap()) {
			System.out.println("Correct market cap!");
			return true;
		}
		if (fivePercentUp >= userGuessCompany.getMarketCap() && fivePercentDown <= userGuessCompany.getMarketCap()) {
			System.out.println("Your guess is within 5% of the target company's!");
			return true;
		}
		else {
			System.out.println("Your guess is not within 5% of the target company's market cap");
			return false;
		}
	}
	
	public boolean compareSizes(Company userGuessCompany, Company answer) {
		System.out.print("Size: ");
		if (userGuessCompany.getSize().equals(answer.getSize())) {
//			System.out.println("Size: "+ answer.getSize());
			System.out.println("Correct size!");
			return true;
		}
		else {
//			System.out.println("Size: "+ userGuessCompany.getSize());
			System.out.println("Incorrect size...");
			return false;
		}
	}
	
//	public boolean compareHeadquarters(Company userGuessCompany, Company answer) {
//		if (userGuessCompany.getHeadquarters().equals(answer.getHeadquarters())) {
//			System.out.println("HQ Location: "+ answer.getHeadquarters());
//			System.out.println("Correct headquarters!");
//			return true;
//		}
//		else {
//			System.out.println("HQ Location: "+ userGuessCompany.getHeadquarters());
//			System.out.println("Incorrect headquarters...");
//			return false;
//		}
//	}
	
	public boolean compareHeadquarters(Company userGuessCompany, Company answer) {
		System.out.print("Headquarters: ");
		if (userGuessCompany.getCountry().equals(answer.getCountry())) {
			if (userGuessCompany.getCountry().contains("United States")) {
				if (userGuessCompany.getHeadquarters().equals(answer.getHeadquarters())) {
					System.out.println("Correct city!");
					return true;
				} else if (userGuessCompany.getState().equals(answer.getState())) {
					System.out.println("Correct state!");
					return true;
				} else {
					System.out.println("Correct country!");
					return true;
				}
			} else {
				System.out.println("Correct country!");
				return true;
			}
		}
		System.out.println("Wrong country!");
		return false;
		
		}
//			if (userGuessCompany.getCountry().contains("United States")) {
//				if (userGuessCompany.getState().equals(answer.getState())) {
//					System.out.println("You got the correct state!");
//					return true;
//				} else if (userGuessCompany.getHeadquarters().equals(answer.getHeadquarters())) {
//					System.out.println("You got the correct city!");
//					return true;
//				}
//			} else {
//				System.out.println("You got the correct (non-US) country!");
//				return true;
//			} 
//		} else {
//			System.out.println("Target company is in a different country!");
//			return false;
//		}
//		System.out.println("here");
//		return false;
//	}
	
	public boolean compareYearsFounded(Company userGuessCompany, Company answer) {
		System.out.print("Year Founded: ");
		int upperBound = answer.getYearFounded() + 10;
		int lowerBound = answer.getYearFounded() - 10;
		if (userGuessCompany.getYearFounded() == answer.getYearFounded()) {
			System.out.println("Correct year founded!");
			return true;
		}
		if (upperBound >= userGuessCompany.getYearFounded() && lowerBound <= userGuessCompany.getYearFounded()) {
			System.out.println("Guess is within 10 years of answer!");
			return true;
		}
		else {
			System.out.println("Guess was founded outside of 10 years near target");
			return false;
		}
	}
	
	public boolean compareOneYearReturns(Company userGuessCompany, Company answer) {
		System.out.print("One Year Return: ");
		int upperBound = (int) (answer.getOneYearReturn() + 10);
		int lowerBound = (int) (answer.getOneYearReturn() - 10);
		
		if (userGuessCompany.getOneYearReturn() == answer.getOneYearReturn()) {
			System.out.println("Correct one year return!");
			System.out.println(" ");
			return true;
		}
		if (upperBound >= userGuessCompany.getOneYearReturn() && lowerBound <= userGuessCompany.getOneYearReturn()) {
			System.out.println("Guess is within 10% answer!");
			System.out.println(" ");
			return true;
		}
		else {
			System.out.println("Incorrect one year return...");
			System.out.println(" ");
			return false;
		}
	}
	
	public boolean isCorrectAnswer(Company userGuessCompany, Company answer) {
		Stockle game = this;
		if (userGuessCompany.getSymbol().equals(answer.getSymbol())) {
			System.out.println("Congratulations! You guessed the correct stock!");
			
			guesses = 6;
			
			System.out.println();
			return true;
		}
		else {
			System.out.println(userGuessCompany.getSymbol() + " was not correct...");
			System.out.println("Guesses Remaining: " + (5 - game.guesses));
			
//			System.out.println("The correct answer was " + answer.getSymbol() + ".");
			System.out.println("--------------------------------------------------------");
			System.out.println();
			if (game.guesses == 5) {
				System.out.println("Game Over!");
				System.out.println("The correct answer was " + answer.getSymbol());
				return true;
			}
			return false;
		}
	}
}
