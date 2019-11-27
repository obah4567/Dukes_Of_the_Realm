package Game;

public class Settings {

	//TROOPS STATISTICS
	public static final int COST_PRODUCTION_PIKEMAN = 100;
	public static final int COST_PRODUCTION_KNIGHT = 500;
	public static final int COST_PRODUCTION_ONAGER = 1000;

	public static final int TIME_COST_PIKEMAN = 5;
	public static final int TIME_COST_KNIGHT = 20;
	public static final int TIME_COST_ONAGER = 50;

	public static final int SPEED_PIKEMAN = 2;
	public static final int SPEED_KNIGHT = 6;
	public static final int SPEED_ONAGER = 1;

	public static final int HEALTH_PIKEMAN = 1;
	public static final int HEALTH_KNIGHT = 3;
	public static final int HEALTH_ONAGER = 5;

	public static final int DAMMAGES_PIKEMAN = 1;
	public static final int DAMMAGES_KNIGHT = 5;
	public static final int DAMMAGES_ONAGER = 10;

	// WINDOW
	public static final int SCENE_WIDTH = 1600;
    public static final int SCENE_HEIGHT = 900;
	public static final int STATUS_BAR_HEIGHT = 45;
	
	public static final String BLANK = "                  ";
	public static final String SBLANK = "     ";

	public static final String GATES[] = {"Sud", "Nord", "Ouest", "Est"};
	public static final String DUC_NAMES[] = {"Ousmane", "Red", "Roger", "Arthur", "Loupi", "Jean", 
			"Aurel", "Petit", "Gros", "Alexandre", "Gourd", "Lourd", "Laella", "Arthas", "Gaspard",
			"Salomon", "Blaise", "Romain", "Joffrey", "Stannis", "Tywin", "Salazar"};
	
	  public static double distance(double x1, double y1, double x2, double y2)
	    {
	    	return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
	    }

	
	
	
}
