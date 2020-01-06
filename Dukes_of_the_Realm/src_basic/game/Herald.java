package game;
/*
 * This file is part of Dukes_Of_the_Realm.
 * this describes the unit Herald
 */
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Herald extends Troops{
	
	/*
	 * Construct a herald
	 * this unit is only instantiated when a castle is launching an attack
	 * it works like a flag, it indicates that it's the last member of the army
	 * when it is discovered it tells the order has been fulfilled, and the defense line
	 * from the target has no longer a purpose
	 * this unit has no damage, and its speed is the speed of the slowest unit of the 
	 * army less 1. it has a special color : AQUA. it is a rectangle
	 * @param layer is the layer which is exposed on the image
	 * @param src is the castle the herald comes from
	 * @param target is the castle the herald is going to
	 * @param speed is the speed of the herald.
	 */
	public Herald(Pane layer, Castle src, Castle target, int speed)
	{
		super(layer, 0, 0, speed - 1, 1, 0, src, target);
		r.setFill(Color.AQUA);
	}
}
