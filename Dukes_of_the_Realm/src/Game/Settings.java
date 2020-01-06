package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
public class Settings {

	//TROOPS STATISTICS
	/*
	 * this class defines the statistics of troops (speed, cost, time production...)
	 * it also defines the width and height of the scene, the status bar, 
	 * the number of troops per castle ...
	 */
	public static final int COST_PRODUCTION_PIKEMAN = 100;
	public static final int COST_PRODUCTION_KNIGHT = 500;
	public static final int COST_PRODUCTION_ONAGER = 1000;

	public static final int TIME_COST_PIKEMAN = 7;
	public static final int TIME_COST_KNIGHT = 20;
	public static final int TIME_COST_ONAGER = 50;

	public static final int SPEED_PIKEMAN = 18;
	public static final int SPEED_KNIGHT = 15;
	public static final int SPEED_ONAGER = 12;

	public static final int HEALTH_PIKEMAN = 1;
	public static final int HEALTH_KNIGHT = 3;
	public static final int HEALTH_ONAGER = 5;

	public static final int DAMMAGES_PIKEMAN = 1;
	public static final int DAMMAGES_KNIGHT = 5;
	public static final int DAMMAGES_ONAGER = 10;
	
	public static final int NB_PIK_DUC = 30;
	public static final int NB_KNI_DUC = 20;
	public static final int NB_ONA_DUC = 5;
	public static final int NB_PIK_NEUT = 10;
	public static final int NB_KNI_NEUT = 20;
	public static final int NB_ONA_NEUT = 2;

	/*
	 * size of the gate, number of troops than can 
	 * cross the gate to get out of the castle
	 * and attack per turn
	 */
	public static final int SIZEGATE = 4;

	// WINDOW
	public static final int SCENE_WIDTH = 1800;
    public static final int SCENE_HEIGHT = 900;
	public static final int STATUS_BAR_HEIGHT = 45;
	
	/*
	 * String that contains a big blank
	 */
	public static final String BLANK = "                  ";
	
	/*
	 * String that contains a small blank
	 */
	public static final String SBLANK = "        ";
	
	/*
	 * String that contains very small blank
	 */
	public static final String SSBLANK = "    ";

	/*
	 * array of possible gates
	 */
	public static final String GATES[] = {"Sud", "Nord", "Ouest", "Est"};
	
	/*
	 * array of possible duc names
	 */
	public static final String DUC_NAMES[] = {"Ousmane", "Red", "Roger", "Arthur", "Loupi", "Jean", 
			"Aurel", "Petit", "Gros", "Alexandre", "Gourd", "Lourd", "Laella", "Arthas", "Gaspard",
			"Salomon", "Blaise", "Romain", "Joffrey", "Stannis", "Tywin", "Salazar"};
	
	/*
	 * a turn lasts TIME_TURN
	 * We recommend a number not too high, to keep the game fun and chaotic
	 */
	public static final long TIME_TURN = 10000 * 10000 ;
	
	/*
	 * number of castle generated in the game
	 */
	public static final int NB_NEUT_CASTLE = 5;
	public static final int NB_ENN_CASTLE = 5;
	
	/*
	 * static method that calculate distance between elements.
	 * @param x1 is the x coordinate of the first element
	 * @param y1 is the y coordinate of the first element
	 * @param x2 is the x coordinate of the second element
	 * @param y2 is the y coordinate of the second element
	 * @return the distance calculated between the 2 elements
	 */
	public static final double DISTANCE_MIN_BETWEEN_CASTLES = 150;
	
	  public static double distance(double x1, double y1, double x2, double y2)
	    {
	    	return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
	    }
	
}