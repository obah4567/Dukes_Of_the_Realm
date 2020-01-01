package Game;

import java.util.ArrayList;
//import java.util.List;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.*;
import java.util.Random;

public class Main extends Application {
	
	HBox hbox = new HBox();
	
	//javafx structures
	private Pane playfieldLayer;
	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
	private Image castlePlayerImg;
	private Image castleImg;
	private Image neutCastleImg;
	private Image tresorImg, chevalierImg, royaumImg, onagreImg, piqImg, pauseImg;

	ImageView imageViewPause;
	
	private Player player;
	public Castle lastCastle = null;
	//private NeutralCastle lastNeutral = null;
	
	private ArrayList<Castle> world = new ArrayList<Castle>();
	private ArrayList<Castle> competition = new ArrayList<Castle>();
	private ArrayList<Options> competition2 = new ArrayList<Options>();
	
	private ArrayList<ArrayList<Troops>> armies = new ArrayList<ArrayList<Troops>>();
	private ArrayList<Castle> sources = new ArrayList<Castle>();
	private ArrayList<Castle> targets = new ArrayList<Castle>();
	
	private ArrayList<Options> arrayOptions = new ArrayList<Options>();
	
	private Text stats = new Text();
	//private Text stats1 = new Text();
	private Text stats2 = new Text();
	private Text stats3 = new Text();
	private Text stats4 = new Text();
	private Text stats5 = new Text();
	
	private ZoneText z;
	
	private boolean option = false;
	private boolean option2 = false;
	private boolean comp = false;

	private Options opt1;
	private Options opt2;
	
	//private void hpause = null ;

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
		///
		
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
			playfieldLayer = new Pane();
			root.getChildren().addAll(playfieldLayer);
			
			loadGame();

