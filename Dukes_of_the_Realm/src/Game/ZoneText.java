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
	
	private double dx;
	private double dy;
	
	 Circle c1;
	 Circle c2;
	 Circle c3;
	
	public ZoneText(Pane layer, double dx, double dy)
	{
		this.layer = layer;
		this.pykh = new HBox();
		this.knih = new HBox();
		this.onah = new HBox();
		
		//pykh.setBackground(null);
		this.pyk = new TextField("0");
		this.kni = new TextField("0");
		this.ona = new TextField("0");
		
		this.pykh.getChildren().add(this.pyk);
		this.knih.getChildren().add(this.kni);
		this.onah.getChildren().add(this.ona);

		pykh.relocate(dx - 40, dy + 20);
		knih.relocate(dx + 160, dy + 40);
		onah.relocate(dx + 330, dy + 40);
		
		this.pyk.relocate(dx - 40, dy + 20);
		this.kni.relocate(dx + 160, dy + 40);
		this.ona.relocate(dx + 330, dy + 40);
		
		this.layer.getChildren().add(pyk);
		this.layer.getChildren().add(kni);
		this.layer.getChildren().add(ona);
		this.layer.getChildren().add(pykh);
		this.layer.getChildren().add(knih);
		this.layer.getChildren().add(onah);
		
		
		c1 = new Circle(dx - 40, dy + 20, 2);
		c2 = new Circle(dx + 160, dy + 40, 2);
		c3 = new Circle(dx + 330, dy + 40, 2);
		c1.setFill(Color.RED);
		c2.setFill(Color.RED);
		c3.setFill(Color.RED);
		this.layer.getChildren().add(c1);
		this.layer.getChildren().add(c2);
		this.layer.getChildren().add(c3);

	}
}
