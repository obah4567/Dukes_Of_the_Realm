package game;
/*
 * This file is part of Dukes_Of_the_Realm.
 * this class describes the TextFields needed to sent orders.
 */

import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;

import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ZoneText {
	
	/*
	 * layer is the Pane of the game
	 */
	private Pane layer;
	
	/*
	 * pyk is a TextField that will be filled by the player 
	 */
	private TextField pyk;
	
	/*
	 * kni is a TextField that will be filled by the player 
	 */
	private TextField kni;
	
	/*
	 * ona is a TextField that will be filled by the player 
	 */
	private TextField ona;
	
	/*
	 * Construct a ZoneText at the right position according the position of the target
	 * it sets the size and a prompt text in the TextFields, then displays the TextFields on layer
	 * @param layer is the Pane of the game
	 * @target is a Castle which depends the ZoneText
	 * @param dx is a double which is the x coordinate of the ZoneText
	 * @param dy is a double which is the y coordinate of the ZoneText
	 */
	public ZoneText(Pane layer, Castle target, double dx, double dy)
	{
		this.layer = layer;
		this.pyk = new TextField();
		this.kni = new TextField();
		this.ona = new TextField();
		
		
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
	}
	
	/*
	 * remove all the TextField from the layer
	 */
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
	
	/*
	 * @return a String that was written in the TextFields
	 */
	public String getTextPyk()
	{
		return pyk.getText();
	}
	
	/*
	 * @return a String that was written in the TextFields
	 */
	public String getTextKni()
	{
		return kni.getText();
	}
	
	/*
	 * @return a String that was written in the TextFields
	 */
	public String getTextOna()
	{
		return ona.getText();
	}

	
}
