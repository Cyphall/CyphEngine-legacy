package fr.cyphall.cyphengine;

import java.util.HashMap;

public class TextureDataManager
{
	private final HashMap<String, TextureData> data = new HashMap<>();
	
	public TextureData get(String name)
	{
		if (data.containsKey(name))
		{
			return data.get(name);
		}
		
		TextureData td = new TextureData(name);
		data.put(name, td);
		return td;
	}
}
