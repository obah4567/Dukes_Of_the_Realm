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
	protected double a = 0, b = 0;
	protected Castle target, src;
	
	//Constructor for attacking troops
	public Troops(Pane layer, int productionCost, int timeCost, int speed, int health, int dammages, Castle src , Castle target)
	{
		this.layer = layer;
		this.productionCost = productionCost;
		this.timeCost = timeCost;
		this.speed = speed;
		this.health = health;
		this.dammages = dammages;
		this.r = new Rectangle(4, 4, Color.BLACK);
		layer.getChildren().add(r);
		this.src = src;
		this.target = target;
	}
	//Constructor for defending troops
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
	public void removeFromLayer()
	{
		this.layer.getChildren().remove(this.r);
	}
	public double getA()
	{
		return this.a;
	}
	public double getB()
	{
		return this.b;
	}
	public void setA(double n)
	{
		a = n;
	}
	public void setB(double n)
	{
		b = n;
	}
	public Castle getTarget()
	{
		return this.target;
	}
	public void setTarget(Castle target)
	{
		this.target = target;
	}
	public Castle getSrc()
	{
		return this.src;
	}
	public void setSrc(Castle c)
	{
		this.src = c;
	}
}
