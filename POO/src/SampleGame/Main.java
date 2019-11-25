package SampleGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import SampleGame.alien.Input;
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

	private Pane playfieldLayer, layerFieldChateau;
	
	private List<Chateau> chateau = new ArrayList<>();

	private Image chateauNeutreImage;
	private Image chateauNormalImage;


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
		
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);
				if (player.getPause())
				{
					
				}
				else
				{

					// player input
					//player.processInput();

					// add random enemies
					//spawnEnemies(true);

					// movement
					//player.move();

					// check collisions
					//checkCollisions();

				// update sprites in scene
				// check if sprite can be removed
				}
		}

	};
	}

	
	
	public Chateau[] generateChateau(int nbChateau)
	{
		Chateau[] tab = new Chateau[nbChateau];
		int size = nbChateau;
		Random aleatoire = new Random();
		for (int i = 0; i < size; i++)
		{
			int dx = aleatoire.nextInt(Settings.SCENE_WIDTH - 50) + 50;
			int dy = aleatoire.nextInt(Settings.SCENE_HEIGHT - 50) + 50;
			tab[i] = new Chateau(imageViewChateau, layerFieldChateau, LIST[i], dx, dy);
			
		}
		return tab;
	}
	
	
	
	
	private void loadGame() {
		
		chateauNormalImage = new Image(getClass().getResource("/images/chateau_image.gif").toExternalForm(), 100, 100, true, true);
		chateauNeutreImage = new Image(getClass().getResource("/images/enemy.png").toExternalForm(), 50, 50, true, true);		
		//createPlayer();
		
	}

	private void createPlayer() {
		double x = (Settings.SCENE_WIDTH - chateauNormalImage.getWidth()) / 2.0;
		double y = Settings.SCENE_HEIGHT * 0.7;
		Player = new Player(layerFieldChateau , chateauNormalImage, x,y);
		
		player.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});
		
		player.getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			//MenuItem low = new MenuItem("Slow");
			//MenuItem medium= new MenuItem("Regular");
			//MenuItem high= new MenuItem("Fast");
			/*low.setOnAction(evt -> player.setFireFrequencyLow());
			medium.setOnAction(evt -> player.setFireFrequencyMedium());
			high.setOnAction(evt -> player.setFireFrequencyHigh());
			contextMenu.getItems().addAll(low, medium, high);
			contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());*/
		});
	}
	private void processInput(Input input, long now) {
		if (input.isPause())
		{
			pause(now);
		}
		if (input.isExit()) {
			Platform.exit();
			System.exit(0);
		}

	}
	private void pause(long now) {
		if (player.canPause(now))
			player.pause(now);
	}


	public static void main(String[] args) {
		launch(args);
	}

}