			gameLoop = new AnimationTimer() {
				@Override
				public void handle(long now) {
					processInput(input, now);
					if (pause == true)
					{
						pauseGame();
					}
					else
					{
						pause = false;
						hbox.getChildren().clear();
						//opt1.removeFromLayer();
						
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
		
		///
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		
		root.getChildren().addAll(vbox);


		
	}

	private void loadGame() {
		castlePlayerImg = new Image(getClass().getResource("/images/castlePlayer.png").toExternalForm(), 200, 200, true, true);
		castleImg = new Image(getClass().getResource("/images/castle1.png").toExternalForm(), 100, 100, true, true);
		neutCastleImg = new Image(getClass().getResource("/images/neutCastle2.png").toExternalForm(), 100, 100, true, true);
		tresorImg = new Image(getClass().getResource("/images/tresor.png").toExternalForm(), 35, 35, true, true);
		chevalierImg = new Image(getClass().getResource("/images/chevalier.jpg").toExternalForm(), 35, 35, true, true);
		royaumImg = new Image(getClass().getResource("/images/castle1.png").toExternalForm(), 30, 35, true, true);
		onagreImg = new Image(getClass().getResource("/images/onagre.png").toExternalForm(), 35, 35, true, true);
		piqImg = new Image(getClass().getResource("/images/piquier.png").toExternalForm(), 35, 35, true, true);
		pauseImg = new Image(getClass().getResource("/images/pause.jpg").toExternalForm(), 180, 150, true, true);
		
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
					if (Settings.distance(e.getX(),e.getY(), arrayOptions.get(4).getDx(), 
							arrayOptions.get(4).getDy()) < arrayOptions.get(4).getBackground().getHeight());
						competition2.add(arrayOptions.get(4));
						
					if (Settings.distance(e.getX(),e.getY(), arrayOptions.get(5).getDx(), 
							arrayOptions.get(5).getDy()) < arrayOptions.get(5).getBackground().getHeight());
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
								player.getListCastle().get(0).getOrder().sendOrder(lastCastle, pyk, kni, ona);
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
				updateStatus(lastCastle);
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
		if (3*min1 > min2)
		{
			comp = false;
			return index2;
		}
		comp = true;
		return index;
	}
	
	//Creation structures
	
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
	
	private void pauseGame() {
		
		//hbox.setPrefSize(Pos.CENTER);
		hbox.setPadding(new Insets(Settings.SCENE_WIDTH/3 + 35));
		//hbox.setAlignment(Pos.CENTER);
		hbox.getStyleClass().add("imageViewPause");
		imageViewPause = new ImageView(pauseImg);
		
		/*
		 * Text message = new Text(); message.getStyleClass().add("message");
		 * message.setText("Vous êtes en Pause !");
		 */
		hbox.getChildren().add(imageViewPause);
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
			lastTurn = now;
			if (lastCastle != null)
				updateStatus(lastCastle);
			if (!armies.isEmpty())
			{
				moveTroops();
				clash();
			}
		}
	}
	public void createStatusBar() {
		HBox statusBar = new HBox();
		
		statusBar.getChildren().addAll(royaumView ,stats, tresorView, stats2, chevaView, piqView, stats5, stats3, onagView, stats4);
		//statusBar.getChildren().addAll(stats);
		stats.setText("									Welcome !");
		stats.resize(0, 0);
		stats.setTranslateY(10);
		stats.translateYProperty();
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		this.playfieldLayer.getChildren().add(statusBar);
	}

	ImageView royaumView = new ImageView();
	ImageView tresorView = new ImageView(); 
	ImageView onagView = new ImageView();
	ImageView chevaView = new ImageView(); 
	ImageView piqView = new ImageView();
	
	private void updateStatus(Castle castle)
	{
			String ordres = new String();
			if (castle.getOrder().getTarget() == null)
				ordres = "Aucun";
			else
				ordres = castle.getOrder().getTarget().getDuc();
			
		royaumView.setImage(royaumImg);
		stats.setText(" : " + castle.getDuc() + Settings.SBLANK + " Niveau : " + castle.getLevel() + Settings.SBLANK);
	
		tresorView.setImage(tresorImg);
		 stats2.setText(" : " + castle.getTreasure() + Settings.SBLANK + " Troupes : " + castle.getTroops()[0]);
				 
		piqView.setImage(piqImg);
		stats5.setText(" : " + castle.getTroops()[1]);
		  
		chevaView.setImage(chevalierImg);
		stats3.setText(" : " + castle.getTroops()[2]);
		  
		onagView.setImage(onagreImg);
		stats4.setText(" : " + Settings.SBLANK + " Produit : " + castle.getProduction().getProducts() + Settings.SBLANK + " Ordre : " + ordres + Settings.SBLANK + " Porte : " + castle.getGate());
		 
		
	}

	
	private void attack(Castle source, Castle target)
	{
		armies.add(source.instanceTroops());
		sources.add(source);
		targets.add(target);
		//defenders.add(target.defend());
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
					sources.remove(i);
					targets.remove(i);
				}
				else
				{
					int nbInsideGate = 0;
					for (int j = 0; j < armies.get(i).size(); j++)
					{
						//
						if (armies.get(i).get(j).getRectangle().getX() - sources.get(i).getDx() < sources.get(i).getHeigth_Image()/4 && 
							armies.get(i).get(j).getRectangle().getY() - sources.get(i).getDy() < sources.get(i).getHeigth_Image()/4)
						{
							if (nbInsideGate < Settings.SIZEGATE)
							{
								if (targets.get(i).getDx() > sources.get(i).getDx())
								{
									armies.get(i).get(j).getRectangle().setX(sources.get(i).getDx() +
										sources.get(i).getWidth_Image()/2);
									armies.get(i).get(j).getRectangle().setY(sources.get(i).getDy() + 
										sources.get(i).getHeigth_Image()/2 - 2*nbInsideGate);
									/*armies.get(i).get(j).getRectangle().relocate(sources.get(i).getDx() +
										sources.get(i).getWidth_Image()/2, sources.get(i).getDy() + 
										sources.get(i).getHeigth_Image()/2 - 2*nbInsideGate);
									nbInsideGate++;*/
								}
								else
								{
									armies.get(i).get(j).getRectangle().setX(sources.get(i).getDx() -
											sources.get(i).getWidth_Image()/2);
										armies.get(i).get(j).getRectangle().setY(sources.get(i).getDy() + 
											sources.get(i).getHeigth_Image()/2 - 2*nbInsideGate);
									/*armies.get(i).get(j).getRectangle().relocate(sources.get(i).getDx() -
										sources.get(i).getWidth_Image()/2, sources.get(i).getDy() + 
										sources.get(i).getHeigth_Image()/2 - 2*nbInsideGate);*/
								}
							}
						}
						else
						{
							double a = (sources.get(i).getDy() - armies.get(i).get(j).getRectangle().getY())/
								(sources.get(i).getDx() - armies.get(i).get(j).getRectangle().getX());
							double b = sources.get(i).getDy() - a * sources.get(i).getDx();
							
							if (targets.get(i).getDx() > armies.get(i).get(j).getRectangle().getX())
							{
								armies.get(i).get(j).getRectangle().setX(armies.get(i).get(j).getRectangle().getX() + armies.get(i).get(j).getSpeed());
								armies.get(i).get(j).getRectangle().setY(a * (armies.get(i).get(j).getRectangle().getX() + armies.get(i).get(j).getSpeed()) + b);
							}
							else
							{
								armies.get(i).get(j).getRectangle().setX(armies.get(i).get(j).getRectangle().getX() - armies.get(i).get(j).getSpeed());
								armies.get(i).get(j).getRectangle().setY(a * (armies.get(i).get(j).getRectangle().getX() - armies.get(i).get(j).getSpeed()) + b);
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
						targets.get(i).getDx(), targets.get(i).getDy()) < targets.get(i).getWidth_Image()/3)
				{
					if (sources.get(i).getDuc().equals(targets.get(i).getDuc()))
					{
						if (armies.get(i).get(j).getClass() == Pikeman.class)
						{
							targets.get(i).setTroops0(targets.get(i).getTroops()[0] + 1);
							armies.get(i).get(j).removeFromLayer();
							armies.get(i).remove(j);
						}
						if (armies.get(i).get(j).getClass() == Knights.class)
						{
							targets.get(i).setTroops1(targets.get(i).getTroops()[1] + 1);
							armies.get(i).remove(j);
						}
						if (armies.get(i).get(j).getClass() == Onager.class)
						{
							targets.get(i).setTroops2(targets.get(i).getTroops()[2] + 1);
							armies.get(i).remove(j);
						}		
					}
					else
					{
						if (targets.get(i).getDef() == null)
						{
							if (targets.get(i).defend())
							{
								targets.get(i).setDuc(sources.get(i).getDuc());
								return;
							}
						}
						if (dammage(intermediate.getDammages(), i))//ligne de défense vide
						{
							if (targets.get(i).defend())
							{
								targets.get(i).setDuc(sources.get(i).getDuc());
								return;
							}
						}
					}
				}
			}
		}
	}
	
