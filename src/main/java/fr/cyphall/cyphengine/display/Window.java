package fr.cyphall.cyphengine.display;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
	private final long window;
	
	private int target_fps = 60;
	private float fps = 0;
	private long timeAtLastTick = 0;
	
	public Window(int width, int height, String title)
	{
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the window");
		
		glfwMakeContextCurrent(window);
		
		createCapabilities();
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if (vidmode != null)
		{
			glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		}
		
		glfwSwapInterval(0);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL_DEPTH_TEST);
		
		this.setClearColor(0, 0, 0);
		
		glfwShowWindow(window);
	}
	
	public long getHandler()
	{
		return window;
	}
	
	public void setClearColor(float r, float g, float b)
	{
		glClearColor(r, g, b, 1);
	}
	
	public void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void swap()
	{
		if (target_fps > 0)
		{
			long waitUntil = timeAtLastTick + 1000000000 / target_fps;
			//noinspection StatementWithEmptyBody
			while (System.nanoTime() < waitUntil);
		}
		fps = 1000000000.0f / (float)(System.nanoTime() - timeAtLastTick);
		timeAtLastTick = System.nanoTime();
		
		glfwSwapBuffers(window);
	}
	
	public void setTargetFPS(int fps)
	{
		this.target_fps = fps;
	}
	
	public float getFPS()
	{
		return fps;
	}
	
	public Vector2i getSize()
	{
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, w ,h);
		return new Vector2i(w.get(), h.get());
	}
}
