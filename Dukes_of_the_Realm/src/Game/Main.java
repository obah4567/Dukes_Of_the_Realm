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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.*;
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

	private Player player;
	public Castle lastCastle = null;
	
	private ArrayList<Castle> world = new ArrayList<Castle>();
	private ArrayList<Castle> competition = new ArrayList<Castle>();
	private ArrayList<Options> competition2 = new ArrayList<Options>();
	
	private ArrayList<ArrayList<Troops>> armies = new ArrayList<ArrayList<Troops>>();
	
	private ArrayList<Options> arrayOptions = new ArrayList<Options>();
	
	private Text stats = new Text();
	
	private ZoneText z;
	
	private boolean option = false;
	private boolean option2 = false;
	private boolean comp = false;

	private Options opt1;
	private Options opt2;

	public Random r = new Random();
	
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
				if (option2)
				{
					if (Settings.distance(e.getX(), e.getY(), arrayOptions.get(4).getDx(), 
							arrayOptions.get(4).getDy()) < arrayOptions.get(4).getBackground().getHeight())
						competition2.add(arrayOptions.get(4));
						
					if (Settings.distance(e.getX(), e.getY(), arrayOptions.get(5).getDx(), 
							arrayOptions.get(5).getDy()) < arrayOptions.get(5).getBackground().getHeight())
						competition2.add(arrayOptions.get(5));
				}
				if (option)
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
							e.getX(), e.getY()) < intermediaire.getWidth_Image()/2)
						competition.add(intermediaire);
				}
				int index = handleCompetition(e.getX(), e.getY());
				if (comp)
				{
					if (!competition2.isEmpty())
					{
						if (competition2.get(index).getC() != null)
								optionMenu(competition2.get(index));
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
								player.getListCastle().get(0).sendOrder(lastCastle, pyk, kni, ona);
								attack(player.getListCastle().get(0), lastCastle);
								z.removeFromLayer();
								for (int i = 0; i < arrayOptions.size(); i++)
									arrayOptions.get(i).removeFromLayer();
								arrayOptions.clear();
								z = null;
								option2 = false;
								option = false;
								
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
							option = true;
							options(intermediate);
							option2 = false;
						}
						else
						{
							if (option2)
							{
								if (z!= null)
									z.removeFromLayer();
								for (int i = 0; i < arrayOptions.size(); i++)
									arrayOptions.get(i).removeFromLayer();
								arrayOptions.clear();
							}
							z = null;
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
			{
				try
				{	
					updateStatus(lastCastle);
				}
				catch(Game_Exception g)
				{
					
				}
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
				" Produit : " +	castle.getProduction().getProducts() + 
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
					for (int j = 0; j < armies.get(i).size(); j++)
					{
						//
						Troops intermediate = armies.get(i).get(j);
						if (Settings.distance(intermediate.getSrc().getDx(), intermediate.getSrc().getDy(), intermediate.getRectangle().getX(), intermediate.getRectangle().getY()) < intermediate.getSrc().getWidth_Image()/4)
						{
							if (nbCrossingGate < Settings.SIZEGATE)
							{
								if (intermediate.getTarget().getDx() > intermediate.getSrc().getDx())
								{
									intermediate.getRectangle().setX(intermediate.getSrc().getDx() +
											intermediate.getSrc().getWidth_Image()/2);
									intermediate.getRectangle().setY(intermediate.getSrc().getDy() + 
											intermediate.getSrc().getHeigth_Image()/2 - 3*nbCrossingGate);		
								}
								else
								{
									intermediate.getRectangle().setX(intermediate.getSrc().getDx() -
											intermediate.getSrc().getWidth_Image()/2);
									intermediate.getRectangle().setY(intermediate.getSrc().getDy() + 
											intermediate.getSrc().getHeigth_Image()/2 - 5*nbCrossingGate);
								}
								nbCrossingGate++;
							}
						}
						else
						{
							/*intermediate.setA((intermediate.getSrc().getDy() - intermediate.getRectangle().getY())/
								(intermediate.getSrc().getDx() - intermediate.getRectangle().getX())); 
							
							intermediate.setB(intermediate.getSrc().getDy() - intermediate.getA() * intermediate.getSrc().getDx());
							
							if (intermediate.getTarget().getDx() > intermediate.getRectangle().getX())
							{
								intermediate.getRectangle().setX(intermediate.getRectangle().getX() + intermediate.getSpeed());
								intermediate.getRectangle().setY(intermediate.getA() * (intermediate.getRectangle().getX() + intermediate.getSpeed()) + intermediate.getB());
							}
							else
							{
								intermediate.getRectangle().setX(intermediate.getRectangle().getX() - intermediate.getSpeed());
								intermediate.getRectangle().setY(intermediate.getA() * (intermediate.getRectangle().getX() - intermediate.getSpeed()) + intermediate.getB());
							}*/
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
			for (int j = 0; j < armies.get(i).size(); j++)
			{
				Troops intermediate = armies.get(i).get(j);
				if (Settings.distance(intermediate.getRectangle().getX(), intermediate.getRectangle().getY(), 
						intermediate.getTarget().getDx(), intermediate.getTarget().getDy()) <intermediate.getTarget().getWidth_Image()/3)
				{
					if (intermediate.getSrc().getDuc().equals(intermediate.getTarget().getDuc()))
					{
						if (intermediate.getClass() == Pikeman.class)
						{
							intermediate.getTarget().setTroops0(intermediate.getTarget().getTroops()[0] + 1);
							intermediate.removeFromLayer();
							armies.get(i).remove(j);
						}
						if (armies.get(i).get(j).getClass() == Knights.class)
						{
							intermediate.getTarget().setTroops1(intermediate.getTarget().getTroops()[1] + 1);
							armies.get(i).remove(j);
						}
						if (intermediate.getClass() == Onager.class)
						{
							intermediate.getTarget().setTroops2(intermediate.getTarget().getTroops()[2] + 1);
							armies.get(i).remove(j);
						}		
					}
					else
					{
						if (intermediate.getTarget().getDef() == null)
						{
							if (intermediate.getTarget().defend())
							{
								intermediate.getTarget().setDuc(intermediate.getSrc().getDuc());
								return;
							}
						}
						if (dammage(intermediate.getDammages(), intermediate.getTarget()))//ligne de défense vide
						{
							if (intermediate.getTarget().defend())
							{
								intermediate.getTarget().setDuc(intermediate.getSrc().getDuc());
								return;
							}
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

	//Options 
	public void options(Castle c)
	{
		if (opt1 != null)
			opt1.removeFromLayer();
		opt1 = new Options(playfieldLayer, "Attaquer", c.getDx(), c.getDy()  , lastCastle);
		
		if (c.getDuc() == "Joueur")
		{
			if (opt2 != null)
				opt2.removeFromLayer();
			opt2 = new Options(playfieldLayer, "Produire des unités", c.getDx(), c.getDy() + 41, lastCastle);
			opt1.setLabel("Envoyer des troupes");
		}
	}
	
	public void optionMenu(Options opt)
	{
		if (opt.getLabel().equals("Attaquer") || opt.getLabel().equals("Envoyer des troupes"))
		{
			if (lastCastle.getDx() > Settings.SCENE_WIDTH - 550) //The castle is near the edge. to get more visibility, the options shall be displayed on the left side of the castle
			{
				arrayOptions.add(new Options(playfieldLayer, "Vos troupes", opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 90 , opt.getC().getDy() -41, opt.getC(), 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Piquiers : " + player.getListCastle().get(0).getTroops()[0], opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 75, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Chevaliers  : " + player.getListCastle().get(0).getTroops()[1], opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 227, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Onagres  : " + player.getListCastle().get(0).getTroops()[2], opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 379, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Clear", opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 300, opt.getC().getDy() + 82));
				arrayOptions.add(new Options(playfieldLayer, "Ok", opt.getC().getDx() - opt.getC().getWidth_Image()/2 - 60, opt.getDy() + 82));
			}
			else
			{
				arrayOptions.add(new Options(playfieldLayer, "Vos troupes", opt.getC().getDx() + opt.getC().getWidth_Image() + 75, opt.getC().getDy() -41, lastCastle, 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Piquiers : " + player.getListCastle().get(0).getTroops()[0], opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 75, opt.getC().getDy(), opt.getC(), 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Chevaliers  : " + player.getListCastle().get(0).getTroops()[1], opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 227, opt.getDy(), opt.getC(), 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Onagres  : " + player.getListCastle().get(0).getTroops()[2], opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 379, opt.getDy(), opt.getC(), 150, 40));
				arrayOptions.add(new Options(playfieldLayer, "Clear", opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 300, opt.getC().getDy() + 82));
				arrayOptions.add(new Options(playfieldLayer, "Ok", opt.getC().getDx() + opt.getC().getWidth_Image()/2 + 60, opt.getC().getDy() + 82));
			}
			z = new ZoneText(playfieldLayer, opt.getC(), opt.getC().getDx(), opt.getC().getDy());
		}
		/*if (opt.getLabel().equals("Envoyer des troupes"))
		{
			arrayOptions.add(new Options(playfieldLayer, "Vos troupes", opt.getDx() + 15, opt.getDy() -40, opt.getC(), 150, 40));
			arrayOptions.add(new Options(playfieldLayer, "Piquiers : " + player.getListCastle().get(0).getTroops()[0], opt.getDx(), opt.getDy(), opt.getC(), 160, 40));
			arrayOptions.add(new Options(playfieldLayer, "Chevaliers  : " + opt.getC().getTroops()[1], opt.getDx() + 160, opt.getDy(), opt.getC(), 170, 40));
			arrayOptions.add(new Options(playfieldLayer, "Onagres  : " + opt.getC().getTroops()[2], opt.getDx() + 330, opt.getDy(), opt.getC(), 160, 40));
			z = new ZoneText(playfieldLayer, lastCastle, player.getListCastle().get(0), opt.getDx(), opt.getDy());
		}*/
		if (opt.getLabel().equals("Produire des unités"))
		{
			arrayOptions.add(new Options(playfieldLayer, "Vous souhaitez produire ", opt.getDx() + 15, opt.getDy() - 40, opt.getC(), 200, 40));
		}
		if (opt1 != null)
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