package fr.cyphall.cyphallengine2d.entity.preset;

import fr.cyphall.cyphallengine2d.display.Texture;
import fr.cyphall.cyphallengine2d.entity.Entity;
import fr.cyphall.cyphallengine2d.interfaces.Drawable;

import java.util.ArrayList;

public class Text extends Entity implements Drawable
{
	private final ArrayList<Texture> chars = new ArrayList<>();
	
	int size;
	
	public Text(String value, int fontSize, int posX, int posY)
	{
		size = fontSize;
		setPos(posX, posY);
		setText(value);
	}
	
	public void setText(String value, int fontSize)
	{
		size = fontSize;
		setText(value);
	}
	
	public void setText(String value)
	{
		chars.clear();
		
		for (int i=0; i<value.length(); i++)
		{
			Texture t = new Texture("c_"+(int)(value.charAt(i)), i*size, 0);
			t.forceWidth(size);
			t.forceHeight(70*size/38);
			chars.add(t);
		}
	}
	
	@Override
	public ArrayList<Texture> getTextures()
	{
		return chars;
	}
}
