package SampleGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Main extends Application {
	private Random rnd = new Random();

	private Pane playfieldLayer;

	private Image playerImage;
	//private Image enemyImage;


	private Scene scene;
	private Input input;
	
	//private boolean justPause = false, withPause = true;
	
	Group root;
	
	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		loadGame();
		
		/*gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);

				// player input
				player.processInput();

				// add random enemies
				//spawnEnemies(true);

				// movement
				player.move();

				// check collisions
				checkCollisions();

			// update sprites in scene
			// check if sprite can be removed

		}

	};
		gameLoop.start();*/
	}

	private void loadGame() {
		
		playerImage = new Image(getClass().getResource("/images/royaume.jpg").toExternalForm(), 100, 100, true, true);
		//enemyImage = new Image(getClass().getResource("/images/enemy.png").toExternalForm(), 50, 50, true, true);		
		createPlayer();
		
	}

	private void createPlayer() {
		double x = (Settings.SCENE_WIDTH - playerImage.getWidth()) / 2.0;
		double y = Settings.SCENE_HEIGHT * 0.7;
		Player = new Player(playfieldLayer , playerImage, x,y);
		
		player.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});
		
		/*player.getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			MenuItem low = new MenuItem("Slow");
			MenuItem medium= new MenuItem("Regular");
			MenuItem high= new MenuItem("Fast");
			low.setOnAction(evt -> player.setFireFrequencyLow());
			medium.setOnAction(evt -> player.setFireFrequencyMedium());
			high.setOnAction(evt -> player.setFireFrequencyHigh());
			contextMenu.getItems().addAll(low, medium, high);
			contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());
		});*/
	}


	public static void main(String[] args) {
		launch(args);
	}

}