package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;
public class Castle extends Sprites{

	//game structures
	protected String duc;
	private int treasure;
	private int level;
	private int troops[];
	private Production production;
	private Order order;
	private char gate;
	//
	private Troops troupes;

	//Constructors
	public Castle(Image img, Pane layer, Castle[] ennemies, int nbEnnemies, Castle[] neutrals, int nbNeutrals, Player player)
	{
		super(layer, img, ennemies, nbEnnemies, neutrals, nbNeutrals, player);
		Random r = new Random();
        
        //where is the gate
        this.gate = Settings.GATES[r.nextInt(4)];
        //army
        this.troops = new int[3];
        this.troops[0] = 0; this.troops[1] = 0; troops[2] = 0;
        
        this.treasure = 0;
        this.level = 1;
        //give a name
        this.duc = Settings.DUC_NAMES[r.nextInt(22)];
	}

    public Castle(Image img, Pane layer, String duc)
	{
		super(layer, img);
		Random r = new Random();
        
        //where is the gate
        this.gate = Settings.GATES[r.nextInt(4)];
        //army
        this.troops = new int[3];
        this.troops[0] = 0; 
        this.troops[1] = 0; 
        troops[2] = 0;
        this.treasure = 0;
        this.level = 1;
        this.duc = duc;
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

	public char getGate() {
		return gate;
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

	public void setTroops(int[] troops) {
		this.troops = troops;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setGate(char gate) {
		this.gate = gate;
	}

	///////
	
	public boolean prod_Cours() {
		long tours = production.getTimeLeft()-1;
		int cout = troupes.getProductionCost();
		
		return ((production.getTimeLeft() > 0 || tours >= 0) && cout >=0);
	}
	
	public long revenuCastel() {
		
		return (getLevel()* 10) / production.getTimeLeft();
	}
	
	public void prod() {
		
		Troops[] troope = new Troops[100];
		ArrayList<Troops> troupe = new ArrayList<Troops>();
		
		long tours = production.getTimeLeft();
		int cout_de_Prod = troupes.getProductionCost();
		
		if (tours > 0) {
			revenuCastel();
			tours -=1;
		}
		cout_de_Prod += 1; 
		
		if (production.getTimeLeft() >= 5  && troupes.getProductionCost() >= 100) {
			
			for (int i = 0; i< troupes.getProductionCost(); i=i+100) {
				
				if (troupes.getProductionCost() >= 100 && troupes.getProductionCost() < 500) {
					troope[i] = new Pikeman();
				}
				
				if (troupes.getProductionCost() >= 500 && troupes.getProductionCost() < 1000) {
					troope[i] = new Knights();
				}
				if (troupes.getProductionCost() >= 1000) {
					troope[i] = new Onager();
				}
				//tours = tours -1;
				
			}
			
		}
	}


}
