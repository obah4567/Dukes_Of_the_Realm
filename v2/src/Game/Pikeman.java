package Game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Pikeman extends Troops{
	
	public Pikeman(Pane layer)
	{
		super(layer, Settings.COST_PRODUCTION_PIKEMAN, Settings.TIME_COST_PIKEMAN, 
				Settings.SPEED_PIKEMAN, Settings.HEALTH_PIKEMAN, Settings.DAMMAGES_PIKEMAN);
		r.setFill(Color.RED);
	}
	
	public Pikeman()
	{
		super(Settings.COST_PRODUCTION_PIKEMAN, Settings.TIME_COST_PIKEMAN, 
				Settings.SPEED_PIKEMAN, Settings.HEALTH_PIKEMAN, Settings.DAMMAGES_PIKEMAN);
	}
}
