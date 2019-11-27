package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.*;

public class Sprites {
    protected ImageView imgView;
    protected Pane layer;
    
    protected double width_image;
    protected double heigth_image;
    
    private double dx;
	private double dy;


    public Sprites(Pane layer, Image image, Castle[] ennemies, int nbEnnemies, Castle[] neutrals, Player player)
    {
        this.layer = layer;
        this.imgView = new ImageView(image);
        //size image
		this.width_image = image.getWidth();
		this.heigth_image = image.getHeight();
		//Coordinates
        
        Random r = new Random();
        do 
        {
        	dx = r.nextInt(Settings.SCENE_WIDTH - 2*(int)this.width_image) + 2*(int)this.width_image; //dimension image chateau
        	dy = r.nextInt(Settings.SCENE_HEIGHT - 2*(int)this.heigth_image) + 2*(int)this.heigth_image;
        }while (this.collision(ennemies, nbEnnemies, neutrals, player));
        
		//put the image on the screen at the right coordinates
		this.imgView.relocate(dx, dy);
   
        addToLayer();
    }
    public Sprites(Pane layer, Image image)
    {
        this.layer = layer;
        this.imgView = new ImageView(image);
        //size image
		this.width_image = image.getWidth();
		this.heigth_image = image.getHeight();
		//Coordinates
        int x = 0, y = 0;
        Random r = new Random();
        x = r.nextInt(Settings.SCENE_WIDTH - 2*(int)this.width_image) + 2*(int)this.width_image; //dimension image chateau
        y = r.nextInt(Settings.SCENE_HEIGHT - 2*(int)this.heigth_image) + 2*(int)this.heigth_image;
                
        this.dx = x;
		this.dy = y;
		//put the image on the screen at the right coordinates
		this.imgView.relocate(dx, dy);
		//add to the arrayList population to handle collision
        addToLayer();
    }
    public double getDx()
    {
    	return this.dx;
    }
    public double getDy()
    {
    	return this.dy;
    }
    public double getWidth_Image()
    {
    	return this.width_image;
    }
    public double getHeigth_Image()
    {
    	return this.heigth_image;
    }
    public ImageView getView()
    {
    	return this.imgView;
    }
    public Pane getLayer()
    {
    	return this.layer;
    }
    
    public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}
	public void setLayer(Pane layer) {
		this.layer = layer;
	}
	public void setWidth_image(double width_image) {
		this.width_image = width_image;
	}
	public void setHeigth_image(double heigth_image) {
		this.heigth_image = heigth_image;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	
	public void addToLayer() {
        this.layer.getChildren().add(this.imgView);
    }
    
    public boolean collision(Castle[] ennemies, int nbEnnemies, Castle[] neutrals, Player player)
    {
    	if (distance(this.dx, this.dy, player.getCastle().getDx(), player.getCastle().getDy()) < player.getCastle().getWidth_Image()*1.5)
    		return true;
    	for (int i = 0; i < nbEnnemies; i ++)
    	{
    		if (distance(this.dx, this.dy, ennemies[i].getDx(), ennemies[i].getDy()) < ennemies[i].getWidth_Image()*1.5)
    			return true;
    	}
    	return false;
    }
    
    public double distance(double x1, double y1, double x2, double y2)
    {
    	return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
    }

}
