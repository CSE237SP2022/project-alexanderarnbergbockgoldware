package Stockle;

import java.io.File;
import java.io.FileNotFoundException;
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
		
		ArgsProcessor ap = new ArgsProcessor (args);

		String userGuess = ap.nextString("Guess the Company's Ticker");
		System.out.println(game.allCompanies.get(userGuess).getIndustry());
		// To-do:
		// game.randomlySelectAnswer();
		// game.printPlayingInstructions();
		// game.play();
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
