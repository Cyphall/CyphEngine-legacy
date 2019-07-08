package fr.cyphall.cyphallengine2d.display;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class TextureData
{
	private int id;
	
	private int width;
	private int height;
	
	public TextureData(String name)
	{
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		
		ByteBuffer data = stbi_load("./resources/"+name+".png", w, h, channels, 0);
		
		if (data == null)
		{
			System.err.println("Texture \""+name+"\" not found.");
			data = stbi_load("./resources/error.png", w, h, channels, 0);
			
			if (data == null)
				throw new IllegalStateException("Error texture not found");
		}
		
		id = glGenTextures();
		width = w.get();
		height = h.get();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		stbi_image_free(data);
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void free()
	{
		glDeleteTextures(id);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
}
