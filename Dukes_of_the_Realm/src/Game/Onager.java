package Game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/*
 * This file is part of Dukes_Of_the_Realm.
 * This describes the unit Onager
 */
public class Onager extends Troops{
	
	/*
	 * Construct a Onager with the right parameters from the file Settings
	 * this constructor is used when an attack is launched
	 * uses the super constructor and change the color of the rectangle of the unit to green
	 * the unit is displayed on the layer
	 * @param layer is the layer which is exposed on the image
	 * @param src is the castle the herald comes from
	 * @param target is the castle the herald is going to
	 */
	public Onager(Pane layer, Castle src, Castle target)
	{
		super(layer, Settings.COST_PRODUCTION_ONAGER, Settings.TIME_COST_ONAGER, Settings.SPEED_ONAGER, 
				Settings.HEALTH_ONAGER, Settings.DAMMAGES_ONAGER, src, target);
		r.setFill(Color.GREEN);
	}
	
	/*
	 * Construct a Onager with the right parameters from the file Settings
	 * this constructor is used in order to form a defense line
	 * uses the super constructor 
	 * 
	 */
	public Onager()
	{
		super(Settings.COST_PRODUCTION_ONAGER, Settings.TIME_COST_ONAGER, Settings.SPEED_ONAGER, 
				Settings.HEALTH_ONAGER, Settings.DAMMAGES_ONAGER);
	}
}
