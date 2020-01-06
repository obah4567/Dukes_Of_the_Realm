package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
import java.io.Serializable;

public class Production {

	private String products = "rien";
	private int timeLeft;
	
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
	
	public void setProducts(String products) {
		this.products = products;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	
	
}
