package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
import java.io.Serializable;

public class Order {
	private int nbPyk;
	private int nbKni;
	private int nbOna;
	private Castle target;

	
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
	public void setNbPyk(int nbPyk) {
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
	public void clear()
	{
		this.nbKni = 0;
		this.nbOna = 0;
		this.nbPyk = 0;
		this.target = null;
	}
}
