package fr.cyphall.cyphengine.display;

import org.lwjgl.opengl.GL46;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Shader implements AutoCloseable
{
	private int vertexID;
	private int fragmentID;
	private int programID;
	
	public Shader(String name)
	{
		createShaderProgram("./resources/shaders/" + name + ".vert", "./resources/shaders/" + name + ".frag");
	}
	
	public void close()
	{
		freeMemory();
	}
	
	private static int compileShader(int type, String path)
	{
		int shaderID = GL46.glCreateShader(type);
		
		if (shaderID == 0)
			throw new RuntimeException("Error while creating shader for " + path + ": " + GL46.glGetError());
		
		StringBuilder source = new StringBuilder();
		
		try
		{
			BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
			String str;
			while ((str = reader.readLine()) != null)
				source.append(str).append("\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		GL46.glShaderSource(shaderID, source);
		GL46.glCompileShader(shaderID);
		
		if (GL46.glGetShaderi(shaderID, GL46.GL_COMPILE_STATUS) == GL46.GL_FALSE)
			throw new RuntimeException("Error while compiling shader \" + path + \": " + GL46.glGetShaderInfoLog(shaderID));
		
		return shaderID;
	}
	
	private void createShaderProgram(String vertexPath, String fragmentPath)
	{
		vertexID = compileShader(GL46.GL_VERTEX_SHADER, vertexPath);
		fragmentID = compileShader(GL46.GL_FRAGMENT_SHADER, fragmentPath);
		
		programID = GL46.glCreateProgram();
		if (programID == 0)
			throw new RuntimeException("Error while creating program for (" + vertexPath + ", " + fragmentPath + "): " + GL46.glGetError());
		
		GL46.glAttachShader(programID, vertexID);
		GL46.glAttachShader(programID, fragmentID);
		
		GL46.glLinkProgram(programID);
		
		if (GL46.glGetProgrami(programID, GL46.GL_LINK_STATUS) == GL46.GL_FALSE)
			throw new RuntimeException("Error while creating program for (" + vertexPath + ", " + fragmentPath + "): " + GL46.glGetProgramInfoLog(programID));
	}
	
	private void freeMemory()
	{
		GL46.glDeleteBuffers(vertexID);
		GL46.glDeleteBuffers(fragmentID);
		GL46.glDeleteProgram(programID);
		
		vertexID = 0;
		fragmentID = 0;
		programID = 0;
	}
	
	public boolean bind()
	{
		if (programID == 0) return false;
		GL46.glUseProgram(programID);
		return true;
	}
	
	public void unbind()
	{
		GL46.glUseProgram(0);
	}
	
	public int getID()
	{
		return programID;
	}
}
