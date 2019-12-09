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

	private Player player;
	private Castle lastCastle = null;
	//private NeutralCastle lastNeutral = null;
	
	private ArrayList<Castle> world = new ArrayList<Castle>();
	private ArrayList<Castle> competition = new ArrayList<Castle>();
	private ArrayList<Options> competition2 = new ArrayList<Options>();

	private Text stats = new Text();
	private boolean option = false;
	private boolean option2 = false;
	private boolean comp = false;

	private Options opt1;
	private Options opt2;


	private boolean pause = false;
	private long lastPause = 0;
	private long lastTurn = 0;
	private long lastCliq = 0;

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
		neutCastleImg = new Image(getClass().getResource("/images/neutCastle2.png").toExternalForm(), 100, 100, true, true);

		input = new Input(scene);
		input.addListeners();

		createPlayer();
		generateEnnemies();
		generateNeutrals();
		createStatusBar();


		scene.setOnMousePressed(e -> {
			if (e.getButton() == MouseButton.SECONDARY)
			{
				//Nothing happens
			}
			else
			{
				if (option == true)
				{
					if (opt1 != null)
					{
						if (Settings.distance(e.getX(), e.getY(), opt1.getDx(), opt1.getDy()) < opt1.getBackground().getHeight())
							competition2.add(opt1);
					}
					if (opt2 != null)
					{
						if (Settings.distance(e.getX(), e.getY(), opt2.getDx(), opt2.getDy()) < opt2.getBackground().getHeight())
							competition2.add(opt2);
					}
				}
			
				for (int i = 0; i < world.size(); i++)
				{
					Castle intermediaire = world.get(i);
					if (Settings.distance(intermediaire.getDx(), intermediaire.getDy(), 
							e.getX(), e.getY()) < intermediaire.getWidth_Image())
						competition.add(intermediaire);
				}
				int index = handleCompetition(e.getX(), e.getY());
				if (comp)
					optionMenu(competition2.get(index));
				else
				{
					Castle intermediate = competition.get(index);
					if (lastCastle == intermediate)
					{
						option = true;
						options(intermediate);
						option2 = false;
					}
					else
					{
						option = false;
						option2 = false;
						lastCastle = intermediate;							
						if (opt1 != null )
							opt1.removeFromLayer();
						if (opt2 != null)
							opt2.removeFromLayer();
						opt1 = null;
						opt2 = null;
					}
					updateStatus(lastCastle);
					competition.removeAll(competition);
					competition2.removeAll(competition2);
				}
				}});
		}
	
	public void clear()
	{
		stats.setText(" ");
	}

	public int handleCompetition(double dx, double dy)
	{
		double min1 = 10000000, min2 = 10000000;
		int index = 0, index2 = 0;
		for (int i = 0; i < competition2.size(); i++)
		{
			if (Settings.distance(dx, dy, competition2.get(i).getDx(), competition2.get(i).getDy()) < min1);
			{
				min1 = Settings.distance(dx, dy, competition2.get(i).getDx(), competition2.get(i).getDy());
				index = i;
			}
		}
		for (int i = 0; i < competition.size(); i++)
		{
			if (Settings.distance(dx, dy, competition.get(i).getDx(), competition.get(i).getDy()) < min2)
			{
				min2 = Settings.distance(dx, dy, competition.get(i).getDx(), competition.get(i).getDy());
				index2 = i;
			}
		}
		if (min1 > min2)
		{
			comp = false;
			return index2;
		}
		comp = true;
		return index;
	}
	
	

	//Creation structures
	
	
	public void createStatusBar() {
		HBox statusBar = new HBox();
		statusBar.getChildren().addAll(stats);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
	}

	
	private void createPlayer() {
	
		Castle c = new Castle(castlePlayerImg, playfieldLayer, "Joueur");
		player = new Player(input, c);
		world.add(c);

	}
	private void generateEnnemies()
	{
		for (int i = 0; i < Settings.NB_ENN_CASTLE; i++)
		{
			Castle c = new Castle(castleImg, playfieldLayer, world);
			world.add(c);
			}
	}
	
	private void generateNeutrals()
	{
		for (int i = 0; i < Settings.NB_NEUT_CASTLE; i++)
		{
			NeutralCastle n = new NeutralCastle(neutCastleImg, playfieldLayer, world);
			world.add(n);
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
	public boolean canCliq(long now)
	{
		return (now - lastCliq > 10000 * 10000);
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
			for (int i = 0; i < world.size(); i++)
			{
				if (world.get(i).getClass() == NeutralCastle.class)
				{
					world.get(i).setTreasure(world.get(i).getTreasure() + 4*world.get(i).getLevel());
				}
				if (world.get(i).getClass() == Castle.class)
					world.get(i).setTreasure(world.get(i).getTreasure() + 10*world.get(i).getLevel());
			}
			lastTurn = now;
			if (lastCastle != null)
				updateStatus(lastCastle);
		}
	}

	private void updateStatus(Castle castle)
	{
		if (option2 == true)
		{
			
		}
		else
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
	}

	//Options 
	public void options(Castle c)
	{
		//Attack
		if (c.getDx() > Settings.SCENE_WIDTH - 2*c.getWidth_Image())
			opt1 = new Options(playfieldLayer, "Attaquer", c.getDx() - c.getWidth_Image(), c.getDy()  , lastCastle);
		else
			opt1 = new Options(playfieldLayer, "Attaquer", c.getDx() + c.getWidth_Image(), c.getDy()  , lastCastle);
		
		if (c.getDuc() == "Joueur")
		{
			if (c.getDx() > Settings.SCENE_WIDTH - 2*c.getWidth_Image())
				opt2 = new Options(playfieldLayer, "Produire des unités", c.getDx() - c.getWidth_Image(), c.getDy() + c.getHeigth_Image()/2 - 20, lastCastle);
			else
				opt2 = new Options(playfieldLayer, "Produire des unités", c.getDx() + c.getWidth_Image(), c.getDy() + c.getHeigth_Image()/2 - 20, lastCastle);
			opt1.labelOption.setText("Envoyer des troupes");
			opt1.setLabel("Envoyer des troupes");
		}
	}
	
	public void optionMenu(Options opt)
	{
		if (opt.getLabel().equals("Attaquer"))
		{
			stats.setText(opt.getLabel() + " sur " + lastCastle.getDuc() + Settings.SSBLANK + "Piquiers : " + Settings.SBLANK + 
					" | Chevaliers : " + Settings.SBLANK + " | Onagres : " + Settings.SBLANK);
		}
		if (opt.getLabel().equals("Envoyer des troupes"))
		{
			stats.setText(opt.getLabel() + " sur " + lastCastle.getDuc() + Settings.SSBLANK + "Piquiers : " + Settings.SBLANK + 
					" | Chevaliers : " + Settings.SBLANK + " | Onagres : " + Settings.SBLANK);
		}
		else
		{
			stats.setText(opt.getLabel());
		}
		opt1.removeFromLayer();
		if (opt2 != null)
			opt2.removeFromLayer();
		opt1 = null;
		opt2 = null;
		option2 = true;
	}

	public static void main(String[] args) {
		launch(args);
	}

}