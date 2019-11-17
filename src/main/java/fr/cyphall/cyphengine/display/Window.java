package fr.cyphall.cyphengine.display;

import fr.cyphall.cyphengine.component.Component;
import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
	private final long window;
	private int pixelScale = ToolBox.settings().getInt("display", "pixelScale");
	
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
//		glLineWidth(2);
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
	
	public long getHandler()
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
	
	public Vector2i getPhysicalSize()
	{
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		org.lwjgl.glfw.GLFW.glfwGetWindowSize(window, w ,h);
		return new Vector2i(w.get(), h.get());
	}
	
	public Vector2i getLogicalSize()
	{
		return new Vector2i(getPhysicalSize().x / pixelScale, getPhysicalSize().y / pixelScale);
	}
	
	public void render()
	{
		glEnable(GL_TEXTURE_2D);
		
		glColor4f(1, 1, 1, 1);
		for (Component component : ToolBox.currentScene().getComponentsByClass(SpriteRenderer.class))
		{
			SpriteRenderer sprite = (SpriteRenderer) component;
			sprite.getTexture().bind();
			glBegin(GL_QUADS);
				glTexCoord2i(0, 0);
				glVertex2i((sprite.getEntity().getAbsolutePos().x - sprite.getSize().x / 2) * pixelScale, (sprite.getEntity().getAbsolutePos().y - sprite.getSize().y / 2) * pixelScale);
				
				glTexCoord2i(1, 0);
				glVertex2i((sprite.getEntity().getAbsolutePos().x + sprite.getSize().x / 2 + (sprite.getSize().x % 2 == 0 ? 0 : 1)) * pixelScale, (sprite.getEntity().getAbsolutePos().y - sprite.getSize().y / 2) * pixelScale);
				
				glTexCoord2i(1, 1);
				glVertex2i((sprite.getEntity().getAbsolutePos().x + sprite.getSize().x / 2 + (sprite.getSize().x % 2 == 0 ? 0 : 1)) * pixelScale, (sprite.getEntity().getAbsolutePos().y + sprite.getSize().y / 2 + (sprite.getSize().y % 2 == 0 ? 0 : 1)) * pixelScale);
				
				glTexCoord2i(0, 1);
				glVertex2i((sprite.getEntity().getAbsolutePos().x - sprite.getSize().x / 2) * pixelScale, (sprite.getEntity().getAbsolutePos().y + sprite.getSize().y / 2 + (sprite.getSize().y % 2 == 0 ? 0 : 1)) * pixelScale);
			glEnd();
		}
		glDisable(GL_TEXTURE_2D);
		
		if (ToolBox.settings().getBoolean("debug", "showPosition"))
		{
			for (Entity entity : ToolBox.currentScene().getEntities())
			{
				glBegin(GL_QUADS);
				glColor4f(1, 0, 0, 1);
					glVertex2i((entity.getAbsolutePos().x - 1) * pixelScale, (entity.getAbsolutePos().y - 1) * pixelScale);
					glVertex2i((entity.getAbsolutePos().x + 2) * pixelScale, (entity.getAbsolutePos().y - 1) * pixelScale);
					glVertex2i((entity.getAbsolutePos().x + 2) * pixelScale, (entity.getAbsolutePos().y + 2) * pixelScale);
					glVertex2i((entity.getAbsolutePos().x - 1) * pixelScale, (entity.getAbsolutePos().y + 2) * pixelScale);
				glEnd();
			}
		}
		
		if (ToolBox.settings().getBoolean("debug", "showCollision"))
		{
			for (Component component : ToolBox.currentScene().getComponentsByClass(Hitbox.class))
			{
				Hitbox hitbox = (Hitbox) component;
				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
				glBegin(GL_QUADS);
					glColor4f(0, 1, 0, 1);
					glVertex2f(hitbox.getAbsoluteLeft() * pixelScale + 0.01f, hitbox.getAbsoluteTop() * pixelScale + 0.01f);
					glVertex2f(hitbox.getAbsoluteRight() * pixelScale - 0.01f, hitbox.getAbsoluteTop() * pixelScale + 0.01f);
					glVertex2f(hitbox.getAbsoluteRight() * pixelScale - 0.01f, hitbox.getAbsoluteBottom() * pixelScale - 0.01f);
					glVertex2f(hitbox.getAbsoluteLeft() * pixelScale + 0.01f, hitbox.getAbsoluteBottom() * pixelScale - 0.01f);
				glEnd();
				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			}
		}
	}
}
