package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.*;
public class Castle extends Sprites{

	//game structures
	/*
	 * String that contains the name of the duc
	 */
	protected String duc;
	
	/*
	 * int that contains the treasure or gold in the castle
	 */
	private int treasure;
	
	/*
	 * int that contains the level of the castle
	 */
	private int level;
	
	/*
	 * array of int, that content the number of troops in the castle
	 */
	private int troops[] = new int[3];
	
	/*
	 * Production contains the production produced in the castle
	 */
	private Production production;
	
	/*
	 * Order contains the order sent by the castle
	 */
	private Order order;
	
	/*
	 * String contains where the gate is
	 */
	private String gate;
	
	/*
	 * this ArrayList is instantiated in case of attacks against the castle.
	 * it contains the line of defense of the castle
	 */
	private ArrayList<Troops> def = new ArrayList<Troops>();

	//Constructors
	/*
	 * Construct a new Castle and display its image on the layer
	 * constructor available for enemies and neutrals
	 * it gives a random name to the castle and instantiate production and order
	 * @param img is the image that shall be exposed
	 * @param layer is the layer which is exposed on the image
	 * @param world is an arrayList that contains every castle
	 * 
	 */
	public Castle(Image img, Pane layer, ArrayList<Castle> world)
	{
		super(layer, img, world);
		Random r = new Random();
        
        //where is the gate
        this.gate = Settings.GATES[r.nextInt(4)];
        //army
        this.troops = new int[3];
        this.troops[0] = Settings.NB_PIK_DUC; this.troops[1] = Settings.NB_KNI_DUC; troops[2] = Settings.NB_ONA_DUC;
        
        this.treasure = 0;
        this.level = 1;
        //give a name
        this.duc = Settings.DUC_NAMES[r.nextInt(22)];
        this.order = new Order(0, 0, 0, null);
        this.production = new Production("rien", 0);
	}

	/*
	 * Construct a new Castle and display its image on the layer
	 * constructor available for the player
	 * it gives a random name to the castle and instantiate production and order
	 * @param img is the image that shall be exposed
	 * @param layer is the layer that is exposed on the image
	 * @param world is an arrayList that contains every castle
	 */
    public Castle(Image img, Pane layer, String duc)
	{
		super(layer, img);
		Random r = new Random();
        
        //where is the gate
        this.gate = Settings.GATES[r.nextInt(4)];
        //army
        this.troops = new int[3];
        this.troops[0] = Settings.NB_KNI_DUC; this.troops[1] = Settings.NB_KNI_DUC; this.troops[2] = Settings.NB_ONA_DUC;
        this.treasure = 0;
        this.level = 1;
        this.duc = duc;
        this.order = new Order(0, 0, 0, null);
        this.production = new Production("rien", 0);
	}


	//getters and setters

	public Pane getLayer() {
		return layer;
	}

	public String getDuc() {
		return duc;
	}

	public int getTreasure() {
		return treasure;
	}

	public int getLevel() {
		return level;
	}

	public int[] getTroops() {
		return troops;
	}

	public Production getProduction() {
		return production;
	}

	public Order getOrder() {
		return order;
	}

	public String getGate() {
		return gate;
	}
	public ArrayList<Troops> getDef()
	{
		return this.def;
	}

	public void setLayer(Pane layer) {
		this.layer = layer;
	}

	public void setDuc(String duc) {
		this.duc = duc;
	}

	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setTroops(int pik, int kni, int ona) {
		this.troops[0] = pik;
		this.troops[1] = kni;
		this.troops[2] = ona;
	}
	
	/*
	 * increments the number of pikemen or troops[0] by nb
	 * @param nb that increments troops[0]
	 */
	public void setTroops0(int nb)
	{
		this.troops[0] = this.troops[0] + nb;
	}
	
	/*
	 * increments the number of knights or troops[1] by nb
	 * @param nb that increments troops[1]
	 */
	public void setTroops1(int nb)
	{
		this.troops[1] = this.troops[1] + nb;
	}
	
	/*
	 * increments the number of onagers or troops[2] by nb
	 * @param nb that increments troops[2]
	 */
	public void setTroops2(int nb)
	{
		this.troops[2] = this.troops[2] + nb;
	}
	
	/*
	 * calculate the number total of troops inside the castle
	 * @return the total number of troops inside the castle
	 */
	public int totalTroops()
	{
		return troops[0] + troops[1] + troops[2];
	}
	
