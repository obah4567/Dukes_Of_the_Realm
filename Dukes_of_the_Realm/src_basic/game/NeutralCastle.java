package game;
import java.util.ArrayList;
import java.io.Serializable;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/*
 * This file is part of Dukes_Of_the_Realm.
 */

public class NeutralCastle extends Castle implements Serializable{
	/*
	 * Construct a NeutralCastle which is a neutral baron's Castle
	 * @param img is the image of the neutral castle
	 * @param layer is the pane which is exposed on the image
	 * @param world is an arraylist that contains every castle in the game
	 */
	public NeutralCastle(Image img, Pane layer, ArrayList<Castle> world)
	{
		super(img, layer, world);
		setDuc("Baron lambda");
		this.setTroops(Settings.NB_PIK_NEUT, Settings.NB_KNI_NEUT, Settings.NB_ONA_NEUT);
	}
	
	public String toString()
	{
		return super.toString();
	}
}
