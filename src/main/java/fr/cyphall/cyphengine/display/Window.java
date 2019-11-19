package fr.cyphall.cyphengine.display;

import fr.cyphall.cyphengine.component.Component;
import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
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
	private Matrix4f projection;
	
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
		
		glEnable(GL_DEPTH_TEST);
		
		this.setClearColor(0, 0, 0);
		
		glOrtho(0.0f, w, h, 0.0f, 0.5f, 1000.0f);
		projection = new Matrix4f().ortho(0.0f, w, h, 0.0f, 0.05f, 1000.0f);
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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
			if (!sprite.isEnabled()) continue;
			
			sprite.getTexture().bind();
			glBegin(GL_QUADS);
				glTexCoord2i(0, 0);
				glVertex3i((sprite.getEntity().getAbsolutePos().x - sprite.getSize().x / 2) * pixelScale, (sprite.getEntity().getAbsolutePos().y - sprite.getSize().y / 2) * pixelScale, -sprite.getDepth());
				
				glTexCoord2i(1, 0);
				glVertex3i((sprite.getEntity().getAbsolutePos().x + sprite.getSize().x / 2 + (sprite.getSize().x % 2 == 0 ? 0 : 1)) * pixelScale, (sprite.getEntity().getAbsolutePos().y - sprite.getSize().y / 2) * pixelScale, -sprite.getDepth());
				
				glTexCoord2i(1, 1);
				glVertex3i((sprite.getEntity().getAbsolutePos().x + sprite.getSize().x / 2 + (sprite.getSize().x % 2 == 0 ? 0 : 1)) * pixelScale, (sprite.getEntity().getAbsolutePos().y + sprite.getSize().y / 2 + (sprite.getSize().y % 2 == 0 ? 0 : 1)) * pixelScale, -sprite.getDepth());
				
				glTexCoord2i(0, 1);
				glVertex3i((sprite.getEntity().getAbsolutePos().x - sprite.getSize().x / 2) * pixelScale, (sprite.getEntity().getAbsolutePos().y + sprite.getSize().y / 2 + (sprite.getSize().y % 2 == 0 ? 0 : 1)) * pixelScale, -sprite.getDepth());
			glEnd();
		}
		glDisable(GL_TEXTURE_2D);
		
		if (ToolBox.settings().getBoolean("debug", "showPosition"))
		{
			for (Entity entity : ToolBox.currentScene().getEntities())
			{
				glBegin(GL_QUADS);
				glColor4f(1, 0, 0, 1);
					glVertex3f((entity.getAbsolutePos().x - 1) * pixelScale, (entity.getAbsolutePos().y - 1) * pixelScale, -0.7f);
					glVertex3f((entity.getAbsolutePos().x + 2) * pixelScale, (entity.getAbsolutePos().y - 1) * pixelScale, -0.7f);
					glVertex3f((entity.getAbsolutePos().x + 2) * pixelScale, (entity.getAbsolutePos().y + 2) * pixelScale, -0.7f);
					glVertex3f((entity.getAbsolutePos().x - 1) * pixelScale, (entity.getAbsolutePos().y + 2) * pixelScale, -0.7f);
				glEnd();
			}
		}
		
		if (ToolBox.settings().getBoolean("debug", "showHitbox"))
		{
			for (Component component : ToolBox.currentScene().getComponentsByClass(Hitbox.class))
			{
				Hitbox hitbox = (Hitbox) component;
				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
				glBegin(GL_QUADS);
					glColor4f(0, 1, 0, 1);
					glVertex3f(hitbox.getAbsoluteLeft() * pixelScale + 0.01f, hitbox.getAbsoluteTop() * pixelScale + 0.01f, -0.6f);
					glVertex3f(hitbox.getAbsoluteRight() * pixelScale - 0.01f, hitbox.getAbsoluteTop() * pixelScale + 0.01f, -0.6f);
					glVertex3f(hitbox.getAbsoluteRight() * pixelScale - 0.01f, hitbox.getAbsoluteBottom() * pixelScale - 0.01f, -0.6f);
					glVertex3f(hitbox.getAbsoluteLeft() * pixelScale + 0.01f, hitbox.getAbsoluteBottom() * pixelScale - 0.01f, -0.6f);
				glEnd();
				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			}
		}
	}
}
