package game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
import javafx.scene.image.Image;
import java.io.Serializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.util.*;

public class Sprites implements Serializable {
	/*
	 * imgView is an ImageView that describes and is displayed on the Pane
	 */
    protected  transient ImageView imgView;
    
    /*
     * layer is the Pane of the game
     */
    protected transient Pane layer;
    
    /*
     * width_image is an double that describes the width of the image of the Sprites
     */
    protected transient double width_image;
    
    /*
     * heigth_image is a double that describe the height of the image of the Sprites
     */
    protected transient double heigth_image;
    
    /*
     * dx is a double that describes the x coordinates of the Sprites
     */
    private double dx;
    
    /*
     * dy is a double that describes the y coordinates of the Sprites
     */
	private double dy;

	/*
	 * Construct a Sprites. Constructor for enemy castle and neutral castle
	 * generate random coordinates, check if there's no collision with other castle
	 * from the collection world or with the edge, and display the Sprites on the layer.
	 * @param layer is the Pane which is exposed on the Sprites
	 * @param image is the Image that is displayed on the layer
	 * @param world is the ArrayList<Castle> that contains every castle in the game
	 */
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
    }
    
    /*
     * Construct a Sprites. Constructor the player
	 * generate random coordinates, 
	 * from the collection world, check if there is no collisions with the edge, 
	 * and display the Sprites on the layer. 
	 * @param layer is the Pane which is exposed on the Sprites
	 * @param image is the Image that is displayed on the layer
     */
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
	
	/*
	 * display this.ImgView on the layer
	 */
	public void addToLayer() {
        this.layer.getChildren().add(this.imgView);
    }
	
	/*
	 * @param world is an arrayList that contains every castle in the game
	 * @return false if this does not collide with any castle of the world
	 * @return true if this does collide with a castle from the world
	 */
	 public boolean collision (ArrayList<Castle> world)
    {
    	for (int i = 0; i < world.size(); i++)
    	{
    		if (Settings.distance(this.dx, this.dy, world.get(i).getDx(), world.get(i).getDy()) < Settings.DISTANCE_MIN_BETWEEN_CASTLES)
    			return true;
    	}
    	return false;
    }
	 
	 /*
	  * @return true if this collide with the edge of the scene
	  * @return false if this does not collide with the edge of the scene
	  */
    public boolean collisionWithEdge()
    {
    	return (this.dx - this.getWidth_Image()/2 < 0 || this.dy - this.getHeigth_Image()/2 < 0);
    }
    
    public String toString()
    {
    	return dx + "|" + dy;
    }

}