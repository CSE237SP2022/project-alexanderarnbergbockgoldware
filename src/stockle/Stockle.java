package stockle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import company.Company;

public class Stockle {
	
	private HashMap<String, Company> allCompanies;
	private Company answer;
	private int guessNumber;
	
	public Stockle() {
		this.allCompanies = new HashMap<String, Company>();
		this.answer = null;
		this.guessNumber = 0;
	}
	
	public static void main(String[] args) {
		Stockle game = new Stockle();
		game.loadData();
		game.generateAnswer();
		game.displayInstructions();
		while (game.guessNumber < 6) {
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
			fileIn.close();
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
		System.out.println("How to Play:");
		System.out.println("Guess any company in the S&P 500 by their stock ticker (ex: Apple = AAPL)");
	}
	
	/**
	 * Prompt user for guess and initiate display of results
	 * @param args
	 */
	public void play(String[] args) {
		Stockle game = this;
		Scanner scanner = new Scanner(System.in);
		String userGuess = scanner.nextLine();
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
			game.guessNumber += 1;
			return true;
		}
		else {
			System.out.println("'" + userGuess + "' is not a valid S&P 500 stock ticker.");
			System.out.println("Stuck? Try again with AAPL, MSFT, or AMZN.");
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
		System.out.println("𝐛𝐨𝐥𝐝=𝐜𝐨𝐫𝐫𝐞𝐜𝐭; 𝘪𝘵𝘢𝘭𝘪𝘤𝘴=𝘤𝘭𝘰𝘴𝘦; regular=incorrect");
		System.out.println();
		compareIndustry(userGuessCompany, answer);
		compareMarketCaps(userGuessCompany, answer);
		compareSizes(userGuessCompany, answer);
		compareHeadquarters(userGuessCompany, answer);
		compareYearsFounded(userGuessCompany, answer);
		compareOneYearReturns(userGuessCompany, answer);
		return isCorrectAnswer(userGuessCompany, answer);
	}
	
	public boolean compareIndustry(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getSector().equals(answer.getSector())) {
			if (userGuessCompany.getIndustry().equals(answer.getIndustry())) {
				System.out.println(String.format("𝐈𝐧𝐝𝐮𝐬𝐭𝐫𝐲: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭! (%s)", userGuessCompany.getIndustry()));
				return true;
			} else {
				System.out.println(String.format("𝘐𝘯𝘥𝘶𝘴𝘵𝘳𝘺: 𝘎𝘦𝘵𝘵𝘪𝘯𝘨 𝘵𝘩𝘦𝘳𝘦! 𝘠𝘰𝘶'𝘷𝘦 𝘨𝘰𝘵 𝘵𝘩𝘦 𝘳𝘪𝘨𝘩𝘵 𝘴𝘦𝘤𝘵𝘰𝘳 𝘣𝘶𝘵 𝘵𝘩𝘦 𝘸𝘳𝘰𝘯𝘨 𝘪𝘯𝘥𝘶𝘴𝘵𝘳𝘺 (%s)", userGuessCompany.getIndustry()));
				return true;
			}
			}
		else {
			System.out.println(String.format("Industry: Keep trying! Wrong sector and industry... (%s)", userGuessCompany.getIndustry()));
			return false;
		}
	}
	
	public boolean compareMarketCaps(Company userGuessCompany, Company answer) {
		long fivePercentUp = (long) (answer.getMarketCap() * 1.05);
		long fivePercentDown = (long) (answer.getMarketCap() * 0.95);
		long oneBillion = 1000000000L;
		if (userGuessCompany.getMarketCap() == answer.getMarketCap()) {
			System.out.println(String.format("𝐌𝐚𝐫𝐤𝐞𝐭 𝐂𝐚𝐩: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭! ($%d billion)", userGuessCompany.getMarketCap()/oneBillion));
			return true;
		}
		if (fivePercentUp >= userGuessCompany.getMarketCap() && fivePercentDown <= userGuessCompany.getMarketCap()) {
			System.out.println(String.format("𝘔𝘢𝘳𝘬𝘦𝘵 𝘊𝘢𝘱: 𝘊𝘭𝘰𝘴𝘦! 𝘠𝘰𝘶𝘳 𝘨𝘶𝘦𝘴𝘴 𝘪𝘴 𝘸𝘪𝘵𝘩𝘪𝘯 5%% 𝘰𝘧 𝘵𝘩𝘦 𝘵𝘢𝘳𝘨𝘦𝘵 𝘤𝘰𝘮𝘱𝘢𝘯𝘺'𝘴 ($%d billion)!", userGuessCompany.getMarketCap()/oneBillion));
			return true;
		}
		else {
			System.out.println(String.format("Market Cap: Your guess is not within 5%% of the target... ($%d billion)", userGuessCompany.getMarketCap()/oneBillion));
			return false;
		}
	}
	
