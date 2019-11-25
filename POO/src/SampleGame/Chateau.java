package SampleGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public abstract class Chateau {
	
	private ImageView imageView;

    private Pane layer;
    
	private Duc duc;
	private int tressors;
	private int niveau;
	private int abcisseChateau;
	private int ordonneChateau;
	private Order ordres;
	private ArrayList<Troupes> troupes;
	private Unite_production unity_prod;
	
	
	public Chateau(ImageView imageView, Pane layer, Duc duc, int tressors, int niveau, int abcisseChateau,
			int ordonneChateau, Order ordres, ArrayList<Troupes> troupes, Unite_production unity_prod) {
		super();
		this.imageView = imageView;
		this.layer = layer;
		this.duc = duc;
		this.tressors = tressors;
		this.niveau = niveau;
		this.abcisseChateau = abcisseChateau;
		this.ordonneChateau = ordonneChateau;
		this.ordres = ordres;
		this.troupes = troupes;
		this.unity_prod = unity_prod;
	}

	
	
	public Chateau(ImageView imageView, Pane layer, Duc duc, int abcisseChateau, int ordonneChateau) {
		super();
		this.imageView = imageView;
		this.layer = layer;
		this.duc = duc;
		this.abcisseChateau = abcisseChateau;
		this.ordonneChateau = ordonneChateau;
	}



	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public Pane getLayer() {
		return layer;
	}

	public void setLayer(Pane layer) {
		this.layer = layer;
	}

	public Duc getDuc() {
		return duc;
	}

	public void setDuc(Duc duc) {
		this.duc = duc;
	}

	public int getTressors() {
		return tressors;
	}

	public void setTressors(int tressors) {
		this.tressors = tressors;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public int getAbcisseChateau() {
		return abcisseChateau;
	}

	public void setAbcisseChateau(int abcisseChateau) {
		this.abcisseChateau = abcisseChateau;
	}

	public int getOrdonneChateau() {
		return ordonneChateau;
	}

	public void setOrdonneChateau(int ordonneChateau) {
		this.ordonneChateau = ordonneChateau;
	}

	public Order getOrdres() {
		return ordres;
	}

	public void setOrdres(Order ordres) {
		this.ordres = ordres;
	}

	public ArrayList<Troupes> getTroupes() {
		return troupes;
	}

	public void setTroupes(ArrayList<Troupes> troupes) {
		this.troupes = troupes;
	}

	public Unite_production getUnity_prod() {
		return unity_prod;
	}

	public void setUnity_prod(Unite_production unity_prod) {
		this.unity_prod = unity_prod;
	}


	

	
}
