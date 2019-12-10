package Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class ZoneText {
	private Pane layer;
	private String value;	
	
	private TextField pyk;
	private TextField kni;
	private TextField ona;
	
	private HBox pykh;
	private HBox knih;
	private HBox onah;
	
	 Circle c1;
	 Circle c2;
	 Circle c3;
	
	public ZoneText(Pane layer)
	{
		this.layer = layer;
		/*this.pykh = new HBox();
		this.knih = new HBox();
		this.onah = new HBox();

		this.pyk = new TextField();
		this.kni = new TextField();
		this.ona = new TextField();
		
		this.pykh.getChildren().add(this.pyk);
		this.knih.getChildren().add(this.kni);
		this.onah.getChildren().add(this.ona);

		pykh.relocate(100, Settings.SCENE_HEIGHT - 20);*/
		c1 = new Circle(260, Settings.SCENE_HEIGHT +30, 2);
		c2 = new Circle(460, Settings.SCENE_HEIGHT + 30, 2);
		c3 = new Circle(670, Settings.SCENE_HEIGHT + 30, 2);
		c1.setFill(Color.RED);
		c2.setFill(Color.RED);
		c3.setFill(Color.RED);
		this.layer.getChildren().add(c1);
		this.layer.getChildren().add(c2);
		this.layer.getChildren().add(c3);

	}
}
