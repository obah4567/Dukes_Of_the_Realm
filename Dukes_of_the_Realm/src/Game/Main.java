package Game;

/*import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;*/

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
	//private Random rnd = new Random();

	private Pane playfieldLayer;

	private Image castleImg;
	private Image neutCastleImg;
	private Image zonesNeutres;
	//private Image freeZoneImg;

	private Player player;
	private Castle[] ennemies = new Castle[5];
	private int nbEnnemies = 0;
	private NeutralCastle[] neutrals = new NeutralCastle[5];
	private int nbNeutrals = 0;
	/*private ArrayList<Sprites> population = new ArrayList<Sprites>();
	private Iterator<Sprites> it = population.iterator();*/
	
	
	private Text scoreMessage = new Text();
	private int scoreValue = 0;


	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	private boolean pause = false;
	private long lastPause = 0;

	Group root;

	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		primaryStage.setTitle("Dukes of the Realm");
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
				if (pause == true)
				{

				}
				else
				{
					// player input
					//player.processInput();

					// movement
					//player.move();

					// update sprites in scene
					//player.updateUI();

				}
				// update score, health, etc
				update();
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

			};
		};gameLoop.start();
	}

	private void loadGame() {
		castleImg = new Image(getClass().getResource("/images/castle1.png").toExternalForm(), 100, 100, true, true);
		neutCastleImg = new Image(getClass().getResource("/images/neutCastle2.png").toExternalForm(), 100, 100, true, true);
		zonesNeutres = new Image(getClass().getResource("/images/carre-vert-clair.png").toExternalForm(), 100, 100, true, true);
		//freeZoneImg = new Image(getClass().getResource("/images/freeZone.png").toExternalForm(), 20, 20, true, true);

		input = new Input(scene);
		input.addListeners();

		createPlayer();
		generateEnnemies();
		generateNeutrals();
		//createStatusBar();


		/*scene.setOnMousePressed(e -> {
			player.setX(e.getX() - (player.getWidth() / 2));
			player.setY(e.getY() - (player.getHeight() / 2));
		});*/
	}


	public void createStatusBar() {
		HBox statusBar = new HBox();
		scoreMessage.setText("Score : 0          Life : " /*+ player.getHealth()*/);
		statusBar.getChildren().addAll(scoreMessage);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
	}

	private void createPlayer() {
	
		Castle c = new Castle(castleImg, playfieldLayer, "Moi");
		player = new Player(input, c);

		/*player.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});*/

		player.getCastle().getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			MenuItem low = new MenuItem("Slow");
			MenuItem medium= new MenuItem("Regular");
			MenuItem high= new MenuItem("Fast");

			contextMenu.getItems().addAll(low, medium, high);
			contextMenu.show(player.getCastle().getView(), e.getScreenX(), e.getScreenY());
		});
	}
	private void generateEnnemies()
	{
		//int size = nbChateau;
		for (int i = 0; i < 5; i++)
		{
			ennemies[i] = new Castle(castleImg, playfieldLayer, ennemies, nbEnnemies, neutrals, nbNeutrals, player);
			nbEnnemies++;
			Castle c = ennemies[i];
			c.getView().setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				MenuItem low = new MenuItem("Slow");
				MenuItem medium= new MenuItem("Regular");
				MenuItem high= new MenuItem("Fast");

				contextMenu.getItems().addAll(low, medium, high);
				contextMenu.show(c.getView(), e.getScreenX(), e.getScreenY());
			});
			}
		}
	private void generateNeutrals()
	{
		//int size = nbChateau;
		for (int i = 0; i < 5; i++)
		{
			//neutrals[i] = new NeutralCastle(neutCastleImg, playfieldLayer, ennemies, nbEnnemies, neutrals, nbNeutrals, player);
			neutrals[i] = new NeutralCastle(zonesNeutres, playfieldLayer, "nom");
			nbNeutrals++;
			NeutralCastle c = neutrals[i];
			c.getView().setOnContextMenuRequested(e -> {
				ContextMenu contextMenu = new ContextMenu();
				MenuItem low = new MenuItem("Slow");
				MenuItem medium= new MenuItem("Regular");
				MenuItem high= new MenuItem("Fast");

				contextMenu.getItems().addAll(low, medium, high);
				contextMenu.show(c.getView(), e.getScreenX(), e.getScreenY());
			});
			}
		}

	public boolean canPause(long now)
	{
		return (now - lastPause > 10000 * 10000);
	}
	public void pause(long now)
	{
		if (canPause(now))
		{
			lastPause = now;
			if (pause)
				pause = false;
			else
				pause = true;
		}
	}
	/*
	private void gameOver() {
		HBox hbox = new HBox();
		hbox.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		hbox.getStyleClass().add("message");
		Text message = new Text();
		message.getStyleClass().add("message");
		message.setText("Game over");
		hbox.getChildren().add(message);
		root.getChildren().add(hbox);
		gameLoop.stop();
	}
	*/
	private void update() {

		/*if (collision) {
			scoreMessage.setText("Score : " + scoreValue + "          Life : " + player.getHealth());
		}*/
	}

	public static void main(String[] args) {
		launch(args);
	}

}
