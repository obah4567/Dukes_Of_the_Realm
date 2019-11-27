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
	//private Image freeZoneImg;

	private Player player;
	
	private Castle[] ennemies = new Castle[5];
	private int nbEnnemies = 0;
	private NeutralCastle[] neutrals = new NeutralCastle[5];
	private int nbNeutrals = 0;
	private Sprites[] freeZones = new Sprites[5];
	private int nbfreeZones = 0;
	
	/* 
	 
	private ArrayList<Castle> ennemies = new ArrayList<Castle>();
	private Iterator<Castle> it1 = ennemies.iterator();
	private ArrayList<NeutralCastle> neutrals = new ArrayList<NeutralCastle>();
	private Iterator<NeutralCastle> it2 = neutrals.iterator();
	private ArrayList<Sprites> freeZones = new ArrayList<Sprites>();
	private Iterator<Sprites> it3 = freeZones.iterator();
	
	*/
	
	
	private Text stats = new Text();
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
		neutCastleImg = new Image(getClass().getResource("/images/neutCastle2.png").toExternalForm(), 80, 80, true, true);
		//freeZoneImg = new Image(getClass().getResource("/images/freeZone.png").toExternalForm(), 20, 20, true, true);

		input = new Input(scene);
		input.addListeners();

		createPlayer();
		generateEnnemies();
		generateNeutrals();
		createStatusBar();


		scene.setOnMousePressed(e -> {
			if (Settings.distance(player.getCastle().getDx(), player.getCastle().getDy(), 
					e.getX(), e.getY()) < player.getCastle().getWidth_Image())
				updateStatus(player);
			for (int i = 0; i < 5; i ++)
			{
				if (Settings.distance(ennemies[i].getDx(), ennemies[i].getDy(), 
						e.getX(), e.getY()) < ennemies[i].getWidth_Image())
					updateStatus(ennemies[i]);
				if (Settings.distance(neutrals[i].getDx(), neutrals[i].getDy(), 
						e.getX(), e.getY()) < neutrals[i].getWidth_Image())
					updateStatus(neutrals[i]);
			}
			//player.setX(e.getX() - (player.getWidth() / 2));
			//player.setY(e.getY() - (player.getHeight() / 2));
		});
	}


	public void createStatusBar() {
		HBox statusBar = new HBox();
		stats.setText("Chateau de : " + Settings.BLANK + " Niveau : " + Settings.BLANK +
				" trésor : " + Settings.BLANK + " Troupes : " + Settings.BLANK + " Produit : " +
				Settings.BLANK + " Ordres : " + Settings.BLANK + " Porte : " + Settings.BLANK);
		statusBar.getChildren().addAll(stats);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
	}

	private void createPlayer() {
	
		Castle c = new Castle(castleImg, playfieldLayer, "Moi");
		player = new Player(input, c);

		player.getCastle().getView().setOnMousePressed(e -> {
			updateStatus(player);
			e.consume();
		});

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
			neutrals[i] = new NeutralCastle(neutCastleImg, playfieldLayer, ennemies, nbEnnemies, neutrals, nbNeutrals, player);
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
	private void updateStatus(Player player)
	{
		String ordres = new String();
		if (player.getCastle().getOrder().getTarget() == null)
			ordres = "Aucun";
		else
			ordres = player.getCastle().getOrder().getTarget().getDuc();
		
		stats.setText("Chateau de : " + player.getCastle().getDuc() + Settings.SBLANK +
				" Niveau : " + player.getCastle().getLevel() + Settings.SBLANK + 
				" trésor : " + player.getCastle().getTreasure() + Settings.SBLANK + 
				" Troupes : " + player.getCastle().getTroops()[0] +" piquiers | " +
				player.getCastle().getTroops()[1] + " chevaliers | " + 
				player.getCastle().getTroops()[2] + " onagres " + Settings.SBLANK + 
				" Produit : " +	player.getCastle().getProduction().getProducts() + 
				Settings.SBLANK + " Ordre : " + ordres
				+ Settings.SBLANK + " Porte : " + player.getCastle().getGate());
	}
	private void updateStatus(Castle ennemi)
	{
		String ordres = new String();
		if (ennemi.getOrder().getTarget() == null)
			ordres = "Aucun";
		else
			ordres = ennemi.getOrder().getTarget().getDuc();
		stats.setText("Chateau de : " + ennemi.getDuc() + Settings.SBLANK +
				" Niveau : " + ennemi.getLevel() + Settings.SBLANK + 
				" trésor : " + ennemi.getTreasure() + Settings.SBLANK + 
				" Troupes : " + ennemi.getTroops()[0] +" piquiers | " +
				ennemi.getTroops()[1] + " chevaliers | " + 
				ennemi.getTroops()[2] + " onagres " + Settings.SBLANK + 
				" Produit : " +	ennemi.getProduction().getProducts() + 
				Settings.SBLANK + " Ordre : " + ordres 
				+ Settings.SBLANK + " Porte : " + ennemi.getGate());
	}
	private void updateStatus(NeutralCastle neutral)
	{
		String ordres = new String("Aucun");
		
		stats.setText("Chateau de : " + neutral.getDuc() + Settings.SBLANK +
				" Niveau : " + neutral.getLevel() + Settings.SBLANK + 
				" trésor : " + neutral.getTreasure() + Settings.SBLANK + 
				" Troupes : " + neutral.getTroops()[0] +" piquiers | " +
				neutral.getTroops()[1] + " chevaliers | " + 
				neutral.getTroops()[2] + " onagres " + Settings.SBLANK + 
				" Produit : " +	neutral.getProduction().getProducts() + 
				Settings.SBLANK + " Ordre : " + ordres 
				+ Settings.SBLANK + " Porte : " + neutral.getGate());
	}

	public static void main(String[] args) {
		launch(args);
	}

}