	/*
	 * instantiate an ArrayList<Troops> , it is an army that is summoned by the order, 
	 * it reduces the amount of troops in the castle by the numbers of the order
	 * it is displayed on the castle
	 * the last troop instantiated is always a Herald, it is a flag, that witness the end of the army, 
	 * that witness the order has been fulfilled
	 * @param target is the Castle troops' target
	 * @return an ArrayList<Troops> that contains the troops sent by the order to their target
	 */
	public ArrayList<Troops> instanceTroops(Castle target)
	{
		ArrayList<Troops> army = new ArrayList<Troops>();
		for (int i = 0; i < this.order.getNbOna(); i++)
		{
			army.add(new Onager(layer, this, target));
			army.get(i).getRectangle().setX(this.getDx() - 4);
			army.get(i).getRectangle().setY(this.getDy());
		}
		int size = army.size();
		for (int i = 0; i < this.order.getNbKni(); i++)
		{
			army.add(new Knights(layer, this, target));
			army.get(i + size).getRectangle().setX(this.getDx() + 4);
			army.get(i + size).getRectangle().setY(this.getDy());
		}
		size = army.size();
		for (int i = 0; i < this.order.getNbPyk(); i++)
		{
			army.add(new Pikeman(layer, this, target));
			army.get(i + size).getRectangle().setX(this.getDx());
			army.get(i + size).getRectangle().setY(this.getDy() + 4);
		}
		
		this.troops[0] = this.troops[0] - getOrder().getNbPyk();
		this.troops[1] = this.troops[1] - getOrder().getNbKni();
		this.troops[2] = this.troops[2] - getOrder().getNbOna();
		
		army.add(new Herald(layer, this, target, army.get(0).getSpeed()));
		army.get(army.size() - 1).getRectangle().setX(this.getDx());
		army.get(army.size() - 1).getRectangle().setY(this.getDy() - 4);
		
		return army;
		
	}
	
	/*
	 * this forms a line of defense in case the castle is under attack, 
	 * its size is if possible and max 10
	 * it decreases the number of troops from the castle by the number of units 
	 * forming the line of defense and adds corresponding troops in def
	 * @return true if the ArrayList def is empty, if
	 * @return false if def is not empty
	 */
	public boolean defend() // Mise en place de l'unité de défense : 6/3/2
	{
		for (int i = 0; i < troops[0] && i < 6; i++)
		{
			def.add(new Pikeman());
			this.troops[0]--;
		}
		for (int i = 0; i < troops[1] && i < 3; i++)
		{
			def.add(new Knights());
			this.troops[1]--;
		}
		if (troops[2] > 0)
		{
			def.add(new Onager());
			this.troops[2]--;
		}
		if (def.size() < 10)
		{
			boolean canAddTroops = true;
			while (canAddTroops)
			{
				if (troops[0] > 0)
				{
					def.add(new Pikeman());
					this.troops[0]--;
				}
				else
				{
					if (troops[1] > 0)
					{
						def.add(new Knights());
						this.troops[1]--;
					}
					else
					{
						if (troops[2] > 0)
						{
							def.add(new Onager());
							this.troops[2]--;
						}
						else
							canAddTroops = false;
					}
				}
			}
		}
		return def.isEmpty();
	}
	
	/*
	 * an order is sent if the values are correct
	 * @param target is the target of the castle
	 * @param nbPyk is the number of pikemen sent in the order
	 * @param nbKni is the number of knights sent in the order
	 * @param nbOna is the number of onagers sent in the order
	 */
	public void sendOrder(Castle target, int nbPyk, int nbKni, int nbOna)
	{
		if (nbPyk != 0 || nbKni != 0 || nbOna != 0)
		{
			this.order.setNbPyk(nbPyk);
			this.order.setNbKni(nbKni);
			this.order.setNbOna(nbOna);
			this.order.setTarget(target);
		}
	}
	
	/*
	 *  add the remaining troops from def to the array of int troops[]
	 *  clear the ArrayList def
	 *  used when an attack is over
	 */
	public void clearDefense()
	{
		for (int i = 0; i < def.size(); i ++)
		{
			if (def.get(i).getClass() == Pikeman.class)
				troops[0]++;
			if (def.get(i).getClass() == Knights.class)
				troops[1]++;
			if (def.get(i).getClass() == Onager.class)
				troops[2]++;
		}
		def.clear();
	}
	
	/*
	 * updates production. If the castle is producing nothing, it changes
	 * its production from "rien" to the right thing, then it sets the 
	 * number of time left right.
	 * @param unit is the unit that shall be produced at the end of the production
	 * @param timeLeft is the time left before the unit is formed
	 * @return false if the unit can't be produced
	 * @return true if the unit can be produced
	 */
	public boolean product(String unit, int timeLeft)
	{
		if (production.getProducts().equals("rien"))
		{
			switch (unit)
			{
			case "Piquier" :
					if (treasure < Settings.COST_PRODUCTION_PIKEMAN)
						return false;
					treasure = treasure - Settings.COST_PRODUCTION_PIKEMAN;
					break;
			case "Chevalier" :
					if (treasure < Settings.COST_PRODUCTION_KNIGHT)
						return false;
					treasure = treasure - Settings.COST_PRODUCTION_KNIGHT;
					break;
			case "Onagre" :
					if (treasure < Settings.COST_PRODUCTION_ONAGER)
						return false;
					treasure = treasure - Settings.COST_PRODUCTION_ONAGER;
					break;
			case "level" :
					if (treasure < (getLevel() + 1) * 1000)
						return false;
					treasure = treasure - (getLevel() + 1)*1000;
					break;
			default :
				return false;
			}
			getProduction().setProducts(unit);
			getProduction().setTimeLeft(timeLeft);
			return true;	
		}
		else
			return false;
	}
}
