package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.core.Game;
import fr.cyphall.cyphengine.core.ToolBox;

import static org.lwjgl.glfw.GLFW.*;

public class Main extends Game
{
	@Override
	public void init()
	{
		ToolBox.window().setSize(600, 800);
		ToolBox.window().setTitle("Bullet Hell");
		ToolBox.window().setVisible(true);
		
		ToolBox.setCurrentScene(new MainScene(ToolBox.window().getLogicalSize()));
	}
	
	@Override
	public void loop()
	{
//		Text fps = new Text("", 10, 0, 0);
		
		while(!glfwWindowShouldClose(ToolBox.window().getHandler()))
		{
			ToolBox.window().clear();
			glfwPollEvents();
			
//			if (ToolBox.settings().getBoolean("debug", "showFPS"))
//			{
//				fps.setText(Float.toString(ToolBox.window().getFPS()));
//				ToolBox.window().render(fps);
//			}
			
			ToolBox.currentScene().update();
			
			ToolBox.window().swap();
		}
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}
}