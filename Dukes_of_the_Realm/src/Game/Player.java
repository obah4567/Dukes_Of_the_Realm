package Game;

public class Player {

	private Castle castle;
	private Input input;

	public Player(Input input, Castle castle)
	{
        this.input = input;
        this.castle = castle;
	}
	
	public Castle getCastle()
	{
		return this.castle;
	}
	public Input getInput()
	{
		return this.input;
	}
}
