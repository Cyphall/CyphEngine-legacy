package fr.cyphall.cyphallengine2d.core;

import fr.cyphall.cyphallengine2d.display.Window;
import fr.cyphall.cyphallengine2d.entity.Text;
import fr.cyphall.cyphallengine2d.management.GameSettings;
import fr.cyphall.cyphallengine2d.management.TextureDataManager;

import static org.lwjgl.glfw.GLFW.*;

public class Game
{
	public Game()
	{
		glfwInit();
		
		Provider.provideWindow(new Window(800, 600, "CyphallEngine2D"));
		Provider.window().setVisible(true);
		
		Provider.provideTextureDataManager(new TextureDataManager());
		
		Provider.provideGameSettings(new GameSettings());
	}
	
	public void init()
	{
	
	}
	
	public void loop()
	{
		Text fps = new Text("", 10, 0, 0);
		
		while(!glfwWindowShouldClose(Provider.window().getRawWindow()))
		{
			Provider.window().clear();
			glfwPollEvents();
			
			if (Provider.settings().showFPS)
			{
				fps.setText(Float.toString(Provider.window().getFPS()));
				Provider.window().temp_render(fps);
			}
			
			Provider.window().swap();
		}
	}
	
	public void quit()
	{
		glfwTerminate();
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		
		game.init();
		game.loop();
		game.quit();
	}
}
