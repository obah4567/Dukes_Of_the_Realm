package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.util.*;

public class Sprites {
    protected ImageView imgView;
    protected Pane layer;
    
    protected double width_image;
    protected double heigth_image;
    private Circle bg;
    
    private double dx;
	private double dy;
	private boolean removable = false;


    public Sprites(Pane layer, Image image, ArrayList<Castle> world)
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
        	dx = r.nextInt(Settings.SCENE_WIDTH - (int)this.width_image);
        	dy = r.nextInt(Settings.SCENE_HEIGHT - (int)this.heigth_image);
        }while (this.collision(world) || collisionWithEdge());
        
		//put the image on the screen at the right coordinates
		this.imgView.relocate(dx - this.width_image/2, dy - this.heigth_image/2);
	
        addToLayer();
        //Circle is the position of the image
        /*this.bg = new Circle(dx, dy, 5);
		this.bg.setFill(Color.RED); 
        this.layer.getChildren().add(bg); 
        this.bg.relocate(dx, dy);*/
    }
    public Sprites(Pane layer, Image image) //Constructor for player
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
        	dx = r.nextInt(Settings.SCENE_WIDTH - (int)this.width_image) ; //dimension image chateau
        	dy = r.nextInt(Settings.SCENE_HEIGHT - (int)this.heigth_image) ;
        }while(collisionWithEdge()) ;   
		//put the image on the screen at the right coordinates
		this.imgView.relocate(dx - this.width_image/2, dy - this.heigth_image/2);
		
        addToLayer();
        //Circle is the position of the image
        /*
        this.bg = new Circle(dx, dy, 5);
		this.bg.setFill(Color.RED); 
        this.layer.getChildren().add(bg); 
        this.bg.relocate(dx, dy);*/
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
    
    public boolean isRemovable() {
        return removable;
    }
    public void remove() {
        this.removable = true;
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
	 public void removeFromLayer() {
	        this.layer.getChildren().remove(this.imgView);
	}
    
	 //This method return boolean and test if distance beetween castles was respect, to avoid the collision
	 
    public boolean collision (ArrayList<Castle> world)
    {
    	for (int i = 0; i < world.size(); i++)
    	{
    		if (Settings.distance(this.dx, this.dy, world.get(i).getDx(), world.get(i).getDy()) < Settings.DISTANCE_MIN_BETWEEN_CASTLES)
    			return true;
    	}
    	return false;
    }
    
    //This method test if the castle don't touch Width or height edge
    public boolean collisionWithEdge()
    {
    	return (this.dx - this.getWidth_Image()/2 < 0 || this.dy - this.getHeigth_Image()/2 < 0);
    }
    
  

}