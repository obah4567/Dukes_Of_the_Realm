package game;
/*
 * This file is part of Dukes_Of_the_Realm.
 * this class describes how a unit is produced
 */
import java.io.Serializable;

public class Production implements Serializable{
	
	/*
	 * products is a String that describe what is being produced
	 */
	private String products = "rien";
	
	/*
	 * timeLeft is an int that describes the time left until the production is complete
	 */
	private int timeLeft;
	
	/*
	 * Construct a new Production
	 * @param products is a String that describe what is being produced
	 * @param timeLeft is an int that describes the time left until the production is complete
	 */
	public Production(String prod, int time)
	{
		this.products = prod;
		this.timeLeft = time;
	}

	public String getProducts()
	{
		return this.products;
	}
	
	public int getTimeLeft()
	{
		return this.timeLeft;
	}
	
	/*
	 * @param this String must be either "Piquier" "Chevalier" "Onagre" or "level"
	 */
	public void setProducts(String products) {
		this.products = products;
	}
	
	/*
	 * @param timeLeft is an int that must be positiv or equal to 0
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public String toString()
	{
		return products + "|" + timeLeft;
	}
	
}