	public boolean compareSizes(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getSize().equals(answer.getSize())) {
			System.out.println(String.format("𝐒𝐢𝐳𝐞: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭! (%s)", userGuessCompany.getSize()));
			return true;
		}
		else {
			System.out.println(String.format("Size: Incorrect... (%s)", userGuessCompany.getSize()));
			return false;
		}
	}
	
	public boolean compareHeadquarters(Company userGuessCompany, Company answer) {
		if (userGuessCompany.getCountry().equals(answer.getCountry())) {
			if (userGuessCompany.getCountry().contains("United States")) {
				if (userGuessCompany.getHeadquarters().equals(answer.getHeadquarters())) {
					System.out.println(String.format("𝐇𝐞𝐚𝐝𝐪𝐮𝐚𝐫𝐭𝐞𝐫𝐬: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭 𝐜𝐢𝐭𝐲! (%s)", userGuessCompany.getHeadquarters()));
					return true;
				} else if (userGuessCompany.getState().equals(answer.getState())) {
					System.out.println(String.format("𝐇𝐞𝐚𝐝𝐪𝐮𝐚𝐫𝐭𝐞𝐫𝐬: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭 𝐬𝐭𝐚𝐭𝐞! (%s)", userGuessCompany.getState()));
					return true;
				} else {
					System.out.println(String.format("𝐇𝐞𝐚𝐝𝐪𝐮𝐚𝐫𝐭𝐞𝐫𝐬: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭 𝐜𝐨𝐮𝐧𝐭𝐫𝐲! (%s)", userGuessCompany.getCountry()));
					return true;
				}
			} else {
				System.out.println(String.format("𝐇𝐞𝐚𝐝𝐪𝐮𝐚𝐫𝐭𝐞𝐫𝐬: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭 𝐜𝐨𝐮𝐧𝐭𝐫𝐲! (%s)", userGuessCompany.getCountry()));
				return true;
			}
		}
		System.out.println(String.format("Headquarters: Wrong country... (%s)", userGuessCompany.getCountry()));
		return false;
		
	}
	
	public boolean compareYearsFounded(Company userGuessCompany, Company answer) {
		int upperBound = answer.getYearFounded() + 10;
		int lowerBound = answer.getYearFounded() - 10;
		if (userGuessCompany.getYearFounded() == answer.getYearFounded()) {
			System.out.println(String.format("𝐘𝐞𝐚𝐫 𝐅𝐨𝐮𝐧𝐝𝐞𝐝: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭! (%d)", userGuessCompany.getYearFounded()));
			return true;
		}
		if (upperBound >= userGuessCompany.getYearFounded() && lowerBound <= userGuessCompany.getYearFounded()) {
			System.out.println(String.format("𝘠𝘦𝘢𝘳 𝘍𝘰𝘶𝘯𝘥𝘦𝘥: 𝘊𝘭𝘰𝘴𝘦! 𝘞𝘪𝘵𝘩𝘪𝘯 10 𝘺𝘦𝘢𝘳𝘴 𝘰𝘧 𝘢𝘯𝘴𝘸𝘦𝘳! (%d)", userGuessCompany.getYearFounded()));
			return true;
		}
		else {
			System.out.println(String.format("Year Founded: 10+ years outside of target... (%d)", userGuessCompany.getYearFounded()));
			return false;
		}
	}
	
	public boolean compareOneYearReturns(Company userGuessCompany, Company answer) {
		int upperBound = (int) (answer.getOneYearReturn() + 10);
		int lowerBound = (int) (answer.getOneYearReturn() - 10);
		
		if (userGuessCompany.getOneYearReturn() == answer.getOneYearReturn()) {
			System.out.println(String.format("𝐎𝐧𝐞 𝐘𝐞𝐚𝐫 𝐑𝐞𝐭𝐮𝐫𝐧: 𝐂𝐨𝐫𝐫𝐞𝐜𝐭! (%.2f%%)", userGuessCompany.getOneYearReturn()));
			System.out.println(" ");
			return true;
		}
		if (upperBound >= userGuessCompany.getOneYearReturn() && lowerBound <= userGuessCompany.getOneYearReturn()) {
			System.out.println(String.format("𝘖𝘯𝘦 𝘠𝘦𝘢𝘳 𝘙𝘦𝘵𝘶𝘳𝘯: 𝘞𝘪𝘵𝘩𝘪𝘯 10%% 𝘰𝘧 𝘢𝘯𝘴𝘸𝘦𝘳! (%.2f%%)", userGuessCompany.getOneYearReturn()));
			System.out.println(" ");
			return true;
		}
		else {
			System.out.println(String.format("One Year Return: Incorrect... (%.2f%%)", userGuessCompany.getOneYearReturn()));
			System.out.println(" ");
			return false;
		}
	}
	
	public boolean isCorrectAnswer(Company userGuessCompany, Company answer) {
		Stockle game = this;
		if (userGuessCompany.getSymbol().equals(answer.getSymbol())) {
			System.out.println("Congratulations! You guessed the correct stock!");
			guessNumber = 6;
			System.out.println();
			return true;
		}
		else {
			System.out.println(userGuessCompany.getSymbol() + " was not correct...");
			System.out.println("Guesses Remaining: " + (5 - game.guessNumber));
			System.out.println("--------------------------------------------------------");
			System.out.println();
			if (game.guessNumber == 5) {
				System.out.println("Game Over!");
				System.out.println("The correct answer was " + answer.getSymbol());
				return true;
			}
			return false;
		}
	}
}
