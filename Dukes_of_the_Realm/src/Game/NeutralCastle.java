package Game;
import java.util.ArrayList;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class NeutralCastle extends Castle{

	public NeutralCastle(Image img, Pane layer, ArrayList<Castle> ennemies, ArrayList<NeutralCastle> neutrals, 
			Player player, ArrayList<Sprites> freeZones)
	{
		super(img, layer, ennemies, neutrals, player, freeZones);
		setDuc("Baron lambda");
		this.setTroops(Settings.NB_PIK_NEUT, Settings.NB_KNI_NEUT, Settings.NB_ONA_NEUT);
	}
}
