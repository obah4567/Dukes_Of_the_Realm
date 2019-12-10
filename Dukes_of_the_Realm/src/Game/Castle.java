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

	//Constructors
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
	public ArrayList<Troops> instanceTroops()
	{
		this.troops[0] = this.troops[0] - getOrder().getNbPyk();
		this.troops[1] = this.troops[1] - getOrder().getNbKni();
		this.troops[2] = this.troops[2] - getOrder().getNbOna();
		return this.order.instanceTroops(this.layer);
	}
	public ArrayList<Troops> defend()
	{
		ArrayList<Troops> def = new ArrayList<Troops>();
		for (int i = 0; i < troops[0]; i++)
			def.add(new Pikeman());
		for (int i = 0; i < troops[1]; i++)
			def.add(new Knights());
		for (int i = 0; i < troops[2]; i++)
			def.add(new Onager());
		troops[0] = 0; troops[1] = 0; troops[2] = 0;
		return def;
	}
}
