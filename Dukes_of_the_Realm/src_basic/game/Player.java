package game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
import java.io.Serializable;

import java.util.ArrayList;

public class Player {
	
	/*
	 * name is a String that describes the player. It is "Joueur"
	 */
	private String name;
	
	/*
	 * listCastle is an ArrayList<Castle> that contains every castle owned by the player
	 */
	private ArrayList<Castle> listCastle;
	
	/*
	 * base is a Castle that describes the main base of operation of the player.
	 * this variables means something when player has more than one castle
	 */
	private Castle base;

	/*
	 * Construct a player. add his castle to his collection listCastle, and make his castle
	 * his base. sets the name of the castle "Joueur".
	 * @param castle is the Castle that describes the player
	 */
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
	
	/*
	 * @return the total of troops from every castle the player owns
	 */
	public int totalTroops()
	{
		int s = 0;
		for (int i = 0; i < listCastle.size(); i++)
			s = s + listCastle.get(i).totalTroops();
		return s;
	}
}
