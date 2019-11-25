package SampleGame;

public class Settings {

	public static final int SCENE_WIDTH = 1800;
    public static final int SCENE_HEIGHT = 1400;
	public static final int STATUS_BAR_HEIGHT = 50;


    public static final double PLAYER_SPEED = 8.0;//4.0
    public static final int    PLAYER_HEALTH = 3;
    public static final double PLAYER_DAMAGE = 1;

    public static final double MISSILE_SPEED = 6.0; //4.0
    public static final int    MISSILE_HEALTH = 0;
    public static final double MISSILE_DAMAGE = 1.0;

    public static final int ENEMY_SPAWN_RANDOMNESS = 100;
    
    public static final int FIRE_FREQUENCY_LOW = 1000 * 1000 * 1000; // 1 second in nanoseconds
    public static final int FIRE_FREQUENCY_MEDIUM = 500 * 1000 * 1000; // 0.5 second in nanoseconds
    public static final int FIRE_FREQUENCY_HIGH = 100 * 1000 * 1000; // 0.1 second in nanoseconds
    public static final String LIST[] = {"Ousmane", "Gerard", "Richard", "Roger", "Fred"};
    
}
