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
		if (this.strategie < 21)
		{
			this.strategie++;
		}
		else
		{
			if (this.strategie > 28)
			{
				if (focus.getDuc().equals(this.getListCastle().get(0).getDuc()))
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
					if (!world.get(i).getDuc().equals(getListCastle().get(0).getDuc()))
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
