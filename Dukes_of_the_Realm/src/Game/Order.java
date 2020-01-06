package Game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */
import java.io.Serializable;

public class Order {
	/*
	 * nbPyk is an int that describes the number of pikemen
	 */
	private int nbPyk;
	
	/*
	 * nbKni is an int that describes the number of knights
	 */
	private int nbKni;
	
	/*
	 * nbOna is an int that describes the number of onagers
	 */
	private int nbOna;
	
	/*
	 * target is a Castle that describes the target of the Order
	 */
	private Castle target;

	/*
	 * Construct a new Order.
	 * @param nb1 is an int that describes the number of pikemen
	 * @param nb2 is an int that describes the number of knights
	 * @param nb3 is an int that describes the number of onagers
	 * @param target is a Castle that describes the target of the Order
	 */
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
	
	/*
	 * Set all the fields to 0, and target to null.
	 */
	public void clear()
	{
		this.nbKni = 0;
		this.nbOna = 0;
		this.nbPyk = 0;
		this.target = null;
	}
}
