package fr.cyphall.cyphallengine2d.display;

import fr.cyphall.cyphallengine2d.core.Provider;

public class Texture
{
	public static final int X = 1;
	public static final int Y = 2;
	public static final int BOTH = 3;
	
	
	private TextureData data;
	
	private int width;
	private int height;
	
	private int offsetPosX;
	private int offsetPosY;
	
	public Texture(String name)
	{
		this(name, 0, 0);
	}
	
	public Texture(String name, int offX, int offY)
	{
		offsetPosX = offX;
		offsetPosY = offY;
		
		this.data = Provider.tdm().get(name);
		
		width = data.getWidth();
		height = data.getHeight();
	}
	
	public void centerOffset(int axe)
	{
		switch (axe)
		{
			case X:
				offsetPosX = -width / 2;
				break;
			case Y:
				offsetPosY = -height / 2;
				break;
			case BOTH:
				offsetPosX = -width / 2;
				offsetPosY = -height / 2;
				break;
		}
	}
	
	public void addOffsetPos(int x, int y)
	{
		offsetPosX += x;
		offsetPosY += y;
	}
	
	public void addOffsetPosX(int x)
	{
		offsetPosX += x;
	}
	
	public void addOffsetPosY(int y)
	{
		offsetPosY += y;
	}
	
	public void setOffsetPos(int x, int y)
	{
		offsetPosX = x;
		offsetPosY = y;
	}
	
	public void setOffsetPosX(int x)
	{
		offsetPosX = x;
	}
	
	public void setOffsetPosY(int y)
	{
		offsetPosY = y;
	}
	
	public int getLeft()
	{
		return offsetPosX;
	}
	
	public int getTop()
	{
		return offsetPosY;
	}
	
	public int getRight()
	{
		return offsetPosX + width;
	}
	
	public int getBottom()
	{
		return offsetPosY + height;
	}
	
	public void forceWidth(int width)
	{
		this.width = width;
	}
	
	public void forceHeight(int height)
	{
		this.height = height;
	}
	
	public void bind()
	{
		data.bind();
	}
}
