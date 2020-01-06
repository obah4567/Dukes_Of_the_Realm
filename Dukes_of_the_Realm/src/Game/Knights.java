package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 * this describes the unit Knight
 */
import java.io.Serializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
public class Knights extends Troops{

	/*
	 * Construct a Knight with the right parameters from the file Settings
	 * this constructor is used when an attack is launched
	 * uses the super constructor and change the color of the rectangle of the unit to grey
	 * the unit is displayed on the layer
	 * @param layer is the layer which is exposed on the image
	 * @param src is the castle the herald comes from
	 * @param target is the castle the herald is going to
	 */
	public Knights(Pane layer, Castle src, Castle target)
	{
		super(layer, Settings.COST_PRODUCTION_KNIGHT, Settings.TIME_COST_KNIGHT, 
				Settings.SPEED_KNIGHT, Settings.DAMMAGES_KNIGHT, Settings.DAMMAGES_KNIGHT, src, target);
		r.setFill(Color.GREY);
	}
	
	/*
	 * Construct a Knight with the right parameters from the file Settings
	 * this constructor is used in order to form a defense line
	 * uses the super constructor 
	 * 
	 */
	public Knights()
	{
		super(Settings.COST_PRODUCTION_KNIGHT, Settings.TIME_COST_KNIGHT, 
				Settings.SPEED_KNIGHT, Settings.DAMMAGES_KNIGHT, Settings.DAMMAGES_KNIGHT);
	}
}
