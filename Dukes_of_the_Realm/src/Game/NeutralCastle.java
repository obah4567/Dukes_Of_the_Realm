package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class NeutralCastle extends Castle{

	public NeutralCastle(Image img, Pane layer, Castle[] ennemies, int nbEnnemies, Castle[] neutrals, 
			int nbNeutrals, Player player)
	{
		super(img, layer, ennemies, nbEnnemies, neutrals, nbNeutrals, player);
		setDuc("baron");
	}

	public NeutralCastle(Image img, Pane layer, String duc) {
		super(img, layer, duc);
		// TODO Auto-generated constructor stub
	}
	
	
}
