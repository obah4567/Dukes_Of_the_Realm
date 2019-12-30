package Game;

import java.util.ArrayList;

public class Player {

	private ArrayList<Castle> listCastle;

	public Player(Castle castle)
	{
        this.listCastle = new ArrayList<Castle>();
        this.listCastle.clear();
        this.listCastle.add(castle);
	}
	
	public ArrayList<Castle> getListCastle()
	{
		return this.listCastle;
	}
}
