package Game;

import java.io.Serializable;

import java.util.ArrayList;

public class Player {
	private String name;
	//This collection is Castle type
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
	//Calculed all troops in my listeCastle collection
	
	public int totalTroops()
	{
		int s = 0;
		for (int i = 0; i < listCastle.size(); i++)
			s = s + listCastle.get(i).totalTroops();
		return s;
	}
}
