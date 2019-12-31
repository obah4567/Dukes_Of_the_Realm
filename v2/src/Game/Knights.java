package Game;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
public class Knights extends Troops{

	public Knights(Pane layer)
	{
		super(layer, Settings.COST_PRODUCTION_KNIGHT, Settings.TIME_COST_KNIGHT, 
				Settings.SPEED_KNIGHT, Settings.DAMMAGES_KNIGHT, Settings.DAMMAGES_KNIGHT);
		r.setFill(Color.GREY);
	}
	public Knights()
	{
		super(Settings.COST_PRODUCTION_KNIGHT, Settings.TIME_COST_KNIGHT, 
				Settings.SPEED_KNIGHT, Settings.DAMMAGES_KNIGHT, Settings.DAMMAGES_KNIGHT);
	}
}
