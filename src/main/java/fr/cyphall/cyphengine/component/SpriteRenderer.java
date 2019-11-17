package fr.cyphall.cyphengine.component;

import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.display.TextureData;
import org.joml.Vector2i;

import java.util.ArrayList;

public class SpriteRenderer extends Component
{
	private TextureData data;
	
	private Vector2i size = new Vector2i();
	
	public SpriteRenderer(String textureName)
	{
		this.data = ToolBox.tdm().get(textureName);
		
		size.x = data.getWidth();
		size.y = data.getHeight();
	}
	
	public TextureData getTexture()
	{
		return data;
	}
	
	public Vector2i getSize()
	{
		return size;
	}
}
