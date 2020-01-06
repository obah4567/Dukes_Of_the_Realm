package Game;

import java.io.Serializable;

import java.util.*;
public class Ennemy extends Player{
	
	int strategie = 5;
	boolean strong;
	Castle focus = null;
	Random r = new Random();
	
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
				if (this.totalTroops() < 60)
					recruit();
				else
				{
					for (int i = 0; i < getListCastle().size(); i++)
						attack(getListCastle().get(i), focus, 1, armies);
				}
			}
		}
	}
	
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
	public void levelUp()
	{
		for (int i = 0; i < getListCastle().size(); i++)
		{
			Castle intermediate = getListCastle().get(i);
			if (intermediate.product("level", intermediate.getLevel()*10))
				strategie = 10;
		}
		
	}
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
