package Game;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Random;
public class Castle extends Sprites{

	//game structures
	private String duc;
	private int treasure;
	private int level;
	private int troops[];
	private Production production;
	private Order order;
	private char gate;
	//coordinates
	private double dx;
	private double dy;

	private double width_image;
	private double heigth_image;

	//Constructors
	public Castle(Image img, Pane layer)
	{
		super(layer, img);
		Random r = new Random();
        //coordonates
		this.dx = r.nextInt(Settings.SCENE_WIDTH - 2*(int)this.width_image) + 2*(int)this.width_image; //dimension image chateau
		this.dy = r.nextInt(Settings.SCENE_HEIGHT - 2*(int)this.heigth_image) + 2*(int)this.heigth_image;
        //size image
		this.width_image = img.getWidth();
		this.heigth_image = img.getHeight();
        //where is the gate
        this.gate = Settings.GATES[r.nextInt(4)];
        this.troops = new int[3];
        this.troops[0] = 0; this.troops[1] = 0; troops[2] = 0;
        this.treasure = 0;
        this.level = 1;
        this.duc = Settings.DUC_NAMES[r.nextInt(5)];
        this.imgView.relocate(dx, dy);
        
		addToLayer();
	}

    public Castle(Image img, Pane layer, String duc)
	{
		super(layer, img);
		Random r = new Random();
        
        //size image
		this.width_image = img.getWidth();
		this.heigth_image = img.getHeight();
		//coordonates
		//int x = 0, y = 0;
		this.dx = r.nextInt(Settings.SCENE_WIDTH - 2*(int)this.width_image) + 2*(int)this.width_image; //dimension image chateau
		this.dy = r.nextInt(Settings.SCENE_HEIGHT - 2*(int)this.heigth_image) + 2*(int)this.heigth_image;
        //where is the gate
        this.gate = Settings.GATES[r.nextInt(4)];
        this.troops = new int[3];
        this.troops[0] = 0; 
        this.troops[1] = 0; 
        troops[2] = 0;
        this.treasure = 0;
        this.level = 1;
        this.duc = duc;
        this.imgView.relocate(dx, dy);

		addToLayer();
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

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
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

	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}



}
