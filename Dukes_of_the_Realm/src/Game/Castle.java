package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Random;
public class Castle extends Sprites{

	//game structures
	protected String duc;
	private int treasure;
	private int level;
	private int troops[];
	private Production production;
	private Order order;
	private char gate;

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



}
