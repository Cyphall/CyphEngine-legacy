package fr.cyphall.bullethell;

import fr.cyphall.cyphallengine2d.core.Game;
import fr.cyphall.cyphallengine2d.core.Provider;

public class Main extends Game
{
	@Override
	public void init()
	{
		Provider.window().setSize(400, 600);
		Provider.window().setTitle("Bullet Hell");
		Provider.window().setVisible(true);
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}
}