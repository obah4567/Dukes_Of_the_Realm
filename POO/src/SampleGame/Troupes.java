package SampleGame;
import javafx.scene.image.Image;

public abstract class Troupes {
	
	    
    public int cout_de_prod ;
    public int temps_de_prod ;
    public int vitesse ;
    public int vie ;
    public double degat ;
    private Image image;
    
	public Troupes(Image image, int cout_de_prod, int temps_de_prod, int vitesse, int vie, double degat) {

		this.image = image;
		this.cout_de_prod = cout_de_prod;
		this.temps_de_prod = temps_de_prod;
		this.vitesse = vitesse;
		this.vie = vie;
		this.degat = degat;
	}
    
    
    
}
