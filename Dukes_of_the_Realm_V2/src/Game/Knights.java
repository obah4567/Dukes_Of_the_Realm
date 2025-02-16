package Game;

import java.io.Serializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
public class Knights extends Troops{

	public Knights(Pane layer, Castle src, Castle target)
	{
		super(layer, Settings.COST_PRODUCTION_KNIGHT, Settings.TIME_COST_KNIGHT, 
				Settings.SPEED_KNIGHT, Settings.DAMMAGES_KNIGHT, Settings.DAMMAGES_KNIGHT, src, target);
		r.setFill(Color.GREY);
	}
	public Knights()
	{
		super(Settings.COST_PRODUCTION_KNIGHT, Settings.TIME_COST_KNIGHT, 
				Settings.SPEED_KNIGHT, Settings.DAMMAGES_KNIGHT, Settings.DAMMAGES_KNIGHT);
	}
}
