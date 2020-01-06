package Game;

import java.util.*;
import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Random;

/*
 * This file is part of Dukes_Of_the_Realm.
 */

public class Main extends Application {
	
	/*
	 * this is the main part of Dukes_of_the_Realm
	 * it orchestrates every components and bouds between each other
	 */
	//javafx structures
	
	/*
	 * Pane of the game
	 */
	private Pane playfieldLayer;
	
	/*
	 * Scene of the game
	 */
	private Scene scene;
	
	/*
	 * input of the game
	 */
	private Input input;
	
	/*
	 * loop of the game
	 */
	private AnimationTimer gameLoop;
	
	/*
	 * image of player's castle
	 */
	private Image castlePlayerImg;
	
	/*
	 * image of ennemy' castle
	 */
	private Image castleImg;
	
	/*
	 * Images of neutral baron's castle
	 */
	private Image neutCastleImg;
	
	/*
	 * variable of the player
	 */
	private Player player;
	
	/*
	 * this variable contains the last Castle clicked
	 */
	public Castle lastCastle = null;
	
	/*
	 * contains every Castle 
	 */
	private ArrayList<Castle> world = new ArrayList<Castle>();
	
	/*
	 * contains every enemies
	 */
	private ArrayList<Ennemy> ennemies = new ArrayList<Ennemy>();
	
	/*
	 * contains every possible clicked Castle
	 */
	private ArrayList<Castle> competition = new ArrayList<Castle>();
	
	/*
	 * contains every possible clicked Options
	 */
	private ArrayList<Options> competition2 = new ArrayList<Options>();
	
	/*
	 * contains every armies. an army is a collection of troops
	 */
	private ArrayList<ArrayList<Troops>> armies = new ArrayList<ArrayList<Troops>>();
	
	/*
	 * Contains the first set of Options
	 */
	private ArrayList<Options> arrayOptions = new ArrayList<Options>();
	
	/*
	 * contains the second set of Options
	 */
	private ArrayList<Options> arrayOptions2 = new ArrayList<Options>();
	
	/*
	 * contains a set of Button
	 */
	private ArrayList<Button> arrayButtons = new ArrayList<Button>();
	
	/*
	 * contains a message that display the stats of lastCastle
	 */
	private Text stats = new Text();
	
	/*
	 * contains the pause image
	 */
	private HBox hPause ;
	
	/*
	 * instantiate the TextField
	 */
	private ZoneText z;
	
	/*
	 * witness of the first set of Options
	 */
	private boolean option = false;
	
	/*
	 * witness of the second set of Options
	 */
	private boolean option2 = false;
	
	/*
	 * indicates what was closer to the cliq, a Castle or an Options
	 */
	private boolean comp = false;

	/*
	 * a tool to generate random numbers
	 */
	public Random r = new Random();
	
	/*
	 * witness of pause state
	 */
	private boolean pause = false;
	
	/*
	 * witness of last pause
	 */
	private long lastPause = 0;
	
	/*
	 * witness of last turn
	 */
	private long lastTurn = 0;

	//Open file, if game was save previously
	File file = new File("saveGameFile.txt");
	
	/*
	 * root of game's structure
	 */
	Group root;

	/*
	 * Instantiate all the games structures. starts the gameLoop
	 * @param main stage of the game
	 * In pause if boolean pause is true, not in pause if pause is false
	 * 
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		primaryStage.setTitle("Dukes of the Realm");
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT, Color.BISQUE);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		VBox vbox = new VBox (13);
		
		Button bouton1 = new Button("Nouvelle Partie");
		Button bouton2 = new Button("Reprendre une Session");
						
		vbox.setPadding(new Insets(30, 50, 30, 50));
		vbox.setAlignment(Pos.CENTER);
						
		Border borderMenu = new Border (new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4), new Insets(Settings.SCENE_WIDTH /3)));
		vbox.setBorder(borderMenu);
		vbox.getChildren().addAll(bouton1, bouton2);
						
		//Buttom for loadgame, start a new game	
		bouton1.setOnAction(e ->{
							
		root.getChildren().clear();
		playfieldLayer = new Pane();
		root.getChildren().addAll(playfieldLayer);


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
					update(now);
				}
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
		});
		//Button for open game was save previously
		bouton2.setOnAction(e ->{
			try {
				input.ouvrirGame(file);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		root.getChildren().addAll(vbox);
	}

	/*
	 * load the images, generate the player, enemies, neutral barons and display them on playfieldLayer
	 * Create a statusBar
	 * scene is analyzed, check which object is the closest from the click then
	 * take appropriate actions.
	 */
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
				if (option2)
				{
					if (Settings.distance(e.getX(), e.getY(), arrayOptions2.get(arrayOptions2.size()-2).getDx(), 
							arrayOptions2.get(arrayOptions2.size()-2).getDy()) < arrayOptions2.get(arrayOptions2.size()-2).getBackground().getHeight())
						competition2.add(arrayOptions2.get(arrayOptions2.size()-2));
						
					if (Settings.distance(e.getX(), e.getY(), arrayOptions2.get(arrayOptions2.size()-1).getDx(), 
							arrayOptions2.get(arrayOptions2.size()-1).getDy()) < arrayOptions2.get(arrayOptions2.size()-1).getBackground().getHeight())
						competition2.add(arrayOptions2.get(arrayOptions2.size()-1));
				}
				if (option)
				{
					if (!arrayOptions.isEmpty())
					{
						for (int i = 0; i < arrayOptions.size(); i++)
						{
							if (Settings.distance(e.getX(), e.getY(), arrayOptions.get(i).getDx(), arrayOptions.get(i).getDy()) < arrayOptions.get(i).getBackground().getHeight())
								competition2.add(arrayOptions.get(i));
						}
					}
				}
			
