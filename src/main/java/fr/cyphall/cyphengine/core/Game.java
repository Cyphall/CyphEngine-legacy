package fr.cyphall.cyphengine.core;

import fr.cyphall.cyphengine.display.Window;
import fr.cyphall.cyphengine.management.InputManager;
import fr.cyphall.cyphengine.management.SettingManager;
import fr.cyphall.cyphengine.management.TextureDataManager;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public abstract class Game
{
	public Game()
	{
		glfwInit();
		
		ToolBox.setSettingManager(new SettingManager("./resources/settings.ini"));
		
		ToolBox.setWindow(new Window(600, 800, "CyphallEngine2D"));
		
		ToolBox.setTextureDataManager(new TextureDataManager());
		
		ToolBox.setInputManager(new InputManager());
	}
	
	public void init(){}
	
	public void loop(){}
	
	public void quit(){}
	
	private void internal_quit()
	{
		quit();
		glfwTerminate();
	}
	
	public void run()
	{
		init();
		loop();
		quit();
	}
}
