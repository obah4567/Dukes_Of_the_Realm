package Game;

public class Production {

	private String products;
	private long timeLeft;
	
	public Production(String prod, int time)
	{
		this.products = prod;
		this.timeLeft = time;
	}

	public String getProducts()
	{
		return this.products;
	}
	
	public long getTimeLeft()
	{
		return this.timeLeft;
	}
	
	public void setProducts(String products) {
		this.products = products;
	}

	public void setTimeLeft(long timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	
	
}