				for (int i = 0; i < world.size(); i++)
				{
					Castle intermediaire = world.get(i);
					if (Settings.distance(intermediaire.getDx(), intermediaire.getDy(), 
							e.getX(), e.getY()) < intermediaire.getWidth_Image()/2)
						competition.add(intermediaire);
				}
				int index = handleCompetition(e.getX(), e.getY());
				if (comp)
				{
					if (!competition2.isEmpty())
					{
						if (competition2.get(index).getC() != null)
						{
							if (competition2.get(index).getLabel().equals("Level UP !"))
							{
								if (!lastCastle.product("level", lastCastle.getLevel()*10))
									arrayOptions.add(new Options(playfieldLayer, "Vous ne pouvez pas pour l'instant", lastCastle.getDx() + lastCastle.getWidth_Image(), lastCastle.getDy() + 122, lastCastle, 300, 40));
								else
								{
									for (int i = 0; i < arrayOptions.size(); i++)
									{
										arrayOptions.get(i).removeFromLayer();
									}
									arrayOptions.clear();
									option = false;
									option2 = false;
								}
							}
							else
							{
								if (competition2.get(index).getLabel().equals("Nouvelle base"))
								{
									player.setBase(lastCastle);
									option = false; option2 = false;
									for (int i = 0; i < arrayOptions.size(); i++)
										arrayOptions.get(i).removeFromLayer();
									arrayOptions.clear();
								}
								else
								{
									optionMenu(competition2.get(index));
								}
							}
								
						}
						else
						{
							if (competition2.get(index).getLabel().equals("Clear"))
								{
									z.getPyk().clear();
									z.getKni().clear();
									z.getOna().clear();
								}
							if (competition2.get(index).getLabel().equals("Ok"))
							{
								int pyk, kni, ona;
								if (z.getTextPyk().equals(""))
									pyk = 0;
								else
									pyk = Integer.valueOf(z.getTextPyk());
								if (z.getTextKni().equals(""))
									kni = 0;
								else
									kni = Integer.valueOf(z.getTextKni());
								if (z.getTextOna().equals(""))
									ona = 0;
								else
									ona = Integer.valueOf(z.getTextOna());
								if (!lastCastle.getDuc().equals("Joueur"))
								{
									if (pyk > player.getBase().getTroops()[0] || kni > player.getBase().getTroops()[1] || ona > player.getBase().getTroops()[2])
									{
										arrayOptions2.add(new Options(playfieldLayer, "Veuillez saisir un nombre correcte", 
												lastCastle.getDx() - lastCastle.getWidth_Image()/2, lastCastle.getDy() + lastCastle.getHeigth_Image(), lastCastle, 250, 40));
									}
									else
									{
										if (player.getBase().getOrder().getTarget() == null)
										{
											player.getBase().sendOrder(lastCastle, pyk, kni, ona);
											attack(player.getBase(), lastCastle);
										}
										z.removeFromLayer();
										for (int i = 0; i < arrayOptions2.size(); i++)
											arrayOptions2.get(i).removeFromLayer();
										arrayOptions2.clear();
										z = null;
										option2 = false;
										option = false;
									}
								}
							}
						}
					}
				}
				else
				{
					if (!competition.isEmpty())
					{
						Castle intermediate = competition.get(index);
						if (lastCastle == intermediate)
						{
							if (option2)
							{
								for (int i = 0; i < arrayOptions2.size(); i++)
									arrayOptions2.get(i).removeFromLayer();
								arrayOptions2.clear();
								if (!arrayButtons.isEmpty())
								{
									for (int i = 0; i < arrayButtons.size(); i++)
										playfieldLayer.getChildren().remove(arrayButtons.get(i));
								}
							}
							option = true; option2 = false;
							options(intermediate);
						}
						else
						{
							if (option2)
							{
								if (z!= null)
									z.removeFromLayer();
								z = null;
								for (int i = 0; i < arrayOptions2.size(); i++)
									arrayOptions2.get(i).removeFromLayer();
								arrayOptions2.clear();
							}
							if (!arrayButtons.isEmpty())
							{
								playfieldLayer.getChildren().removeAll(arrayButtons.get(0), arrayButtons.get(1), arrayButtons.get(2));
								arrayButtons.clear();
							}
							z = null;
							option = false;
							option2 = false;
							lastCastle = intermediate;							
							if (!arrayOptions.isEmpty())
							{
								for (int i = 0; i < arrayOptions.size(); i++)
									arrayOptions.get(i).removeFromLayer();
								arrayOptions.clear();
							}
						}
					}
				}
				try
				{
				updateStatus(lastCastle);
				} catch(Game_Exception g)
				{
					
				}
				competition.removeAll(competition);
				competition2.removeAll(competition2);
				}});
		}

	
	/*
	 * Compare every objects close from the click in the ArrayList competition and competition2,
	 * and select the most probable one.
	 * 
	 * @param dx is the abscissa coordinate of the click.
	 * @param dy is the ordinate coordinate of the click.
	 * @return the index of the object, and use the boolean comp to tell in which collection he is. 
	 * 
	 * if comp is true, then it is an Options, if not it is a Castle
	 */
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
		if (min1 > min2 + 10)
		{
			comp = false;
			return index2;
		}
		comp = true;
		return index;
	}

	//Creation structures
		
	/*
	 * Generate the status Bar that shall display informations about lastCastle
	 */
	public void createStatusBar() {
		HBox statusBar = new HBox();
		statusBar.getChildren().addAll(stats);
		stats.setTranslateY(10);
		stats.translateYProperty();
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		this.playfieldLayer.getChildren().add(statusBar);
	}

	/*
	 * Generate the player, and add his castle in the ArrayList world
	 */
	private void createPlayer() {
	
		Castle c = new Castle(castlePlayerImg, playfieldLayer, "Joueur");
		player = new Player(c);
		world.add(c);
	}
	
	/*
	 * generate NB_ENN_CASTLE enemies. Add each castle to the world.
	 * If 2 castle have the same duc, the second castle is added to the list of castle 
	 * to the enemy.
	 * The enemy is added to the list of enemies if it's not in there already
	 */
	private void generateEnnemies()
	{
		for (int i = 0; i < Settings.NB_ENN_CASTLE; i++)
		{
			Castle c = new Castle(castleImg, playfieldLayer, world);
			world.add(c);
			if (ennemies.size() > 0)
			{
				for (int j = 0; j < ennemies.size(); j++)
				{
					if (ennemies.get(j).getListCastle().get(0).getDuc().equals(c.getDuc()))
						ennemies.get(j).getListCastle().add(c);
					else
						ennemies.add(new Ennemy(c, c.getDuc()));
				}
			}
			else
			{
				ennemies.add(new Ennemy(c, c.getDuc()));
			}
		}
	}
	/*
	 * generate the castle of neutrals barons. Add each castle to the world
	 */
	private void generateNeutrals()
	{
		for (int i = 0; i < Settings.NB_NEUT_CASTLE; i++)
		{
			NeutralCastle n = new NeutralCastle(neutCastleImg, playfieldLayer, world);
			world.add(n);
		}
	}
	
	/*
	 * Check if it is possible to use function pause
	 * @param now is the time right now.
	 * @return true if the game can pause,
	 * @return false if the game can not pause
	 */
	public boolean canPause(long now)
	{
		return (now - lastPause > 10000 * 10000);
	}
	
	/*
	 * pause or unpause the game.
	 * if pause is true, then it unpauses the game, if not it pauses it
	 * remove the pause image if it unpauses
	 * call pauseGame if it pauses
	 * @param now is the time right now
	 */
	public void pause(long now)
	{
		if (canPause(now))
		{
			lastPause = now;
			if (pause)
			{
				pause = false;
				hPause.getChildren().clear();
				playfieldLayer.getChildren().remove(hPause);
			}
			else
			{
				pause = true;
				pauseGame();
			}
		}
	}
	
	/*
	 * display the pause image
	 */
	private void pauseGame() {
		
VBox vboxPause = new VBox(15);
		
		Button bottonCont = new Button("Continuer la Partie");
		Button bottonSauveg = new Button("Sauvergarder la Partie");
		Button bottonQuit = new Button("Quittez la Partie");
		vboxPause.setPadding(new Insets(30, 50, 30, 50));
		vboxPause.setAlignment(Pos.CENTER);
				
		Border borderMenu = new Border (new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4), new Insets(Settings.SCENE_WIDTH /3)));
		vboxPause.setBorder(borderMenu);
		vboxPause.getChildren().addAll(bottonCont, bottonSauveg, bottonQuit);
		root.getChildren().addAll(vboxPause);
		//Buttom for continue game
		bottonCont.setOnAction(e ->{
			if (pause)
			{
				pause = false;
				root.getChildren().removeAll(vboxPause, hPause);
			}
			else
			{
				pause = true;
			}
		});
		//Button for save game
		bottonSauveg.setOnAction(e ->{
			try {
				input.sauvegardeGame();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		//Button for quit game
		bottonQuit.setOnAction(e ->{
			
			Platform.exit();
			System.exit(0);
			});
	}

	/*
	 * Display a message telling the player lost and game is over.
	 * stops gameLoop and everything
	 */
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
	
	/*
	 * this function update everything at every turn.
	 * if the time is right : a turn just happened, then it increased the gold in every Castle
	 * by the right amount, it updates the production, the position of troops, the strategy of enemies, and their
	 * actions. It also makes the troops fight if they're close enough form theirs targets.
	 * @param now is the time right now
	 * @Exception catch a Game_Exception if a click happens and lastCastle is still null 
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
			updateProductions();
			lastTurn = now;
			if (lastCastle != null)
			{
				try
				{	
					updateStatus(lastCastle);
				}
				catch(Game_Exception g)
				{
					g.printStackTrace();
				}
			}
			for (int i = 0; i < ennemies.size(); i++)
			{
				ennemies.get(i).think();
				ennemies.get(i).act(world, armies);
			}
			if (!armies.isEmpty())
			{
				moveTroops();
				clash();
			}
		}
	}
	
	/*
	 * displays in statusBar the informations about lastCastle
	 * @param castle is the lastCastle clicked
	 * throws a Game_Exception if castle is null
	 */
	private void updateStatus(Castle castle) throws Game_Exception
	{
		if (castle == null)
			throw new Game_Exception("Castle is null, updateStatus trying to show an order from a null castle");
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
				" Produit : " +	castle.getProduction().getProducts() + Settings.SSBLANK +
				" Temps restant : " + castle.getProduction().getTimeLeft() + 
				Settings.SBLANK + " Ordre : " + ordres
				+ Settings.SBLANK + " Porte : " + castle.getGate());
		
	}

	/*
	 * this add the army to armies sent from source to target.
	 * @param source is the castle from where the army comes from
	 * @param target is the castle where the army goes
	 */
	private void attack(Castle source, Castle target)
	{
		armies.add(source.instanceTroops(target));
	}
	
	/*
	 * this function makes troops move.
	 * If they're inside the castle, a specific number (gate size) come out
	 * and is placed on the side on the castle that faces the target (right or left side)
	 * if they got out of the castle, their position will change to be closer to the target 
	 * until they reach it
	 */
	private void moveTroops()
	{
		if (armies.isEmpty())
		{
			
		}
		else
		{
			for (int i = 0; i < armies.size(); i++)
			{
				if (armies.get(i).isEmpty())
				{
					armies.remove(i);
				}
				else
				{
					int nbCrossingGate = 0;
					for (Iterator<Troops> it = armies.get(i).iterator(); it.hasNext();)
					{
						//
						Troops intermediate = it.next();
						if (Settings.distance(intermediate.getSrc().getDx(), intermediate.getSrc().getDy(), intermediate.getRectangle().getX(), intermediate.getRectangle().getY()) < intermediate.getSrc().getWidth_Image()/8)
						{
							if (nbCrossingGate < Settings.SIZEGATE)
							{
								if (intermediate.getTarget().getDx() > intermediate.getSrc().getDx())
								{
									intermediate.getRectangle().setX(intermediate.getSrc().getDx() +
											intermediate.getSrc().getWidth_Image()/2);
									intermediate.getRectangle().setY(intermediate.getSrc().getDy() + 
											intermediate.getSrc().getHeigth_Image()/2 - 10 - 10*nbCrossingGate);		
								}
								else
								{
									intermediate.getRectangle().setX(intermediate.getSrc().getDx() -
											intermediate.getSrc().getWidth_Image()/2);
									intermediate.getRectangle().setY(intermediate.getSrc().getDy() + 
											intermediate.getSrc().getHeigth_Image()/2 - 10*nbCrossingGate);
								}
								nbCrossingGate++;
							}
						}
						else
						{
							if (intermediate.getTarget().getDx() > intermediate.getRectangle().getX())
							{
								intermediate.getRectangle().setX(intermediate.getRectangle().getX() + intermediate.getSpeed());
								if (intermediate.getTarget().getDy() > intermediate.getRectangle().getY())
								{
									intermediate.getRectangle().setY(intermediate.getRectangle().getY() + intermediate.getSpeed());
								}
								else
								{
									intermediate.getRectangle().setY(intermediate.getRectangle().getY() - intermediate.getSpeed());
								}
							}
							else
							{
								intermediate.getRectangle().setX(intermediate.getRectangle().getX() - intermediate.getSpeed());
								if (intermediate.getTarget().getDy() > intermediate.getRectangle().getY())
								{
									intermediate.getRectangle().setY(intermediate.getRectangle().getY() + intermediate.getSpeed());
								}
								else
								{
									intermediate.getRectangle().setY(intermediate.getRectangle().getY() - intermediate.getSpeed());
								}
							}
						}
					}
				}	
			}
		}
	}
	
	
	/*
	 * if troops are close enough to their targets, then they attack the castle.
	 * if the duc of the target and the duc of the troop are the same, then the troop is added to the troops of the castle
	 * if the target has a defense line, it deals its damages to one unit from the defense line, then die.
	 * if the target it calls defend(), if it still has no defense line, then the troops seize the castle.
	 * if a Herald happens to clash with castle, then he signs the end of the army, the end of the attack and the order from
	 * the source castle becomes null again.
	 */
	private void clash()
	{
		for (int i = 0; i < armies.size(); i++)
		{
			for (Iterator<Troops> it = armies.get(i).iterator(); it.hasNext();)
			{
				Troops intermediate = it.next();
				if (Settings.distance(intermediate.getRectangle().getX(), intermediate.getRectangle().getY(), 
						intermediate.getTarget().getDx(), intermediate.getTarget().getDy()) < intermediate.getTarget().getWidth_Image()* 0.55)
				{
					if (intermediate.getSrc().getDuc().equals(intermediate.getTarget().getDuc()))
					{
						if (intermediate.getClass() == Pikeman.class)
						{
							intermediate.getTarget().setTroops0(intermediate.getTarget().getTroops()[0] + 1);
							intermediate.removeFromLayer();
							it.remove();
						}
						if (intermediate.getClass() == Knights.class)
						{
							intermediate.getTarget().setTroops1(intermediate.getTarget().getTroops()[1] + 1);
							intermediate.removeFromLayer();
							it.remove();
						}
						if (intermediate.getClass() == Onager.class)
						{
							intermediate.getTarget().setTroops2(intermediate.getTarget().getTroops()[2] + 1);
							intermediate.removeFromLayer();
							it.remove();
						}
						if (intermediate.getClass() == Herald.class)
						{
							intermediate.removeFromLayer();
							intermediate.getSrc().getOrder().clear();
							it.remove();
							armies.remove(i);
						}
					}
					else
					{
						
						if (intermediate.getClass() == Herald.class)
						{
							intermediate.getTarget().clearDefense();
							intermediate.removeFromLayer();
							armies.get(i).clear();
							intermediate.getSrc().getOrder().clear();
							return;
						}
						else
						{
							if (intermediate.getTarget().getDef().isEmpty())
							{
								if (intermediate.getTarget().defend())
								{
									seizeCastle(intermediate.getSrc(), intermediate.getTarget());
									return;
								}
							}
							if (dammage(intermediate.getDammages(), intermediate.getTarget()))//ligne de défense vide
							{
								if (intermediate.getTarget().defend())
								{
									seizeCastle(intermediate.getSrc(), intermediate.getTarget());
									return;
								}
							}
							intermediate.removeFromLayer();
							it.remove();
						}
						
					}
				}
			}
		}
	}
	
	/*
	 * damages are dealt to the target's defense line
	 * if a unit from the defense line loses all its health, it is removed 
	 * from defense line. 
	 * @param dammages is the number of damaged dealt
	 * @param target is the target of the damages.
	 * @return true if the defense line is empty
	 * @return false if the defense line is not empty
	 */
	private boolean dammage(int dammages, Castle target)
	{
		ArrayList<Troops> def = target.getDef();
		if (def.isEmpty())
			return true;
		int unit = r.nextInt(def.size());
		def.get(unit).setHealth(dammages);
		if (def.get(unit).getHealth() < 1)
			def.remove(unit);
		return false;
	}
	
	/*
	 * the owner of src owns now target. it removes from the list of castle of target's duc target
	 * and it adds to list of castle of src's duc target.
	 * it also change the name of the duc from the castle. 
	 * @param src is the castle that seized the castle target from battle
	 * @param target is the castle seized by castle src
	 */
	public void seizeCastle(Castle src, Castle target)
	{
		if (src.getDuc().equals("Joueur"))
		{
			String ennemyName = target.getDuc();
			for (int i = 0; i < ennemies.size(); i++)
			{
				if (ennemies.get(i).getName().equals(ennemyName))
				{
					for (int j = 0; j < ennemies.get(i).getListCastle().size(); j++)
					{
						if (ennemies.get(i).getListCastle().get(j) == target)
							ennemies.get(i).getListCastle().remove(j);
					}
					if (ennemies.get(i).getListCastle().isEmpty())
					{
						ennemies.remove(i);
					}
				}
			}
			player.getListCastle().add(target);
			target.setDuc("Joueur");
		}
		else
		{
			if (target.getDuc().equals("Joueur"))
			{
				for (int i = 0; i < player.getListCastle().size(); i++)
				{
					if (player.getListCastle().get(i) == target)
						player.getListCastle().remove(i);
				}
				if (player.getListCastle().isEmpty())
					gameOver();
				if (target == player.getBase())
				{
					player.setBase(player.getListCastle().get(0));
				}
			}
			else
			{
				for (int i = 0; i < ennemies.size(); i++)
				{
					if (ennemies.get(i).getName().equals(target.getDuc()))
					{
						for (int j = 0; j < ennemies.get(i).getListCastle().size(); j++)
						{
							if (ennemies.get(i).getListCastle().get(j) == target)
								ennemies.get(i).getListCastle().remove(j);
						}
						if (ennemies.get(i).getListCastle().isEmpty())
							ennemies.remove(i);
					}
					if (ennemies.get(i).getName().equals(src.getDuc()))
						ennemies.get(i).getListCastle().add(target);
				}
				target.setDuc(src.getDuc());
			}
			
		}
	}
	
	//Options 
	
	/*
	 * this displays the first set of options available for the lastCastle which are
	 * "attaquer"/"envoyer des troupes", "produire des unités", "level UP" !, and "nouvelle base".
	 * transform attaquer into envoyer des troupes if the castle is one from the player
	 * if there are already other options displayed they are removed
	 * @param c is the castle that gets the options displayed
	 */
	public void options(Castle c)
	{
		if (!arrayOptions.isEmpty())
		{
			for (int i = 0; i < arrayOptions.size(); i++)
			{
				arrayOptions.get(i).removeFromLayer();
			}
			arrayOptions.clear();
		}
		arrayOptions.add(new Options(playfieldLayer, "Attaquer", c.getDx(), c.getDy()  , lastCastle));
		
		if (c.getDuc().equals("Joueur"))
		{
			arrayOptions.add(new Options(playfieldLayer, "Produire des unités", c.getDx(), c.getDy() + 41, c));
			if (player.getBase() != c)
				arrayOptions.add(new Options(playfieldLayer, "Nouvelle base", c.getDx(), c.getDy() -41, c));
			arrayOptions.add(new Options(playfieldLayer, "Level UP !", c.getDx(), c.getDy() + 82, c));
			arrayOptions.get(0).setLabel("Envoyer des troupes");
		}
	}
	
	/*
	 * this displays the second set of options according to the options clicked.
	 * if it was "attaquer"/"Envoyer des troupes" then it shows how many troops the player has
	 * in base, instantiate z, that shall displays TextFields and display 2 "buttons" which are "Clear"
	 * that clear the TextFields and "Ok" that confirms the order is sent and the army is also sent.
	 * if the numbers in TextFields are incorrect values a window show up and notify the player.
	 * if it was "produire des unités" then it shows the statistics of each units and calls createButtons()
	 * if it was "nouvelle base" then the function switch the castle base from the player
	 * the function also removes the precedent set of options
	 * @param opt is the Options that got clicked by the player.
	 */
	public void optionMenu(Options opt)
	{
		if (opt.getLabel().equals("Attaquer") || opt.getLabel().equals("Envoyer des troupes"))
		{
			if (lastCastle.getDx() > Settings.SCENE_WIDTH - 550) //The castle is near the edge. to get more visibility, the options shall be displayed on the left side of the castle
			{
				arrayOptions2.add(new Options(playfieldLayer, "Vos troupes", opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 90 , opt.getC().getDy() -41, opt.getC(), 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Piquiers : " + player.getBase().getTroops()[0], opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 75, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Chevaliers  : " + player.getBase().getTroops()[1], opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 227, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Onagres  : " + player.getBase().getTroops()[2], opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 379, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Clear", opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 300, opt.getC().getDy() + 82));
				arrayOptions2.add(new Options(playfieldLayer, "Ok", opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 60, opt.getDy() + 82));
			}
			else
			{
				arrayOptions2.add(new Options(playfieldLayer, "Vos troupes", opt.getC().getDx() + opt.getC().getWidth_Image() + 75, opt.getC().getDy() -41, lastCastle, 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Piquiers : " + player.getBase().getTroops()[0], opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 75, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Chevaliers  : " + player.getBase().getTroops()[1], opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 227, opt.getDy(), opt.getC(), 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Onagres  : " + player.getBase().getTroops()[2], opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 379, opt.getDy(), opt.getC(), 150, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Clear", opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 300, opt.getC().getDy() + 82));
				arrayOptions2.add(new Options(playfieldLayer, "Ok", opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 60, opt.getC().getDy() + 82));
			}
			z = new ZoneText(playfieldLayer, opt.getC(), opt.getC().getDx(), opt.getC().getDy());
		}
		if (opt.getLabel().equals("Produire des unités"))
		{
			if (lastCastle.getDx() > Settings.SCENE_WIDTH - 570)
			{
				arrayOptions2.add(new Options(playfieldLayer, "Piquiers : Coût : " + Settings.COST_PRODUCTION_PIKEMAN + " | Temps de prod : " + Settings.TIME_COST_PIKEMAN + 
						" | Sante : " + Settings.HEALTH_PIKEMAN + " | Dommages : " + Settings.DAMMAGES_PIKEMAN + " | Vitesse : " + Settings.SPEED_PIKEMAN, opt.getC().getDx() - opt.getC().getWidth_Image() - 185, 
						opt.getC().getDy() - 41, opt.getC(), 570, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Chevaliers : Coût : " + Settings.COST_PRODUCTION_KNIGHT + " | Temps de prod : " + Settings.TIME_COST_KNIGHT + 
						" | Sante : " + Settings.HEALTH_KNIGHT + " | Dommages : " + Settings.DAMMAGES_KNIGHT + " | Vitesse : " + Settings.SPEED_KNIGHT, opt.getC().getDx() - opt.getC().getWidth_Image() - 185, 
						opt.getC().getDy(), opt.getC(), 570, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Onagres : Coût : " + Settings.COST_PRODUCTION_ONAGER + " | Temps de prod : " + Settings.TIME_COST_ONAGER + 
						" | Sante : " + Settings.HEALTH_ONAGER + " | Dommages : " + Settings.DAMMAGES_ONAGER + " | Vitesse : " + Settings.SPEED_ONAGER, opt.getC().getDx() -opt.getC().getWidth_Image() - 185, 
						opt.getC().getDy() + 41, opt.getC(), 570, 40));
				createButtons();	
				
			}
			else
			{
				arrayOptions2.add(new Options(playfieldLayer, "Piquiers : Coût : " + Settings.COST_PRODUCTION_PIKEMAN + " | Temps de prod : " + Settings.TIME_COST_PIKEMAN + 
						" | Sante : " + Settings.HEALTH_PIKEMAN + " | Dommages : " + Settings.DAMMAGES_PIKEMAN + " | Vitesse : " + Settings.SPEED_PIKEMAN, opt.getC().getDx()+ opt.getC().getWidth_Image()/2 + 290, 
						opt.getC().getDy() - 41, opt.getC(), 570, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Chevaliers : Coût : " + Settings.COST_PRODUCTION_KNIGHT + " | Temps de prod : " + Settings.TIME_COST_KNIGHT + 
						" | Sante : " + Settings.HEALTH_KNIGHT + " | Dommages : " + Settings.DAMMAGES_KNIGHT + " | Vitesse : " + Settings.SPEED_KNIGHT, opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 290, 
						opt.getC().getDy(), opt.getC(), 570, 40));
				arrayOptions2.add(new Options(playfieldLayer, "Onagres : Coût : " + Settings.COST_PRODUCTION_ONAGER + " | Temps de prod : " + Settings.TIME_COST_ONAGER + 
						" | Sante : " + Settings.HEALTH_ONAGER + " | Dommages : " + Settings.DAMMAGES_ONAGER + " | Vitesse : " + Settings.SPEED_ONAGER, opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 290, 
						opt.getC().getDy() + 41, opt.getC(), 570, 40));
				createButtons();
				
			}
			
		}		
		if (opt.getLabel().equals("Nouvelle base"))
		{
			player.setBase(opt.getC());
		}
		for (int i = 0; i < arrayOptions.size(); i++)
		{
			arrayOptions.get(i).removeFromLayer();
		}
		arrayOptions.clear();
		option2 = true;
	}
	
	/*
	 * happens when the user clicked "Produire des unités", 3 buttons are displayed
	 * the user has to click on these buttons if he wish to produces a unit, but if he has
	 * not enough gold to produce that unit, then a window pops up and tells him he can't.
	 * This window also pops up if he is already producing something
	 * if he can produces a unit, it changes the Production from the lastCastle and it puts
	 * the right unit and the right time left before production is over
	 * it also removes the second set of Options and the buttons
	 */
	public void createButtons()
	{
		arrayButtons.add(new Button("Piquier"));
		arrayButtons.add(new Button("Chevalier"));
		arrayButtons.add(new Button("Onagre"));
		
		for (int i = 0; i < 3; i ++)
		{
			arrayButtons.get(i).relocate(arrayOptions2.get(0).getDx() + 10 + 101*i, lastCastle.getDy() + 62);
			arrayButtons.get(i).setPrefWidth(90);
			arrayButtons.get(i).setPrefHeight(50);
			arrayButtons.get(i).setFont(new Font(15));
			playfieldLayer.getChildren().add(arrayButtons.get(i));
		}
		arrayButtons.get(0).setOnAction(e -> {
			if (!lastCastle.product("Piquier", Settings.TIME_COST_PIKEMAN))
			{
				arrayOptions2.add(new Options(playfieldLayer, "Vous ne pouvez produire cette unité pour le moment", 
						lastCastle.getDx() + lastCastle.getWidth_Image()/2, lastCastle.getDy() - lastCastle.getHeigth_Image()/2 - 10, lastCastle, 400, 40));
			}
			else
			{
				for (int i = 0; i < arrayButtons.size(); i++)
				{
					playfieldLayer.getChildren().remove(arrayButtons.get(i));
				}
				for (int i = 0; i < arrayOptions2.size(); i++)
				{
					arrayOptions2.get(i).removeFromLayer();
				}
				arrayButtons.clear();
				arrayOptions2.clear();
				option2 = false;
			}
		});
		arrayButtons.get(1).setOnAction(e -> {
			if (!lastCastle.product("Chevalier", Settings.TIME_COST_KNIGHT))
			{
				arrayOptions2.add(new Options(playfieldLayer, "Vous ne pouvez produire cette unité pour le moment", 
						lastCastle.getDx() + lastCastle.getWidth_Image()/2, lastCastle.getDy() - lastCastle.getHeigth_Image()/2 - 10, lastCastle, 400, 40));
			}
			else
			{
				for (int i = 0; i < arrayButtons.size(); i++)
				{
					playfieldLayer.getChildren().remove(arrayButtons.get(i));
				}
				for (int i = 0; i < arrayOptions2.size(); i++)
				{
					arrayOptions2.get(i).removeFromLayer();
				}
				arrayButtons.clear();
				arrayOptions2.clear();
				option2 = false;
			}
		});
		arrayButtons.get(2).setOnAction(e -> {
			if (!lastCastle.product("Onagre", Settings.TIME_COST_ONAGER))
			{
				arrayOptions2.add(new Options(playfieldLayer, "Vous ne pouvez produire cette unité pour le moment", 
						lastCastle.getDx() + lastCastle.getWidth_Image()/2, lastCastle.getDy() - lastCastle.getHeigth_Image()/2 - 10, lastCastle, 400, 40));
			}
			else
			{
				for (int i = 0; i < arrayButtons.size(); i++)
				{
					playfieldLayer.getChildren().remove(arrayButtons.get(i));
				}
				for (int i = 0; i < arrayOptions2.size(); i++)
				{
					arrayOptions2.get(i).removeFromLayer();
				}
				arrayButtons.clear();
				arrayOptions2.clear();
				option2 = false;
			}
		});
	}
	
	/*
	 * this function reduces by 1 the time left from the productions of every castle if it was
	 * producing something. 
	 * if the time left reaches 0, and there was something that was produced, then the unit,
	 * or the level is incremented.
	 */
	public void updateProductions()
	{
		for (int i = 0; i < world.size(); i++)
		{
			if (!world.get(i).getProduction().getProducts().equals("rien"))
			{
				if (world.get(i).getProduction().getTimeLeft() == 0)
				{
					if (world.get(i).getProduction().getProducts().equals("Piquier"))
						world.get(i).setTroops0(1);
					if (world.get(i).getProduction().getProducts().equals("Chevalier"))
						world.get(i).setTroops1(1);
					if (world.get(i).getProduction().getProducts().equals("Onagre"))
						world.get(i).setTroops2(1);
					if (world.get(i).getProduction().getProducts().equals("level"))
						world.get(i).setLevel(world.get(i).getLevel() + 1);
					world.get(i).getProduction().setProducts("rien");
				}
				else
				{
					world.get(i).getProduction().setTimeLeft(world.get(i).getProduction().getTimeLeft() - 1);
				}
			}
		}
	}
	/*
	 * launch the game
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}