package fr.cyphall.bullethell;

import fr.cyphall.bullethell.core.Game;

public class Main
{
	public static void main(String[] args)
	{
		Game game = new Game(400, 600, "Bullet Hell");
		
		game.loop();
		game.quit();
	}
}
