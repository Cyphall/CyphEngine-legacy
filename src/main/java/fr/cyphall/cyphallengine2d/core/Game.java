package fr.cyphall.cyphallengine2d.core;

import fr.cyphall.cyphallengine2d.display.Window;
import fr.cyphall.cyphallengine2d.management.InputManager;
import fr.cyphall.cyphallengine2d.management.SettingManager;
import fr.cyphall.cyphallengine2d.management.TextureDataManager;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Game
{
	public Game()
	{
		glfwInit();
		
		ToolBox.setWindow(new Window(600, 800, "CyphallEngine2D"));
		
		ToolBox.setTextureDataManager(new TextureDataManager());
		
		ToolBox.setSettingManager(new SettingManager("./resources/settings.ini"));
		
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
