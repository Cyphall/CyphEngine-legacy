package fr.cyphall.bullethell.core;

import fr.cyphall.bullethell.display.Window;
import fr.cyphall.bullethell.management.GameSettings;
import fr.cyphall.bullethell.management.TextureDataManager;

public class Provider
{
	private static Window window = null;
	private static TextureDataManager tdm = null;
	private static GameSettings settings = null;
	
	public static void provideWindow(Window w)
	{
		window = w;
	}
	public static void provideTextureDataManager(TextureDataManager tdm)
	{
		Provider.tdm = tdm;
	}
	public static void provideGameSettings(GameSettings s)
	{
		Provider.settings = s;
	}
	
	public static Window window()
	{
		return window;
	}
	public static TextureDataManager tdm()
	{
		return tdm;
	}
	public static GameSettings settings()
	{
		return settings;
	}
}
