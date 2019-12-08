package fr.cyphall.cyphengine.display;

import org.apache.commons.io.IOUtils;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class TextureData
{
	private int id;
	
	private Vector2i size = new Vector2i();
	
	public TextureData(String name)
	{
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		
		ByteBuffer data;
		try
		{
			InputStream is = TextureData.class.getResourceAsStream("/" + name + ".png");
			if (is == null) throw new IOException();
			byte[] array = IOUtils.toByteArray(is);
			ByteBuffer bb = BufferUtils.createByteBuffer(array.length);
			bb.put(array);
			bb.flip();
			data = stbi_load_from_memory(bb, w, h, channels, 0);
		}
		catch (IOException e)
		{
			System.err.println("Texture \""+name+"\" not found.");
			try
			{
				InputStream is = TextureData.class.getResourceAsStream("/error.png");
				if (is == null) throw new IOException();
				byte[] array = IOUtils.toByteArray(is);
				ByteBuffer bb = BufferUtils.createByteBuffer(array.length);
				bb.put(array);
				bb.flip();
				data = stbi_load_from_memory(bb, w, h, channels, 0);
			}
			catch (IOException ex)
			{
				throw new IllegalStateException("Error texture not found");
			}
		}
		
		if (data == null)
			throw new IllegalStateException("Texture " + name +  " could not be loaded");
		
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
		return new Vector2i(size);
	}
}
