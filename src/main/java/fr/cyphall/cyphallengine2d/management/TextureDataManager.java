package fr.cyphall.cyphallengine2d.management;

import fr.cyphall.cyphallengine2d.display.TextureData;

import java.io.File;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

public class TextureDataManager
{
	private HashMap<String, TextureData> data = new HashMap<>();
	
	public TextureDataManager()
	{
		data.put("ship", new TextureData("ship"));
		
		for (File f : Objects.requireNonNull(new File("./resources/font").listFiles()))
		{
			String fullName = f.getName();
			String name = fullName.substring(0, fullName.lastIndexOf('.'));
			
			data.put("c_"+name, new TextureData("font/"+name));
		}
	}
	
	public TextureData get(String name)
	{
		for (String key : data.keySet())
		{
			if (key.equals(name)) return data.get(name);
		}
		throw new NoSuchElementException();
	}
}
