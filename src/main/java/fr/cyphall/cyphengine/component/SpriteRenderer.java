package fr.cyphall.cyphengine.component;

import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.display.TextureData;
import org.joml.Vector2i;

import java.util.ArrayList;

public class SpriteRenderer extends Component
{
	private static ArrayList<SpriteRenderer> instances = new ArrayList<>();
	
	private TextureData data;
	
	private Vector2i size = new Vector2i();
	
	public SpriteRenderer(String textureName)
	{
		this.data = ToolBox.tdm().get(textureName);
		
		size.x = data.getWidth() * ToolBox.settings().getInt("display", "pixelScale");
		size.y = data.getHeight() * ToolBox.settings().getInt("display", "pixelScale");
		
		instances.add(this);
	}
	
	public static ArrayList<SpriteRenderer> getInstances()
	{
		return instances;
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
