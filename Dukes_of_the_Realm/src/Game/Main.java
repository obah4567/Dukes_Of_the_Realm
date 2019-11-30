package Game;

import java.util.ArrayList;
//import java.util.List;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Main extends Application {
	
	//javafx structures
	private Pane playfieldLayer;
	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
	private Image castlePlayerImg;
	private Image castleImg;
	private Image neutCastleImg;
	private Image freeZoneImg;

	private Player player;
	private Castle lastCastle = null;
	private NeutralCastle lastNeutral = null;
	
	private ArrayList<Castle> ennemies = new ArrayList<Castle>();
	private ArrayList<NeutralCastle> neutrals = new ArrayList<NeutralCastle>();
	private ArrayList<Sprites> freeZones = new ArrayList<Sprites>();

	private Text stats = new Text();
	private Text options = new Text();
	private boolean option = false;
	private HBox optionsBar;

	private boolean pause = false;
	private long lastPause = 0;
	private long lastTurn = 0;

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
					update(now);
				}
				// update score, health, etc
				
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
		castlePlayerImg = new Image(getClass().getResource("/images/castlePlayer.png").toExternalForm(), 200, 200, true, true);
		castleImg = new Image(getClass().getResource("/images/castle1.png").toExternalForm(), 100, 100, true, true);
		neutCastleImg = new Image(getClass().getResource("/images/neutCastle2.png").toExternalForm(), 80, 80, true, true);
		freeZoneImg = new Image(getClass().getResource("/images/freeZone.png").toExternalForm(), 20, 20, true, true);

		input = new Input(scene);
		input.addListeners();

		createPlayer();
		generateEnnemies();
		generateNeutrals();
		generateFreeZones();
		createStatusBar();


		scene.setOnMousePressed(e -> {
			if (e.getButton() == MouseButton.SECONDARY)
			{
				
			}
			else
			{
				if (option)
				{
					if (Settings.distance(e.getX(), e.getY(), optionsBar.getLayoutX(), optionsBar.getLayoutY()) < optionsBar.getWidth())
						optionMenu();
				}
				
				
				if (Settings.distance(player.getCastle().getDx(), player.getCastle().getDy(), 
						e.getX(), e.getY()) < player.getCastle().getWidth_Image())
				{
					if (lastCastle == player.getCastle())
					{
						internOptions(player.getCastle());
						option = true;
					}
					else
					{
						lastCastle = player.getCastle();
						lastNeutral = null;
						option = false;
						if (optionsBar != null)
							root.getChildren().remove(optionsBar);
						optionsBar = null;
					}
					updateStatus(player.getCastle());
				}
				else
				{
					for (int i = 0; i < ennemies.size(); i++)
					{
						Castle intermediaire = ennemies.get(i);
						if (Settings.distance(intermediaire.getDx(), intermediaire.getDy(), 
								e.getX(), e.getY()) < intermediaire.getWidth_Image())
						{
							if (lastCastle == intermediaire)
							{
								if (!option)
								{
									option = true;
									externOptions(intermediaire);
								}
							}
							else
							{
								lastCastle = intermediaire;
								lastNeutral = null;
								option = false;
								if (optionsBar != null)
									root.getChildren().remove(optionsBar);
								optionsBar = null;
							}
							updateStatus(intermediaire);
						}
					}
					for (int i = 0; i < neutrals.size(); i++)
					{
						NeutralCastle intermediaire = neutrals.get(i);
						if (Settings.distance(intermediaire.getDx(), intermediaire.getDy(), 
								e.getX(), e.getY()) < intermediaire.getWidth_Image())
						{
							updateStatus(intermediaire);
							if (lastNeutral == intermediaire)
							{
								if (!option)
								{
									option = true;
									externOptions(intermediaire);
								}
							}
							else
								{
								lastNeutral = intermediaire;
								lastCastle = null;
								option = false;
								if (optionsBar != null)
									root.getChildren().remove(optionsBar);
								optionsBar = null;
								}
							updateStatus(intermediaire);
						}
					}
						//if (Settings.distance(neutrals[i].getDx(), neutrals[i].getDy(), 
								//e.getX(), e.getY()) < neutrals[i].getWidth_Image())//ebauche de bouton clear
					}
			}
			});
		}
	
	public void clear()
	{
		stats.setText(" ");
	}


	//Creation structures
	
	
	public void createStatusBar() {
		HBox statusBar = new HBox();
		/*stats.setText("Chateau de : " + Settings.BLANK + " Niveau : " + Settings.BLANK +
				" tr�sor : " + Settings.BLANK + " Troupes : " + Settings.BLANK + " Produit : " +
				Settings.BLANK + " Ordres : " + Settings.BLANK + " Porte : " + Settings.BLANK);
		*/
		statusBar.getChildren().addAll(stats);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
	}

	
	private void createPlayer() {
	
		Castle c = new Castle(castlePlayerImg, playfieldLayer, "Joueur");
		player = new Player(input, c);

	}
	private void generateEnnemies()
	{
		for (int i = 0; i < Settings.NB_ENN_CASTLE; i++)
		{
			Castle c = new Castle(castleImg, playfieldLayer, ennemies, neutrals, player, freeZones);
			ennemies.add(c);
			}
	}
	
	private void generateNeutrals()
	{
		for (int i = 0; i < Settings.NB_NEUT_CASTLE; i++)
		{
			NeutralCastle n = new NeutralCastle(neutCastleImg, playfieldLayer, ennemies, neutrals, player, freeZones);
			neutrals.add(n);
		}
	}
	
	private void generateFreeZones()
	{
		for (int i = 0; i < Settings.NB_FREE_ZONES; i ++)
		{
			Sprites fz = new Sprites(playfieldLayer, freeZoneImg, ennemies, neutrals, player, freeZones);
			freeZones.add(fz);
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
	private void update(long now) {
		if (now - lastTurn > Settings.TIME_TURN)
		{
			player.getCastle().setTreasure(player.getCastle().getTreasure() + 10*player.getCastle().getLevel());
			for (int i = 0; i < ennemies.size(); i++)
			{
				ennemies.get(i).setTreasure(ennemies.get(i).getTreasure() + 10*ennemies.get(i).getLevel());
			}
			for (int i = 0; i < neutrals.size(); i++)
			{
				neutrals.get(i).setTreasure(neutrals.get(i).getTreasure() + 2*neutrals.get(i).getLevel());
			}
			lastTurn = now;
			if (lastCastle != null)
				updateStatus(lastCastle);
			if(lastNeutral != null)
				updateStatus(lastNeutral);
		}
	}

	private void updateStatus(Castle castle)
	{
		String ordres = new String();
		if (castle.getOrder().getTarget() == null)
			ordres = "Aucun";
		else
			ordres = castle.getOrder().getTarget().getDuc();
		stats.setText("Chateau de : " + castle.getDuc() + Settings.SBLANK +
				" Niveau : " + castle.getLevel() + Settings.SBLANK + 
				" tresor : " + castle.getTreasure() + Settings.SBLANK + 
				" Troupes : " + castle.getTroops()[0] +" piquiers | " +
				castle.getTroops()[1] + " chevaliers | " + 
				castle.getTroops()[2] + " onagres " + Settings.SBLANK + 
				" Produit : " +	castle.getProduction().getProducts() + 
				Settings.SBLANK + " Ordre : " + ordres 
				+ Settings.SBLANK + " Porte : " + castle.getGate());
	}
	private void updateStatus(NeutralCastle neutral)
	{
		String ordres = new String("Aucun");
		
		stats.setText("Chateau de : " + neutral.getDuc() + Settings.SBLANK +
				" Niveau : " + neutral.getLevel() + Settings.SBLANK + 
				" tresor : " + neutral.getTreasure() + Settings.SBLANK + 
				" Troupes : " + neutral.getTroops()[0] +" piquiers | " +
				neutral.getTroops()[1] + " chevaliers | " + 
				neutral.getTroops()[2] + " onagres " + Settings.SBLANK + 
				" Produit : " +	neutral.getProduction().getProducts() + 
				Settings.SBLANK + " Ordre : " + ordres 
				+ Settings.SBLANK + " Porte : " + neutral.getGate());
	}
	public void internOptions(Castle c)
	{
		optionsBar = new HBox();
		options.setText("Produire des unites");
		optionsBar.getChildren().addAll(options);
		optionsBar.getStyleClass().add("statusBar");
		if (c.getDx() > Settings.SCENE_WIDTH - 2*c.getWidth_Image()){
			optionsBar.relocate(c.getDx() - c.getWidth_Image() + 20, c.getDy()); }
		else {
			optionsBar.relocate(c.getDx() + c.getWidth_Image() - 20, c.getDy()); }
		optionsBar.setPrefSize(c.getWidth_Image(), c.getHeigth_Image()/3);
		root.getChildren().add(optionsBar);
	}
	public void externOptions(Castle c)
	{
		optionsBar = new HBox();
		options.setText("Attaquer");
		optionsBar.getChildren().addAll(options);
		optionsBar.getStyleClass().add("statusBar");
		if (c.getDx() > Settings.SCENE_WIDTH - 2*c.getWidth_Image()) {
			optionsBar.relocate(c.getDx() - c.getWidth_Image() - 15 , c.getDy()); }
		else {
			optionsBar.relocate(c.getDx() + c.getWidth_Image() + 5, c.getDy()); }
		optionsBar.setPrefSize(c.getWidth_Image(), c.getHeigth_Image()/3);
		root.getChildren().add(optionsBar);
	}
	public void optionMenu()
	{
		if (options.getText() == "Attaquer")
		{
			root.getChildren().remove(optionsBar);
			optionsBar = null;
			option = false;
			lastCastle = null;
			lastNeutral = null;
			
			stats.setText("Attaque : " + Settings.SBLANK + "Piquiers : " + Settings.SBLANK + 
					" | Chevaliers : " + Settings.SBLANK + " | Onagres : " + Settings.SBLANK);
		}
		else
		{
			lastCastle = null;
			lastNeutral = null;
			root.getChildren().remove(optionsBar);
			optionsBar = null;
			option = false;
			stats.setText("Produire des unités de ?");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}