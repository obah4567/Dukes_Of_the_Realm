package game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
/*
 * This file is part of Dukes_Of_the_Realm.
 * this describes the unit Pikeman
 */
public class Pikeman extends Troops{
	
	/*
	 * Construct a Pikeman with the right parameters from the file Settings
	 * this constructor is used when an attack is launched
	 * uses the super constructor and change the color of the rectangle of the unit to red
	 * the unit is displayed on the layer
	 * @param layer is the layer which is exposed on the image
	 * @param src is the castle the herald comes from
	 * @param target is the castle the herald is going to
	 */
	public Pikeman(Pane layer, Castle src, Castle target)
	{
		super(layer, Settings.COST_PRODUCTION_PIKEMAN, Settings.TIME_COST_PIKEMAN, 
				Settings.SPEED_PIKEMAN, Settings.HEALTH_PIKEMAN, Settings.DAMMAGES_PIKEMAN, src, target);
		r.setFill(Color.RED);
	}
	
	/*
	 * Construct a Pikeman with the right parameters from the file Settings
	 * this constructor is used in order to form a defense line
	 * uses the super constructor 
	 * 
	 */
	public Pikeman()
	{
		super(Settings.COST_PRODUCTION_PIKEMAN, Settings.TIME_COST_PIKEMAN, 
				Settings.SPEED_PIKEMAN, Settings.HEALTH_PIKEMAN, Settings.DAMMAGES_PIKEMAN);
	}
}
