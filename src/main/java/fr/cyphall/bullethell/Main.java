package fr.cyphall.bullethell;

import fr.cyphall.cyphallengine2d.core.Game;
import fr.cyphall.cyphallengine2d.core.ToolBox;
import fr.cyphall.cyphallengine2d.entity.preset.Text;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main extends Game
{
	@Override
	public void init()
	{
		ToolBox.window().setSize(400, 600);
		ToolBox.window().setTitle("Bullet Hell");
		ToolBox.window().setVisible(true);
	}
	
	@Override
	public void loop()
	{
		Text fps = new Text("", 10, 0, 0);
		
		while(!glfwWindowShouldClose(ToolBox.window().getRawWindow()))
		{
			ToolBox.window().clear();
			glfwPollEvents();
			
			if (ToolBox.settings().getBoolean("debug", "showFPS"))
			{
				fps.setText(Float.toString(ToolBox.window().getFPS()));
				ToolBox.window().temp_render(fps);
			}
			
			ToolBox.window().swap();
		}
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}
}