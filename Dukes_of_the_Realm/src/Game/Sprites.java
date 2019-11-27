package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sprites {
    protected ImageView imgView;
    protected Pane layer;

    public Sprites(Pane layer, Image image)
    {
        this.layer = layer;
        this.imgView = new ImageView(image);
        
    }
    
    public ImageView getView()
    {
    	return this.imgView;
    }
    public Pane getLayer()
    {
    	return this.layer;
    }
    
    public void addToLayer() {
        this.layer.getChildren().add(this.imgView);
    }

}
