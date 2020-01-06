package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.*;
public class Castle extends Sprites{

	//game structures
	protected String duc;
	private int treasure;
	private int level;
	private int troops[] = new int[3];
	private Production production;
	private Order order;
	private String gate;
	private ArrayList<Troops> def = new ArrayList<Troops>();

	//Constructors
	/*
	 * Castle constructor for every castle
	 * @param img is the image that shall be exposed
	 * @param layer is the layer that is exposed on the image
	 * 
	 * return a
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


	//Mutators
	public ImageView getImgView() {
		return imgView;
	}

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
	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
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
	
	public void setTroops0(int nb)
	{
		this.troops[0] = this.troops[0] + nb;
	}
	
	public void setTroops1(int nb)
	{
		this.troops[1] = this.troops[1] + nb;
	}
	
	public void setTroops2(int nb)
	{
		this.troops[2] = this.troops[2] + nb;
	}
	
	public void setProduction(Production production) {
		this.production = production;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}
	public int totalTroops()
	{
		return troops[0] + troops[1] + troops[2];
	}
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
