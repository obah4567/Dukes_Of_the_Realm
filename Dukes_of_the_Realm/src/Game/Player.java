package Game;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Castle> listCastle;
	private Castle base;

	public Player(Castle castle)
	{
        this.listCastle = new ArrayList<Castle>();
        base = castle;
        this.listCastle.add(base);
        this.name = "Joueur";
	}
	
	public ArrayList<Castle> getListCastle()
	{
		return this.listCastle;
	}
	public String getName()
	{
		return this.name;
	}
	public Castle getBase()
	{
		return this.base;
	}
	public void setBase(Castle c)
	{
		this.base = c;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName(String name)
	{
		return this.name;
	}
	public int totalTroops()
	{
		int s = 0;
		for (int i = 0; i < listCastle.size(); i++)
			s = s + listCastle.get(i).totalTroops();
		return s;
	}
}
