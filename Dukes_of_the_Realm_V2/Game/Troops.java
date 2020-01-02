package Game;

public abstract class Troops {
	private int productionCost;
	private int timeCost;
	private int speed;
	private int health;
	private int dammages;
	
	public Troops(int productionCost, int timeCost, int speed, int health, int dammages)
	{
		this.productionCost = productionCost;
		this.timeCost = timeCost;
		this.speed = speed;
		this.health = health;
		this.dammages = dammages;
	}
	
	public int getProductionCost()
	{
		return this.productionCost;
	}

	public int getTimeCost() {
		return timeCost;
	}

	public int getSpeed() {
		return speed;
	}

	public int getHealth() {
		return health;
	}

	
	public int getDammages() {
		return dammages;
	}

	
	
}
