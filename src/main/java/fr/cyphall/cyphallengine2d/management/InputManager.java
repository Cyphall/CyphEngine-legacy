package fr.cyphall.cyphallengine2d.management;

import fr.cyphall.cyphallengine2d.core.ToolBox;
import org.lwjgl.glfw.GLFW;

public class InputManager
{
	public boolean isActionPerformed(String action)
	{
		return GLFW.glfwGetKey(ToolBox.window().getHandler(), ToolBox.settings().getInt("keys", action)) == 1;
	}
}
