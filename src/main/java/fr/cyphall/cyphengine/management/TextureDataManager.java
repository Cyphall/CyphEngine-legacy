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
		data.put("bullet1", new TextureData("bullet1"));
		data.put("bullet2", new TextureData("bullet2"));
		data.put("cockpit", new TextureData("cockpit"));
		data.put("canon", new TextureData("canon"));
		data.put("arriere", new TextureData("arriere"));
		data.put("moteur1", new TextureData("moteur1"));
		data.put("moteur2", new TextureData("moteur2"));
		data.put("enemy1", new TextureData("enemy1"));
		
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
