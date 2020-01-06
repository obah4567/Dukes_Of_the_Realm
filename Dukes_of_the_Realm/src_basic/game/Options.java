package game;
/*
 * This file is part of Dukes_Of_the_Realm.
 * this class is responsable for every options displayed on the layer. 
 */
import javafx.event.EventHandler;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

public class Options {
	
	/*
	 * layer is the pane of the game
	 */
	private Pane layer;
	
	/*
	 * label is the label of the Options, or its name
	 */
	private String label;
	

	/*
	 * dx is the x coordinate of the Options
	 */
    private double dx = 0;
    
    /*
     * dy is the y coordinate of the Options
     */
    private double dy = 0;
    
    /*
     * c is the castle concerned by the Options
     */
    private Castle c = null;
    
    /*
     * background is the background of the Options, its shape is a rectangle
     */
    private Rectangle background;
    
    /*
     * labelOption is a Text, that shall be displayed on the layer. 
     */
    private Text labelOption;
    
    //Constructor for buttons Attack and produce units
    /*
     * Construct an Options from the first set of Options that is displayed on the screen
     * the player can click on it. 
     * it is added to the arraylist arrayOptions
     * this option is set at the right position according the edge of the scene
     * @param layer is the Pane which is exposed on the Options
     * @param l is the String that describes the Options
     * @param x is the double x coordinate of the Options
     * @param y is the double y coordinate of the Options
     * @param c is the castle concerned by the option
     */
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
    /*
     * Construct an Options displayed on the Pane
     * these Options can not get clicked, these are just windows showing informations. 
     * @param layer is the Pane which is exposed on the Options
     * @param l is the String that describes the Options
     * @param x is the double x coordinate of the Options
     * @param y is the double y coordinate of the Options
     * @param c is the castle concerned by the option
     * @param wRectangle is a double, the width of the background of the option
     * @param hRectangle is a double, the height of the background of the option
     */
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
    /*
     * Construct an Options from the second set of Options displayed on the Pane
     * these Options can get clicked. 
     * it is added to the arraylist arrayOptions2
     * @param layer is the Pane which is exposed on the Options
     * @param l is the String that describes the Options
     * @param x is the double x coordinate of the Options
     * @param y is the double y coordinate of the Options
     */
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
	
	/*
	 * Change label and labelOption with the parameter.
	 * @param label is a String that shall change the current label and labelOption
	 */
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
    
	/*
	 * removes from the Pane all structures of this Options : background and labelOption
	 */
	public void removeFromLayer()
	{
		this.layer.getChildren().remove(this.background);
		this.layer.getChildren().remove(this.labelOption);
	}
   
    
}
