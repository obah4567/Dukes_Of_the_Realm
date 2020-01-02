package Game;

public class Order {
	private int nbTroop1;
	private int nbTroop2;
	private int nbTroop3;
	private Castle target;
	
	public Order(int nb1, int nb2, int nb3, Castle target)
	{
		this.nbTroop1 = nb1;
		this.nbTroop2 = nb2;
		this.nbTroop3 = nb3;
		this.target = target;
	}
	
	public int getNbTroop1() {
		return nbTroop1;
	}
	public int getNbTroop2() {
		return nbTroop2;
	}
	public int getNbTroop3() {
		return nbTroop3;
	}
	public Castle getTarget() {
		return target;
	}
	public void setNbTroop1(int nbTroop1) {
		this.nbTroop1 = nbTroop1;
	}
	public void setNbTroop2(int nbTroop2) {
		this.nbTroop2 = nbTroop2;
	}
	public void setNbTroop3(int nbTroop3) {
		this.nbTroop3 = nbTroop3;
	}
	public void setTarget(Castle target) {
		this.target = target;
	}
	
	
}
