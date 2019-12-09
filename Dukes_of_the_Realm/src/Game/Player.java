package Game;

import java.util.ArrayList;

public class Player {

	private ArrayList<Castle> listCastle;
	private Input input;

	public Player(Input input, Castle castle)
	{
        this.input = input;
        this.listCastle = new ArrayList<Castle>();
        this.listCastle.add(castle);
	}
	
	public ArrayList<Castle> getListCastle()
	{
		return this.listCastle;
	}
	public Input getInput()
	{
		return this.input;
	}
}
