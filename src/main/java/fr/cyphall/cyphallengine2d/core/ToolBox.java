package fr.cyphall.cyphallengine2d.core;

import fr.cyphall.cyphallengine2d.display.Window;
import fr.cyphall.cyphallengine2d.management.SettingManager;
import fr.cyphall.cyphallengine2d.management.TextureDataManager;

public class ToolBox
{
	private static Window window = null;
	private static TextureDataManager tdm = null;
	private static SettingManager settings = null;
	private static Scene currentScene = null;
	
	public static void setWindow(Window w)
	{
		window = w;
	}
	public static void setTextureDataManager(TextureDataManager tdm)
	{
		ToolBox.tdm = tdm;
	}
	public static void setSettingManager(SettingManager s)
	{
		ToolBox.settings = s;
	}
	public static void setCurrentScene(Scene s)
	{
		ToolBox.currentScene = s;
	}
	
	public static Window window()
	{
		return window;
	}
	public static TextureDataManager tdm()
	{
		return tdm;
	}
	public static SettingManager settings()
	{
		return settings;
	}
	public static Scene currentScene()
	{
		return currentScene;
	}
}
