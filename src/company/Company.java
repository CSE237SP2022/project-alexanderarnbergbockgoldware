package company;

import java.util.Objects;

/**
 * Data class that represents a company and its data (a singular line of 'Stockle Data.csv')
 * @author isaacbock
 *
 */
public class Company {
	
	private String symbol;
	private String size;
	private String sector;
	private String industry;
	private long marketCap;
	private String headquarters;
	private int yearFounded;
	private String country;
	private String state;

	private double oneYearReturn;
	
	public Company(String[] companyData) {
		this.symbol = companyData[0];
		this.size = companyData[1];
		this.sector = companyData[2];
		this.industry = companyData[3];
		this.marketCap = Long.parseLong(companyData[4]);
		this.headquarters = companyData[5];
		this.yearFounded = Integer.parseInt(companyData[6]);
		this.oneYearReturn = Double.parseDouble(companyData[7]);
		this.country = companyData[8];
		this.state = companyData[9];
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getSize() {
		return size;
	}
	
	public String getSector() {
		return sector;
	}
	
	public String getIndustry() {
		return industry;
	}
	
	public long getMarketCap() {
		return marketCap;
	}
	
	public String getHeadquarters() {
		return headquarters;
	}
	
	public int getYearFounded() {
		return yearFounded;
	}
	
	public double getOneYearReturn() {
		return oneYearReturn;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getState() {
		return state;
	}

	/**
	 * Auto-generated Company equality function (for use within unit testing)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(country, other.country) && Objects.equals(headquarters, other.headquarters)
				&& Objects.equals(industry, other.industry) && marketCap == other.marketCap
				&& Double.doubleToLongBits(oneYearReturn) == Double.doubleToLongBits(other.oneYearReturn)
				&& Objects.equals(sector, other.sector) && Objects.equals(size, other.size)
				&& Objects.equals(state, other.state) && Objects.equals(symbol, other.symbol)
				&& yearFounded == other.yearFounded;
	}
	
}
