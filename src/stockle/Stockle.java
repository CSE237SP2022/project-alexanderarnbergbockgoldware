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
	private int hintCount = 0;
	
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
			game.displayHintPossibility();
			game.guessNumber += 1;
			return true;
		}
		else if (userGuess.equals("HINT") == true && hintCount == 10) {
			game.giveHint();
			return true;
		}
		
		else {
			System.out.println("'" + userGuess + "' is not a valid S&P 500 stock ticker.");
			System.out.println("Stuck? Try again with AAPL, MSFT, or AMZN.");
			return false;
		}
		
	}
	
	
	/**
	 * Display hint notification
	 */
	public void displayHintPossibility() {
		if (guessNumber == 2) {
			hintCount = 10;
			System.out.println("*****");
			System.out.println("If you need help, try \"HINT\"");
			System.out.println("*****");
		}
	}
	
	/**
	 * Give random list of stocks, one of which being the correct answer, as a hint
	 * 
	 * 
	 */
	public void giveHint() {
		//randomly generate ten stocks, one of which will be correct answer
		//may need to number the stocks in stock data, 1-500
		//or can convert hashmap to an array
		
		Object[] moveToArray = allCompanies.keySet().toArray();
		Company[] hints = new Company[9];
		Random rand = new Random();
		int randomIndex = rand.nextInt(10);
		hints[randomIndex] = answer;
		int i = 0;
		while(i<9) {
			
			if (i == randomIndex) {
				i++;
			}
			else {
				Random random = new Random();
				Object randomCompany = moveToArray[random.nextInt(moveToArray.length)];
				Company randomComp = allCompanies.get(randomCompany);
				hints[i] = randomComp;
				i++;
			}
		}
		System.out.println("Here is your list of hints:");
		System.out.println();
		for (Company c : hints) {
			System.out.println(c.getSymbol());
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
	
	public boolean compareMarketCaps(Company userGuessCompany, Company answer) {
		System.out.print("Market Cap: ");
		long fivePercentUp = (long) (answer.getMarketCap() * 1.05);
		long fivePercentDown = (long) (answer.getMarketCap() * 0.95);
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
			System.out.println("Correct size!");
			return true;
		}
		else {
			System.out.println("Incorrect size...");
			return false;
		}
	}
	
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
