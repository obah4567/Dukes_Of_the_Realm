package game;
/*
 * This file is part of Dukes_Of_the_Realm.
 */

/*
 * construct a Game_Exception that print the causes/message in stderr
 */
public class Game_Exception extends Exception{
	Game_Exception(String s)
	{
		System.err.println(s);
	}
}