	private boolean dammage(int dammages, int index)
	{
		ArrayList<Troops> def = targets.get(index).getDef();
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
		if (c.getDx() > Settings.SCENE_WIDTH - 2*c.getWidth_Image())
			opt1 = new Options(playfieldLayer, "Attaquer", c.getDx() - c.getWidth_Image(), c.getDy()  , lastCastle);
		else
			opt1 = new Options(playfieldLayer, "Attaquer", c.getDx() + c.getWidth_Image(), c.getDy()  , lastCastle);
		
		if (c.getDuc() == "Joueur")
		{
			if (opt2 != null)
				opt2.removeFromLayer();
			if (c.getDx() > Settings.SCENE_WIDTH - 2*c.getWidth_Image())
				opt2 = new Options(playfieldLayer, "Produire des unités", c.getDx() - c.getWidth_Image(), c.getDy() + c.getHeigth_Image()/2 - 20, lastCastle);
			else
				opt2 = new Options(playfieldLayer, "Produire des unités", c.getDx() + c.getWidth_Image(), c.getDy() + c.getHeigth_Image()/2 - 20, lastCastle);
			opt1.setLabel("Envoyer des troupes");
		}
	}
	
	public void optionMenu(Options opt)
	{
		if (opt.getLabel().equals("Attaquer"))
		{
			z = new ZoneText(playfieldLayer, lastCastle, player.getListCastle().get(0), opt.getDx(), opt.getDy());
			arrayOptions.add(new Options(playfieldLayer, "Vos troupes", opt.getDx() + 15, opt.getDy() -41, opt.getC(), 150, 40));
			arrayOptions.add(new Options(playfieldLayer, "Piquiers : " + player.getListCastle().get(0).getTroops()[0], opt.getDx(), opt.getDy(), opt.getC(), 160, 40));
			arrayOptions.add(new Options(playfieldLayer, "Chevaliers  : " + player.getListCastle().get(0).getTroops()[1], opt.getDx() + 161, opt.getDy(), opt.getC(), 170, 40));
			arrayOptions.add(new Options(playfieldLayer, "Onagres  : " + player.getListCastle().get(0).getTroops()[2], opt.getDx() + 332, opt.getDy(), opt.getC(), 160, 40));
			arrayOptions.add(new Options(playfieldLayer, "Clear", opt.getDx() + 110, opt.getDy() + 80));
			arrayOptions.add(new Options(playfieldLayer, "Ok", opt.getDx() + 330, opt.getDy() + 80));
		}
		if (opt.getLabel().equals("Envoyer des troupes"))
		{
			arrayOptions.add(new Options(playfieldLayer, "Vos troupes", opt.getDx() + 15, opt.getDy() -40, opt.getC(), 150, 40));
			arrayOptions.add(new Options(playfieldLayer, "Piquiers : " + player.getListCastle().get(0).getTroops()[0], opt.getDx(), opt.getDy(), opt.getC(), 160, 40));
			arrayOptions.add(new Options(playfieldLayer, "Chevaliers  : " + opt.getC().getTroops()[1], opt.getDx() + 160, opt.getDy(), opt.getC(), 170, 40));
			arrayOptions.add(new Options(playfieldLayer, "Onagres  : " + opt.getC().getTroops()[2], opt.getDx() + 330, opt.getDy(), opt.getC(), 160, 40));
			z = new ZoneText(playfieldLayer, lastCastle, player.getListCastle().get(0), opt.getDx(), opt.getDy());
		}
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