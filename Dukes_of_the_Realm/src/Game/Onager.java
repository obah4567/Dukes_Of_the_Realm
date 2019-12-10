package Game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Onager extends Troops{

	public Onager(Pane layer)
	{
		super(layer, Settings.COST_PRODUCTION_ONAGER, Settings.TIME_COST_ONAGER, Settings.SPEED_ONAGER, 
				Settings.HEALTH_ONAGER, Settings.DAMMAGES_ONAGER);
		r.setFill(Color.BROWN);
	}
	public Onager()
	{
		super(Settings.COST_PRODUCTION_ONAGER, Settings.TIME_COST_ONAGER, Settings.SPEED_ONAGER, 
				Settings.HEALTH_ONAGER, Settings.DAMMAGES_ONAGER);
		r.setFill(Color.BROWN);
	}
}
