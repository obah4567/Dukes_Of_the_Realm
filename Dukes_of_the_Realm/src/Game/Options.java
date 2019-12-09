package Game;

import javafx.scene.Parent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.*;
import javafx.scene.layout.Pane;

public class Options extends Parent{
	private Pane layer;
	private String label;
    private double dx = 0;
    private double dy = 0;
    private Castle c;
    private Circle ci;
    
    Rectangle background;
    Text labelOption;
    
    public Options(Pane layer, String l, double x, double y, Castle c){
        this.layer = layer;
    	label =  new String(l);
        dx = x;
        dy = y;
        this.c = c;
        int size = 100;
        if (c.getDuc() == "Joueur")
        	size = 200;
        background = new Rectangle(size, 40, Color.WHITE);
        background.setArcHeight(10);
        background.setArcWidth(10);
       	this.layer.getChildren().add(background);
        background.relocate(dx - c.getWidth_Image()/2, dy - 20);
       	labelOption = new Text(label);
       	labelOption.setFont(new Font(18));
       	labelOption.setFill(Color.GREY);
       	labelOption.setX(25);
       	labelOption.setY(45);
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-45.0);
        Lighting li = new Lighting();
        li.setLight(light);
        background.setEffect(li);
        this.layer.getChildren().add(labelOption);
        labelOption.relocate(dx - c.getWidth_Image()/2 + 5, dy - 15);
        /*
        this.ci = new Circle(dx, dy, 2);
        this.ci.setFill(Color.RED);
        this.ci.relocate(dx, dy);
        this.layer.getChildren().add(ci);*/
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
