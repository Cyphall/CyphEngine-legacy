package fr.cyphall.cyphallengine2d.management;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class SettingManager
{
	private Wini ini;
	
	public SettingManager(String path)
	{
		try
		{
			ini = new Wini(new File(path));
		}
		catch(IOException e)
		{
			System.err.println("Config file " + path + " not found");
		}
	}
	
	public boolean getBoolean(String section, String option)
	{
		return ini == null ? false : ini.get(section, option, Boolean.class);
	}
	
	public int getInt(String section, String option)
	{
		return ini == null ? 0 : ini.get(section, option, Integer.class);
	}
	
	public float getFloat(String section, String option)
	{
		return ini == null ? 0 : ini.get(section, option, Float.class);
	}
	
	public double getDouble(String section, String option)
	{
		return ini == null ? 0 : ini.get(section, option, Double.class);
	}
}
