package fr.cyphall.cyphallengine2d.display;

import fr.cyphall.cyphallengine2d.core.ToolBox;
import fr.cyphall.cyphallengine2d.interfaces.Collidable;
import fr.cyphall.cyphallengine2d.interfaces.Drawable;
import fr.cyphall.cyphallengine2d.physics.Hitbox;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
	private final long window;
	
	private int target_fps = 60;
	private float fps = 0;
	private long timeAtLastTick = 0;
	
	public Window(int w, int h, String title)
	{
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		window = glfwCreateWindow(w, h, title, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the window");
		
		centerPos(w, h);
		
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		
		glfwSwapInterval(0);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		this.setClearColor(0, 0, 0);
		
		glOrtho(0.f, w, h, 0.f, 0.f, 1.f);
	}
	
	public void setSize(int w, int h)
	{
		glfwSetWindowSize(window, w, h);
		centerPos(w, h);
	}
	
	public void setTitle(String title)
	{
		glfwSetWindowTitle(window, title);
	}
	
	private void centerPos(int w, int h)
	{
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if (vidmode != null)
		{
			glfwSetWindowPos(window, (vidmode.width() - w) / 2, (vidmode.height() - h) / 2);
		}
	}
	
	public long getRawWindow()
	{
		return window;
	}
	
	public void setVisible(boolean state)
	{
		if (state)
		{
			glfwShowWindow(window);
		}
		else
		{
			glfwHideWindow(window);
		}
	}
	
	public void setClearColor(float r, float g, float b)
	{
		glClearColor(r, g, b, 0);
	}
	
	public void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public void swap()
	{
		if (target_fps > 0)
		{
			long waitUntil = timeAtLastTick + 1000000000 / target_fps;
			//noinspection StatementWithEmptyBody
			while (System.nanoTime() < waitUntil)
			{
			
			}
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
	
	public void temp_render(Drawable drawable)
	{
		glEnable(GL_TEXTURE_2D);
		
		glColor4f(1, 1, 1, 1);
		for (Texture tex : drawable.getTextures())
		{
			tex.bind();
			glBegin(GL_QUADS);
				glTexCoord2i(0, 0);
				glVertex2i(Math.round(drawable.getPosX() + tex.getLeft()), Math.round(drawable.getPosY() + tex.getTop()));
				
				glTexCoord2i(1, 0);
				glVertex2i(Math.round(drawable.getPosX() + tex.getRight()), Math.round(drawable.getPosY() + tex.getTop()));
				
				glTexCoord2i(1, 1);
				glVertex2i(Math.round(drawable.getPosX() + tex.getRight()), Math.round(drawable.getPosY() + tex.getBottom()));
				
				glTexCoord2i(0, 1);
				glVertex2i(Math.round(drawable.getPosX() + tex.getLeft()), Math.round(drawable.getPosY() + tex.getBottom()));
			glEnd();
		}
		glDisable(GL_TEXTURE_2D);
		
		if (ToolBox.settings().getBoolean("debug", "showPosition"))
		{
			glBegin(GL_QUADS);
				glColor4f(1, 0, 0, 1);
				glVertex2i(Math.round(drawable.getPosX() - 2), Math.round(drawable.getPosY() - 2));
				glVertex2i(Math.round(drawable.getPosX() + 2), Math.round(drawable.getPosY() - 2));
				glVertex2i(Math.round(drawable.getPosX() + 2), Math.round(drawable.getPosY() + 2));
				glVertex2i(Math.round(drawable.getPosX() - 2), Math.round(drawable.getPosY() + 2));
			glEnd();
		}
		
		if (drawable instanceof Collidable && ToolBox.settings().getBoolean("debug", "showCollision"))
		{
			for (Hitbox hb : ((Collidable)drawable).getHitboxes())
			{
				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
				glBegin(GL_QUADS);
				glColor4f(0, 1, 0, 1);
				glVertex2i(Math.round(drawable.getPosX() + hb.getLeft()), Math.round(drawable.getPosY() + hb.getTop()));
				glVertex2i(Math.round(drawable.getPosX() + hb.getRight()), Math.round(drawable.getPosY() + hb.getTop()));
				glVertex2i(Math.round(drawable.getPosX() + hb.getRight()), Math.round(drawable.getPosY() + hb.getBottom()));
				glVertex2i(Math.round(drawable.getPosX() + hb.getLeft()), Math.round(drawable.getPosY() + hb.getBottom()));
				glEnd();
				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			}
		}
	}
}
