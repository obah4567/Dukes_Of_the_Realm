package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 * This describes an enemy and how he behaves
 */
import java.io.Serializable;

import java.util.*;
public class Ennemy extends Player{
	/*
	 * strategie is an int that indicates the strategy the ennemy will perform this turn
	 */
	int strategie = 5;
	
	/*
	 * boolean indicates if this enemy has a better luck with randomness
	 */
	boolean strong;
	
	/*
	 * focus is a castle, a target ennemy wishes to take over at all cost.
	 * although some circumstances can make him change his mind and strategy
	 */
	Castle focus = null;
	
	/*
	 * a tool to generate random values
	 */
	Random r = new Random();
	
	/*
	 * Construct a new Ennemy from a castle c and a String name
	 * determinates if this Ennemy is strong or not
	 * @param c castle that sets the first member of the arraylist listCastle of Ennemy
	 * @param name is the String name of the Ennemy
	 */
	public Ennemy(Castle c, String name)
	{
		super(c);
		setName(name);
		int strength  = r.nextInt(22);
		if (strength == 10 || strength == 20)
			strong = true;
		else
			strong = false;
		setBase(null);
	}
	
	/*
	 * determinates the strategy to follow this turn.
	 * if strategie < 15 then strategy increments until 15 
	 * if 15 < strategie < 27 set a random number in strategie
	 * if 26 < strategie < 30 then focus a target until it is defeated
	 * or wait for some circumstances to change the strategie
	 * if strategie > 29 wait
	 */
	public void think()
	{
		if (strategie < 15)
		{
			strategie++;
			if (strategie == 15)
				strategie = strategie + r.nextInt(12);
		}
		else
		{
			if (this.strategie > 26)
			{
				if (strategie >29)
				{
					//Nothing happens
				}
				else
				{
					if (focus.getDuc().equals(this.getName()))
					{
						strategie = 10;
						focus = null;
					}	
					if (totalTroops() < 20)
						strategie = 21;
				}
			}
			else
			{
				if (strong)
					strategie = r.nextInt(23) + 8;
				else
					strategie = r.nextInt(31);
			}
		}
	}
	
	/*
	 * act according strategie
	 * strategie < 15 : do nothing
	 * 14 < strategie < 21 : recruit
	 * 21 < strategie < 27 : attack a random target with few troops
	 * 26 < strategie < 30 : focus a target or recruit in order to attack that target with a lot of troops
	 * 29 < strategie : level Up or wait to get enough gold to level up
	 * 
	 */
	public void act(ArrayList<Castle> world, ArrayList<ArrayList<Troops>> armies)
	{
		if (strategie < 15)
		{
			
		}
		if (strategie > 26)
		{
			if (strategie > 29)
			{
				levelUp();
			}
			else
			{
				focus(world, armies);
			}
		}
		if (strategie > 14 && strategie < 21)
		{
			recruit();
		}
		if (strategie > 20 && strategie < 27)
		{
			randomAttack(world, armies);
		}
	}
	
	/*
	 * attack the target focus. if focus is null, then set a focus randomly
	 * @param world is an arralist that contains every Castle in the game
	 * @param armies is an arraylist that contains every ArrayList<Troops> in the game
	 */
	public void focus(ArrayList<Castle> world, ArrayList<ArrayList<Troops>> armies)
	{
		if (focus == null)
		{
			boolean go = false;
			while (!go)
			{
				int i = r.nextInt(world.size());
				if (!world.get(i).getDuc().equals(getListCastle().get(0).getDuc()))
				{
					focus = world.get(i);
					go = true;
				}
				
			}
		}
		else
		{
			if (!focus.getDuc().equals(this.getName()))
			{
				if (this.totalTroops() < 50)
					recruit();
				else
				{
					for (int i = 0; i < getListCastle().size(); i++)
						attack(getListCastle().get(i), focus, 1, armies);
				}
			}
		}
	}
	
	/*
	 * select a random target to attack with a few troops
	 * @param world is an arraylist that contains every Castle in the game
	 * @param armies is an arraylist that contains every ArrayList<Troops> in the game
	 */
	public void randomAttack(ArrayList<Castle> world, ArrayList<ArrayList<Troops>> armies)
	{
		for (int i = 0; i < getListCastle().size(); i++)
		{
			int j = 0;
			boolean go = false;
			while (!go)
			{
				j = r.nextInt(world.size() - 1);
				if (!world.get(j).getDuc().equals(this.getName()))
					go = true;
			}
			attack(getListCastle().get(i), world.get(j), 0, armies);
		}
	}
	
	/*
	 * produces troops 
	 * if there's no troops it produces pikeman
	 * if there's more than 40% pikemen, and 40% knights it produces onagers
	 * if there's more than 40% pikemen and less than 40% knights it produces knights
	 * if there's less than 40% pikemen it produces pikemen
	 */
	public void recruit()
	{
		double ratio = 0;
		for (int i = 0; i < getListCastle().size(); i++)
		{
			Castle intermediate = getListCastle().get(i);
			if (intermediate.totalTroops() == 0)
			{
				intermediate.product("Piquier", Settings.TIME_COST_PIKEMAN);
				return;
			}
			ratio = (double) intermediate.getTroops()[0] / (double) intermediate.totalTroops();
			if (ratio > 0.4)
			{
				ratio = (double) intermediate.getTroops()[1] / (double) intermediate.totalTroops();
				if (ratio > 0.4)
				{
					intermediate.product("Onagre", Settings.TIME_COST_ONAGER);
				}
				else
				{
					intermediate.product("Chevalier", Settings.TIME_COST_KNIGHT);
				}
			}
			else
			{
				intermediate.product("Piquier", Settings.TIME_COST_PIKEMAN);
			}
		}
	}
	
	/*
	 * sets production on level UP !
	 * if the castle can produce level , strategie gets 0
	 */
	public void levelUp()
	{
		for (int i = 0; i < getListCastle().size(); i++)
		{
			Castle intermediate = getListCastle().get(i);
			if (intermediate.product("level", intermediate.getLevel()*10))
				strategie = 0;
		}
		
	}
	
	/*
	 * launch an attack on a target from a source if it is possible
	 * an order is sent, an army is instantiated and added to the collection armies
	 * @param src is the source castle
	 * @param target is the target of the attack
	 * @param mode is an int that describes the scale of the attack ; 0 small scale, 1 big one
	 * @param armies, is the arraylist that contains every ArrayList<Troops> or armies. 
	 */
	public void attack(Castle src, Castle target, int mode, ArrayList<ArrayList<Troops>> armies)
	{
		if (mode == 1)
		{
			if (src.getOrder().getTarget() == null)
			{
				src.sendOrder(target, src.getTroops()[0] * 3 / 4, src.getTroops()[1] * 3 / 4, src.getTroops()[2] * 3 / 4);
				if (src.getOrder().getTarget() != null)
				{
					armies.add(src.instanceTroops(target));
				}
			}
		}
		if (mode == 0)
		{
			if (src.getOrder().getTarget() == null)
			{
				src.sendOrder(target, src.getTroops()[0] / 4, src.getTroops()[1] / 4, src.getTroops()[2] / 4);
				if (src.getOrder().getNbPyk() != 0 || src.getOrder().getNbKni() != 0 || src.getOrder().getNbOna() != 0 )
				{
					armies.add(src.instanceTroops(target));
				}
			}
		}
	}

}
