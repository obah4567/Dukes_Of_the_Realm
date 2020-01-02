package Game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Herald extends Troops{
	
	public Herald(Pane layer, Castle src, Castle target, int speed)
	{
		super(layer, 0, 0, speed, 1, 0, src, target);
		r.setFill(Color.BEIGE);
	}
}
