package Game;

import java.util.*;

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

public class Main extends Application {
	
	//javafx structures
	private Pane playfieldLayer;
	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
	private Image castlePlayerImg;
	private Image castleImg;
	private Image neutCastleImg;
	private Image pauseImg;                          

	private Player player;
	public Castle lastCastle = null;
	
	private ArrayList<Castle> world = new ArrayList<Castle>();
	private ArrayList<Ennemy> ennemies = new ArrayList<Ennemy>();
	
	private ArrayList<Castle> competition = new ArrayList<Castle>();
	private ArrayList<Options> competition2 = new ArrayList<Options>();
	
	private ArrayList<ArrayList<Troops>> armies = new ArrayList<ArrayList<Troops>>();
	private ArrayList<Options> arrayOptions = new ArrayList<Options>();
	private ArrayList<Options> arrayOptions2 = new ArrayList<Options>();
	private ArrayList<Button> arrayButtons = new ArrayList<Button>();
	
	private Text stats = new Text();
	private HBox hPause = new HBox();
	
	private ZoneText z;
	
	private boolean option = false;
	private boolean option2 = false;
	private boolean comp = false;

	public Random r = new Random();
	
	private boolean pause = false;
	private long lastPause = 0;
	private long lastTurn = 0;

	Group root;

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
		Button bouton3 = new Button("A propos");
				
		vbox.setPadding(new Insets(30, 50, 30, 50));
		vbox.setAlignment(Pos.CENTER);
				
		Border borderMenu = new Border (new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4), new Insets(Settings.SCENE_WIDTH /3)));
		vbox.setBorder(borderMenu);
		vbox.getChildren().addAll(bouton1, bouton2, bouton3);
				
		bouton1.setOnAction(e ->{
			
		root.getChildren().clear();
		// create layers
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
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		root.getChildren().addAll(vbox);
	}

	private void loadGame() {
		castlePlayerImg = new Image(getClass().getResource("/images/castlePlayer.png").toExternalForm(), 200, 200, true, true);
		castleImg = new Image(getClass().getResource("/images/castle1.png").toExternalForm(), 100, 100, true, true);
		neutCastleImg = new Image(getClass().getResource("/images/neutCastle2.png").toExternalForm(), 100, 100, true, true);
		pauseImg = new Image(getClass().getResource("/images/pause.jpg").toExternalForm(), 100, 80, true, true);

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
	
	public void clear()
	{
		stats.setText("");
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
		if (min1 > min2 + 10)
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
		stats.setTranslateY(10);
		stats.translateYProperty();
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		this.playfieldLayer.getChildren().add(statusBar);
	}

	
	private void createPlayer() {
	
		Castle c = new Castle(castlePlayerImg, playfieldLayer, "Joueur");
		player = new Player(c);
		world.add(c);

	}
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
			{
				pause = false;
				playfieldLayer.getChildren().remove(hPause);
			}
			else
			{
				pause = true;
				pauseGame();
			}
		}
	}
	
private void pauseGame() {
		
		//hbox.setPrefSize(Pos.CENTER);
		hPause.setPadding(new Insets(Settings.SCENE_WIDTH/3 + 35));
		//hbox.setAlignment(Pos.CENTER);
		hPause.getStyleClass().add("imageViewPause");
		ImageView imageViewPause = new ImageView(pauseImg);
		
		/*
		 * Text message = new Text(); message.getStyleClass().add("message");
		 * message.setText("Vous Ãªtes en Pause !");
		 */
		hPause.getChildren().add(imageViewPause);
		playfieldLayer.getChildren().add(hPause);
	}

	
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

	
	private void attack(Castle source, Castle target)
	{
		armies.add(source.instanceTroops(target));
	}
	
	
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
					//sources.remove(i);
					//targets.remove(i);
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
			
		}
		for (int i = 0; i < arrayOptions.size(); i++)
		{
			arrayOptions.get(i).removeFromLayer();
		}
		arrayOptions.clear();
		option2 = true;
	}

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
	public static void main(String[] args) {
		launch(args);
	}

}