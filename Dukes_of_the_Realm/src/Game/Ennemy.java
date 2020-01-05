package Game;
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
	}
	
	public void think()
	{
		if (this.strategie < 15)
		{
			this.strategie++;
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
					if (focus.getDuc().equals(this.getListCastle().get(0).getDuc()))
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
				if (this.strong)
					this.strategie = r.nextInt(23) + 8;
				else
					this.strategie = r.nextInt(31);
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
		if (strategie > 14 && strategie < 22)
		{
			recruit();
		}
		if (strategie > 21 && strategie < 27)
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
			if (!focus.getDuc().equals(this.getBase().getDuc()))
			{
				if (this.totalTroops() < 250)
					recruit();
				else
				{
					for (int i = 0; i < getListCastle().size(); i++)
					{
						Castle intermediate = getListCastle().get(i);
						attack(intermediate, focus, 1, armies);
					}
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
		for (int i = 0; i < getListCastle().size(); i++)
		{
			Castle intermediate = getListCastle().get(i);
			if (intermediate.totalTroops() == 0)
			{
				if (intermediate.getTreasure() > Settings.COST_PRODUCTION_PIKEMAN)
				{
					if (intermediate.getProduction().getProducts().equals("rien"))
					{
						intermediate.setTreasure(intermediate.getTreasure() - Settings.COST_PRODUCTION_PIKEMAN);
						intermediate.product("Piquier", Settings.TIME_COST_PIKEMAN);
					}
				}
				return;
			}
			if (intermediate.getTroops()[0] / intermediate.totalTroops() > 0.4)
			{
				if (intermediate.getTroops()[1] / intermediate.totalTroops() > 0.4)
				{
					if (intermediate.getTreasure() > Settings.COST_PRODUCTION_ONAGER)
					{
						if (intermediate.getProduction().getProducts().equals("rien"))
						{
							intermediate.setTreasure(intermediate.getTreasure() - Settings.COST_PRODUCTION_ONAGER);
							intermediate.product("Onagre", Settings.TIME_COST_ONAGER);
						}
					}
				}
				else
				{
					if (intermediate.getTreasure() > Settings.COST_PRODUCTION_KNIGHT)
					{
						if (intermediate.getProduction().getProducts().equals("rien"))
						{
							intermediate.setTreasure(intermediate.getTreasure() - Settings.COST_PRODUCTION_KNIGHT);
							intermediate.product("Chevalier", Settings.TIME_COST_KNIGHT);
						}
					}
				}
			}
			else
			{
				if (intermediate.getTreasure() > Settings.COST_PRODUCTION_PIKEMAN)
				{
					if (intermediate.getProduction().getProducts().equals("rien"))
					{
						intermediate.setTreasure(intermediate.getTreasure() - Settings.COST_PRODUCTION_PIKEMAN);
						intermediate.product("Piquier", Settings.TIME_COST_PIKEMAN);
					}
				}
			}
		}
	}
	public void levelUp()
	{
		for (int i = 0; i < getListCastle().size(); i++)
		{
			Castle intermediate = getListCastle().get(i);
			if (intermediate.getTreasure() > 1000 * (intermediate.getLevel() + 1))
			{
				intermediate.setLevel(intermediate.getLevel() + 1);
				intermediate.setTreasure(intermediate.getTreasure() - intermediate.getLevel()*1000);
				strategie = 10;
			}
		}
		
	}
	public void attack(Castle src, Castle target, int mode, ArrayList<ArrayList<Troops>> armies)
	{
		if (mode == 1)
		{
			src.sendOrder(target, src.getTroops()[0] * 3 / 4, src.getTroops()[1] * 3 / 4, src.getTroops()[2] * 3 / 4);
			armies.add(src.instanceTroops(target));
		}
		if (mode == 0)
		{
			src.sendOrder(target, src.getTroops()[0] / 4, src.getTroops()[1] / 4, src.getTroops()[2] / 4);
			armies.add(src.instanceTroops(target));
		}
	}

}
