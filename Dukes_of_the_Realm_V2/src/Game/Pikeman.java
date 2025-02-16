package Game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Pikeman extends Troops{
	
	public Pikeman(Pane layer, Castle src, Castle target)
	{
		super(layer, Settings.COST_PRODUCTION_PIKEMAN, Settings.TIME_COST_PIKEMAN, 
				Settings.SPEED_PIKEMAN, Settings.HEALTH_PIKEMAN, Settings.DAMMAGES_PIKEMAN, src, target);
		r.setFill(Color.RED);
	}
	
	public Pikeman()
	{
		super(Settings.COST_PRODUCTION_PIKEMAN, Settings.TIME_COST_PIKEMAN, 
				Settings.SPEED_PIKEMAN, Settings.HEALTH_PIKEMAN, Settings.DAMMAGES_PIKEMAN);
	}
}
