package Game;


import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;

import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class ZoneText {
	private Pane layer;
	private String value;	
	
	private TextField pyk;
	private TextField kni;
	private TextField ona;
	
	private boolean gotCliked = false;
	
	private HBox pykh;
	private HBox knih;
	private HBox onah;
	
	Circle c;
	Circle c1;
	Circle c2;
	Circle c3;
	
	
	public ZoneText(Pane layer, Castle target, double dx, double dy)
	{
		this.layer = layer;
		this.pyk = new TextField();
		this.kni = new TextField();
		this.ona = new TextField();
		
		/*this.pykh = new HBox(pyk);	
		this.knih = new HBox(kni);
		this.onah = new HBox(ona);
		this.pykh.relocate(dx, dy + 20);
		this.knih.relocate(dx + 160, dy + 20);
		this.onah.relocate(dx + 320, dy + 20);*/
		
		this.pyk.setPrefSize(130, 40);
		this.kni.setPrefSize(130, 40);
		this.ona.setPrefSize(130, 40);
		
		this.pyk.setPromptText("Nombre de piquiers");
		this.kni.setPromptText("Nombre de chevaliers");
		this.ona.setPromptText("Nombre d'onagres");
		
		if (target.getDx() > Settings.SCENE_WIDTH - 550)//The castle is near the edge. to get more visibility, the options shall be displayed on the left side of the castle
		{
			this.pyk.relocate(dx - target.getWidth_Image()/2 - 145, dy + 21);
			this.kni.relocate(dx - target.getWidth_Image()/2 - 295, dy + 21);
			this.ona.relocate(dx - target.getWidth_Image()/2 - 445, dy + 21);
		}
		else
		{
			this.pyk.relocate(dx + target.getWidth_Image()/2 + 15, dy + 21);
			this.kni.relocate(dx + target.getWidth_Image()/2 + 165, dy + 21);
			this.ona.relocate(dx + target.getWidth_Image()/2 + 315, dy + 21);
		}
		
		this.layer.getChildren().addAll(pyk, kni, ona);
		/*
		c = new Circle(dx, dy, 2);
		c1 = new Circle(dx, dy + 20, 2);
		c2 = new Circle(dx + 160, dy + 20, 2);
		c3 = new Circle(dx + 320, dy + 20, 2);
		
		c.setFill(Color.GREEN);
		c1.setFill(Color.RED);
		c2.setFill(Color.RED);
		c3.setFill(Color.RED);
		this.layer.getChildren().add(c);
		this.layer.getChildren().add(c1);
		this.layer.getChildren().add(c2);
		this.layer.getChildren().add(c3);
		*/
	}
	public void removeFromLayer()
	{
		layer.getChildren().removeAll(pyk, kni, ona);

	}
	public TextField getPyk()
	{
		return this.pyk;
	}
	public TextField getKni()
	{
		return this.kni;
	}
	public TextField getOna()
	{
		return this.ona;
	}
	public String getTextPyk()
	{
		return pyk.getText();
	}
	public String getTextKni()
	{
		return kni.getText();
	}
	public String getTextOna()
	{
		return ona.getText();
	}
	public void resetTextField()
	{
		this.removeFromLayer();
		layer.getChildren().removeAll(pyk, kni, ona, pykh, knih, onah);
	}
	
}
