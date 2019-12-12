package fr.cyphall.cyphengine;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Game
{
	public Game()
	{
		glfwInit();
		
		ToolBox.setSettingManager(new SettingManager());
		
		ToolBox.setWindow(initWindow());
		
		ToolBox.setTextureDataManager(new TextureDataManager());
		
		ToolBox.setInputManager(new InputManager());
	}
	
	public abstract Window initWindow();
	
	public void init(){}
	public void quit(){}
	
	private void internal_quit()
	{
		quit();
		glfwTerminate();
	}
	
	public final void run()
	{
		init();
		
		while(!ToolBox.window().shouldClose())
		{
			ToolBox.window().clear();
			glfwPollEvents();
			
			ToolBox.currentScene().update();
			
			ToolBox.window().swap();
		}
		
		internal_quit();
	}
}
