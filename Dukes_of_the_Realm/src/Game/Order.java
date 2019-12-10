package Game;

import java.util.ArrayList;
import javafx.scene.layout.Pane;

public class Order {
	private Pane layer;
	private int nbPyk;
	private int nbKni;
	private int nbOna;
	private Castle target;
	private ArrayList<Troops> army;
	
	public Order(int nb1, int nb2, int nb3, Castle target)
	{
		this.nbPyk = nb1;
		this.nbKni = nb2;
		this.nbOna = nb3;
		this.target = target;
	}
	
	public int getNbPyk() {
		return nbPyk;
	}
	public int getNbKni() {
		return nbKni;
	}
	public int getNbOna() {
		return nbOna;
	}
	public Castle getTarget() {
		return target;
	}
	public void setNbPyk1(int nbPyk) {
		this.nbPyk = nbPyk;
	}
	public void setNbKni(int nbKni) {
		this.nbKni = nbKni;
	}
	public void setNbOna(int nbOna) {
		this.nbOna = nbOna;
	}
	public void setTarget(Castle target) {
		this.target = target;
	}
	
	public ArrayList<Troops> instanceTroops(Pane layer)
	{
		ArrayList<Troops> army = new ArrayList<Troops>();
		for (int i = 0; i < nbOna; i++)
		{
			this.army.add(new Onager(layer));
		}
		for (int i = 0; i < nbKni; i++)
		{
			this.army.add(new Knights(layer));
		}
		for (int i = 0; i < nbPyk; i++)
		{
			this.army.add(new Pikeman(layer));
		}
		return army;
	}
}
