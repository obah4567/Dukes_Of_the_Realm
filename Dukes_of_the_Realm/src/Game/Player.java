package Game;

import java.util.ArrayList;

public class Player {

	private ArrayList<Castle> listCastle;
	private Castle base;

	public Player(Castle castle)
	{
        this.listCastle = new ArrayList<Castle>();
        base = castle;
        this.listCastle.add(base);
        
	}
	
	public ArrayList<Castle> getListCastle()
	{
		return this.listCastle;
	}
}
