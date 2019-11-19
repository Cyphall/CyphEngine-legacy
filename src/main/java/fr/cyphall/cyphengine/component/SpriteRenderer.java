package fr.cyphall.cyphengine.component;

import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.display.Shader;
import fr.cyphall.cyphengine.display.TextureData;
import org.joml.Vector2i;

public class SpriteRenderer extends Component
{
	private TextureData data;
	private static Shader shader;
	
	private Vector2i size;
	private int depth = 1;
	
	public SpriteRenderer(String textureName)
	{
		this.data = ToolBox.tdm().get(textureName);
		
		size = data.getSize();
		
		if (shader == null)
			shader = new Shader("default");
	}
	
	public SpriteRenderer(String textureName, int depth)
	{
		this(textureName);
		
		this.depth = Math.max(Math.min(depth, 999), 1);
	}
	
	public TextureData getTexture()
	{
		return data;
	}
	
	public Vector2i getSize()
	{
		return size;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	public void render()
	{
		shader.bind();
		
		//TODO: render
	}
}
