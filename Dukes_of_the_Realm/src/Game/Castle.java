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
	
	private Troops troupes;

	//Constructors
	public Castle(Image img, Pane layer, ArrayList<Castle> ennemies, ArrayList<NeutralCastle> neutrals, Player player, ArrayList<Sprites> freeZones)
	{
		super(layer, img, ennemies, neutrals, player, freeZones);
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
        this.troops[0] = Settings.NB_PIK_DUC; this.troops[1] = Settings.NB_KNI_DUC; this.troops[2] = Settings.NB_ONA_DUC;
        this.treasure = 50;
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

	public void setProduction(Production production) {
		this.production = production;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}
	
	
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
