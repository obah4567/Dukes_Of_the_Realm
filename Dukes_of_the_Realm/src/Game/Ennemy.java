package Game;
import java.util.*;
public class Ennemy {
	private ArrayList<Castle> listCastle;
	int strategie = 5;
	boolean strong;
	Castle base;
	Castle focus = null;
	Random r = new Random();
	
	public Ennemy(Castle c)
	{
		this.listCastle = new ArrayList<Castle>();
		base = c;
		this.listCastle.add(base);
		int strength  = r.nextInt(22);
		if (strength == 10 || strength == 20)
			strong = true;
		else
			strong = false;
	}
	public ArrayList<Castle> getListCastle()
	{
		return this.listCastle;
	}
	public int totalTroops()
	{
		int s = 0;
		for (int i = 0; i < listCastle.size(); i++)
			s = s + listCastle.get(i).totalTroops();
		return s;
	}
	
	
	public void think()
	{
		if (this.strategie < 21)
		{
			this.strategie++;
		}
		else
		{
			if (this.strategie > 28)
			{
				if (focus.getDuc().equals(this.listCastle.get(0).getDuc()))
				{
					strategie = 10;
					focus = null;
				}	
				if (totalTroops() < 100)
					strategie = 21;
			}
			else
			{
				if (this.strong)
					this.strategie = r.nextInt(22) + 8;
				else
					this.strategie = r.nextInt(30);
			}
		}
	}
	
	public void act(ArrayList<Castle> world)
	{
		if (strategie < 16)
		{
			
		}
		if (strategie > 28)
		{
			if (focus == null)
			{
				boolean go = false;
				while (!go)
				{
					int i = r.nextInt(world.size());
					if (!world.get(i).getDuc().equals(listCastle.get(0).getDuc()))
					{
						focus = world.get(i);
						go = true;
					}
					
				}
			}
		}
		if (strategie < 16)
		{
			
		}
	}

}
