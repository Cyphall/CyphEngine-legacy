package fr.cyphall.cyphengine;

public class ToolBox
{
	private static Window window = null;
	private static TextureDataManager tdm = null;
	private static SettingManager settings = null;
	private static InputManager inputs = null;
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
	public static void setInputManager(InputManager i)
	{
		ToolBox.inputs = i;
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
	public static InputManager inputs()
	{
		return inputs;
	}
	public static Scene currentScene()
	{
		return currentScene;
	}
}
