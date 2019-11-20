package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.core.Game;
import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.display.Window;
import org.joml.Vector2i;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main extends Game
{
	@Override
	public Window initWindow()
	{
		return new Window(600, 800, "Bullet Hell");
	}
	
	@Override
	public void init()
	{
		ToolBox.setCurrentScene(
				new MainScene(
						new Vector2i(ToolBox.window().getSize().x/2, ToolBox.window().getSize().y/2)
				)
		);
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