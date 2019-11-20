package fr.cyphall.cyphengine.display;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class TextureData
{
	private int id;
	
	private Vector2i size = new Vector2i();
	
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
		size.x = w.get();
		size.y = h.get();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size.x, size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		stbi_image_free(data);
	}
	
	public boolean bind()
	{
		if (id == 0) return false;
		glBindTexture(GL_TEXTURE_2D, id);
		return true;
	}
	
	public void free()
	{
		glDeleteTextures(id);
	}
	
	public Vector2i getSize()
	{
		return size;
	}
}
