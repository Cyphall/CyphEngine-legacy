package fr.cyphall.cyphengine.management;

import fr.cyphall.cyphengine.display.TextureData;

import java.io.File;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

public class TextureDataManager
{
	private final HashMap<String, TextureData> data = new HashMap<>();
	
	public TextureDataManager()
	{
		data.put("ship", new TextureData("ship"));
		data.put("ship2", new TextureData("ship2"));
		data.put("bullet", new TextureData("bullet1"));
		data.put("enemy", new TextureData("enemy1"));
		
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