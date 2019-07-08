package fr.cyphall.bullethell.core;

import fr.cyphall.bullethell.display.Window;
import fr.cyphall.bullethell.entity.Player;
import fr.cyphall.bullethell.entity.Text;
import fr.cyphall.bullethell.management.GameSettings;
import fr.cyphall.bullethell.management.TextureDataManager;

import static org.lwjgl.glfw.GLFW.*;

public class Game
{
	public Game(int w, int h, String title)
	{
		glfwInit();
		
		Provider.provideWindow(new Window(w, h, title));
		Provider.window().setVisible(true);
		
		Provider.provideTextureDataManager(new TextureDataManager());
		
		Provider.provideGameSettings(new GameSettings());
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
}
