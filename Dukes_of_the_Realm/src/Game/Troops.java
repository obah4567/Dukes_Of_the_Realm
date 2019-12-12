package Game;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Troops {
	protected Pane layer;
	protected int productionCost;
	protected int timeCost;
	protected int speed;
	protected int health;
	protected int dammages;
	protected Rectangle r;

	
	public Troops(Pane layer, int productionCost, int timeCost, int speed, int health, int dammages)
	{
		this.layer = layer;
		this.productionCost = productionCost;
		this.timeCost = timeCost;
		this.speed = speed;
		this.health = health;
		this.dammages = dammages;
		this.r = new Rectangle(4, 4, Color.BLACK);
		layer.getChildren().add(r);
	}
	
	public Troops( int productionCost, int timeCost, int speed, int health, int dammages)
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
	
	public void setHealth(int h)
	{
		this.health = this.health - h;
	}

	public Rectangle getRectangle()
	{
		return this.r;
	}
	
}
