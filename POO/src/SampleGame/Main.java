
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

//import SampleGame.alien.Input;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends Application {
	private Random rnd = new Random();

	private Pane playfieldLayer, layerFieldChateau;
	private ImageView imageViewChateau;
	
	private AnimationTimer gameLoop;
	
	private List<Chateau> chateau = new ArrayList<>();

	private Image chateauNeutreImage;
	private Image chateauNormalImage;
	private Duc duc;
	
	private Player player;
	
	private static Settings settings = new Settings();

	private Scene scene;
	private Input input;
	
	//private boolean justPause = false, withPause = true;
	
	Group root;
	
	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Dukes of the Realm");
		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT, Color.WHITE);
		//scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		
		// create layers
		layerFieldChateau = new Pane();
		root.getChildren().add(layerFieldChateau);
		
		//loadGame();
		
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
	
	 Rectangle chateau_DUC = new Rectangle();
	 Rectangle porte_Duc = new Rectangle();
	 Rectangle barron1 = new Rectangle();
	 Rectangle barron2 = new Rectangle();
	 
	 
	 chateau_DUC.setX(50);
	 chateau_DUC.setY(25);
	 chateau_DUC.setWidth(150);
	 chateau_DUC.setHeight(150);
	 chateau_DUC.setFill(Color.GREEN);
	 /////
	 porte_Duc.setX(100);
	 porte_Duc.setY(160);
	 porte_Duc.setWidth(40);
	 porte_Duc.setHeight(20);
	 porte_Duc.setFill(Color.BLACK);
	 /////
	 
	 
	    
	    
	    root.getChildren().add(chateau_DUC);//On ajoute le rectangle après le cercle
	    //primaryStage.setVisible(true);
	    
	    
	    root.getChildren().add(porte_Duc);//On ajoute le rectangle après le cercle
	    //primaryStage.setVisible(true);
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
			
			tab[i] = new Chateau_Normal(layerFieldChateau, chateauNeutreImage, duc , dx, dy);

					
		}
		return tab;
	}
	
   
	
	
    private void loadGame() {
		
		//chateauNormalImage = new Image(getClass().getResource("/images/chateau_image.gif").toExternalForm(), 100, 100, true, true);
		chateauNeutreImage = new Image(getClass().getResource("/images/enemy.png").toExternalForm(), 50, 50, true, true);		
		//createPlayer();
		
	}

	private void createPlayer() {
		double x = (Settings.SCENE_WIDTH - chateauNormalImage.getWidth()) / 2.0;
		double y = Settings.SCENE_HEIGHT * 0.7;
		player = new Player(layerFieldChateau , chateauNormalImage, x,y, 1 ,1.0);
		
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