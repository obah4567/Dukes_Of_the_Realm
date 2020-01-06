package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Troops {
	
	/*
	 *  layer is the Pane of the game
	 */
	protected Pane layer;
/*
 * is an int that describes the gold needed for the production of this Troops
 */
	protected int productionCost;
	
	/*
	 * is an int that describes the time needed for the production of this Troops
	 */
	protected int timeCost;
	
	/*
	 * is an int that describes how fast the Troops move
	 */
	protected int speed;
	
	/*
	 * is an int that the health of this Troops
	 */
	protected int health;
	
	/*
	 * is an int that describes the damages of this Troops
	 */
	protected int dammages;
	
	/*
	 * is the shape of this Troops on the pane
	 */
	protected Rectangle r;
	
	/*
	 * are the castle which where the Troops is from and where the Troops go to
	 */
	protected Castle target, src;
	
	//Constructor for attacking troops
	/*
	 * Construct a Troops,this constructor is used when a castle launches an attack
	 * Display its shape on the layer
	 * @param layer is the Pane which is exposed on the image
	 * @param productionCost is an int that describes the gold needed for the production of this Troops
	 * @param timeCost is an int that describes the time needed for the production of this Troops
	 * @param speed is an int that describes how fast the Troops move
	 * @param health is an int that the health of this Troops
	 * @param dammages is an int that describes the damages of this Troops
	 * @param src is a Castle which where the troops is from
	 * @param target is a Castle that is where the Troops go
	 */
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
	/*
	 * Construct a Troops, this constructor is used when a castle forms a defense Line
	 * @param productionCost is an int that describes the gold needed for the production of this Troops
	 * @param timeCost is an int that describes the time needed for the production of this Troops
	 * @param speed is an int that describes how fast the Troops move
	 * @param health is an int that the health of this Troops
	 * @param dammages is an int that describes the damages of this Troops
	 */
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
	
	/*
	 * removes the rectangle r from the layer
	 */
	public void removeFromLayer()
	{
		this.layer.getChildren().remove(this.r);
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
