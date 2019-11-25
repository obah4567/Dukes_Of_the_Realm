package SampleGame;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Player extends Sprite {

	private Input input;
	private Chateau c;
	private boolean pause = false;

	public Player(Pane layer, Image image, Input input, Chateau c) {
		super(layer, image, x, y, health, damage);
		this.input = input;
		this.c = c;
	}


	public boolean canPause(long now)
	{
		return (now - lastPause >= Settings.FIRE_FREQUENCY_MEDIUM);
	}
	public boolean getPause()
	{
		return pause;
	}
	public void pause(long now) {
		lastPause = now;
		if (input.isPause())
		{
			if (pause)
				pause = false;
			else
				pause = true;
		}
	}

	
	

}
