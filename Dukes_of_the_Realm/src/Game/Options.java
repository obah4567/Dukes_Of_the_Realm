package Game;

import javafx.event.EventHandler;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class Options {
	private Pane layer;
	private String label;
    private double dx = 0;
    private double dy = 0;
    private Castle c = null;
    
    private Rectangle background;
    private Text labelOption;
    
    //Constructor for buttons Attack and send ressources
    public Options(Pane layer, String l, double x, double y, Castle c){
        this.layer = layer;
        this.c = c;
    	this.label =  l;
    	int size = 100;
    	if (c.getDuc().equals("Joueur"))
        	size = 200;
    	if  (x > Settings.SCENE_WIDTH - c.getWidth_Image() * 1.5)
    		dx = x - c.getWidth_Image()/2 - size/2;
    	else
    		dx = x + c.getWidth_Image()/2 + size/2;
    	dy = y ;
        background = new Rectangle(size, 40, Color.BLACK);
        background.setArcHeight(10);
        background.setArcWidth(10);
       	this.layer.getChildren().add(background);
        background.relocate(dx - size/2, dy - 20);
       	labelOption = new Text(l);
       	labelOption.setFont(new Font(18));
       	labelOption.setFill(Color.WHITE);
        
        this.layer.getChildren().add(labelOption);
        labelOption.relocate(dx - size/2 + 10, dy - 15);
        
        labelOption.setOnMouseEntered(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                background.setFill(Color.LIGHTGREY);
                labelOption.setFill(Color.GREY);
            }
        });
        
        labelOption.setOnMouseExited(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                background.setFill(Color.BLACK);
                labelOption.setFill(Color.WHITE);
            }
        });
    }
    //Constructors for windows Your troops, pikeman, knights, onagers
    public Options(Pane layer, String l, double x, double y, Castle c,double wRectangle, double hRectangle)
    {
        this.layer = layer;
        label =  l;
        dx = x;
        dy = y;
        this.c = c;
        background = new Rectangle(wRectangle, hRectangle, Color.GREY);
        background.setArcHeight(13);
        background.setArcWidth(13);
        this.layer.getChildren().add(background);
        background.relocate(dx - wRectangle/2, dy - hRectangle/2);
        
        labelOption = new Text(l);
        labelOption.setFont(new Font(15));
       	labelOption.setFill(Color.WHITE);        
        this.layer.getChildren().add(labelOption);
        labelOption.relocate(dx - wRectangle/2 + 5, dy - hRectangle/2 + 10);
   }
    
    //Constructors for button Ok and Clear
    public Options(Pane layer, String l, double x, double y){
        this.layer = layer;
    	label =  l;
        dx = x;
        dy = y;  
        int size = 80;
        if (label.length() < 4)
        	size = 50;
        background = new Rectangle(size, 40, Color.BLACK);
        background.setArcHeight(10);
        background.setArcWidth(10);
       	this.layer.getChildren().add(background);
        background.relocate(dx - size/2, dy - 20);
       	
        labelOption = new Text(l);
       	labelOption.setFont(new Font(15));
       	labelOption.setFill(Color.WHITE);       
        
        this.layer.getChildren().add(labelOption);
        labelOption.relocate(dx - size/2 + 15, dy - 10);

        labelOption.setOnMouseEntered(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                background.setFill(Color.LIGHTGREY);
                labelOption.setFill(Color.GREY);
            }
        });
        
        labelOption.setOnMouseExited(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent me){
                background.setFill(Color.BLACK);
                labelOption.setFill(Color.WHITE);
            }
        });
    }
	public String getLabel() {
		return label;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public Castle getC() {
		return c;
	}

	public Rectangle getBackground() {
		return background;
	}

	public void setLabel(String label) {
		this.label = label;
		this.labelOption.setText(label);
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public void setC(Castle c) {
		this.c = c;
	}

	public void setBackground(Rectangle background) {
		this.background = background;
	}
    
	public void removeFromLayer()
	{
		this.layer.getChildren().remove(this.background);
		this.layer.getChildren().remove(this.labelOption);
	}
   
    
}
