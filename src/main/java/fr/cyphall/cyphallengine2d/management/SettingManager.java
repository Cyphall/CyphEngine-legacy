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
		if (ini == null) return false;
		try
		{
			return ini.get(section, option, Boolean.class);
		}
		catch(NullPointerException e)
		{
			System.err.println("Option \""+ option +"\" not found in section ["+ section +"]");
			return false;
		}
	}
	
	public int getInt(String section, String option)
	{
		if (ini == null) return 0;
		try
		{
			return ini.get(section, option, Integer.class);
		}
		catch(NullPointerException e)
		{
			System.err.println("Option \""+ option +"\" not found in section ["+ section +"]");
			return 0;
		}
	}
	
	public float getFloat(String section, String option)
	{
		if (ini == null) return 0;
		try
		{
			return ini.get(section, option, Float.class);
		}
		catch(NullPointerException e)
		{
			System.err.println("Option \""+ option +"\" not found in section ["+ section +"]");
			return 0;
		}
	}
	
	public double getDouble(String section, String option)
	{
		if (ini == null) return 0;
		try
		{
			return ini.get(section, option, Double.class);
		}
		catch(NullPointerException e)
		{
			System.err.println("Option \""+ option +"\" not found in section ["+ section +"]");
			return 0;
		}
	}
}
