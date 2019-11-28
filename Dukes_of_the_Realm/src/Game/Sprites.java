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


    public Sprites(Pane layer, Image image, ArrayList<Castle> ennemies, ArrayList<NeutralCastle> neutrals, 
    		 Player player, ArrayList<Sprites> freeZones)
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
        	dx = r.nextInt(Settings.SCENE_WIDTH - (int)this.width_image); //dimension image chateau
        	dy = r.nextInt(Settings.SCENE_HEIGHT - (int)this.heigth_image);
        }while (this.collision(ennemies, neutrals, player, freeZones) || collisionWithEdge());
        
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
        Random r = new Random();
        do
        {
        	dx = r.nextInt(Settings.SCENE_WIDTH - (int)this.width_image) ; //dimension image chateau
        	dy = r.nextInt(Settings.SCENE_HEIGHT - (int)this.heigth_image) ;
        }while(collisionWithEdge()) ;   
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
    
    public boolean collision(ArrayList<Castle> ennemies, ArrayList<NeutralCastle> neutrals, 
    		 Player player, ArrayList<Sprites> freeZones)
    {
    	if (Settings.distance(this.dx, this.dy, player.getCastle().getDx(), player.getCastle().getDy()) < player.getCastle().getWidth_Image()*1.5)
    		return true;
    	int size = ennemies.size();
    	for (int i = 0; i < size; i ++)
    	{
    		if (Settings.distance(this.dx, this.dy, ennemies.get(i).getDx(), ennemies.get(i).getDy()) < ennemies.get(i).getWidth_Image()*1.5)
    			return true;
    	}
    	size = neutrals.size();
    	for (int i = 0; i < size; i++)
    	{
    		if (Settings.distance(this.dx, this.dy, neutrals.get(i).getDx(), neutrals.get(i).getDy()) < this.getWidth_Image()*1.5)
    			return true;
    	}
    	size = freeZones.size();
    	for (int i = 0; i < size; i++)
    	{
    		Sprites s = freeZones.get(i);
    		if (Settings.distance(this.dx, this.dy, s.getDx(), s.getDy()) < this.getWidth_Image()*1.5)
    			return true;
    	}
    	return false;
    }
    public boolean collisionWithEdge()
    {
    	return (this.dx < this.getWidth_Image() && this.dy < this.getHeigth_Image());
    }
    
  

}
