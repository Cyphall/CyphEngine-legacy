package fr.cyphall.bullethell;

import fr.cyphall.cyphallengine2d.component.Hitbox;
import fr.cyphall.cyphallengine2d.component.Script;
import fr.cyphall.cyphallengine2d.component.SpriteRenderer;
import fr.cyphall.cyphallengine2d.core.Game;
import fr.cyphall.cyphallengine2d.core.Scene;
import fr.cyphall.cyphallengine2d.core.ToolBox;
import fr.cyphall.cyphallengine2d.entity.Entity;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main extends Game
{
	@Override
	public void init()
	{
		ToolBox.window().setSize(600, 800);
		ToolBox.window().setTitle("Bullet Hell");
		ToolBox.window().setVisible(true);
		
		ToolBox.setCurrentScene(new Scene());
		
		Entity ship = new Entity();
		ship.setRelativePos(new Vector2f(300, 700));
		ship.addComponent(new SpriteRenderer("ship"));
		ship.addComponent(new ShipScript());
		ship.addComponent(new Hitbox(-32, -32, 32, 32));
		
		Entity ship2 = new Entity();
		ship2.setParent(ship);
		ship2.setRelativePos(new Vector2f(-60, 60));
		ship2.addComponent(new SpriteRenderer("ship2"));
		ship2.addComponent(new Hitbox(-32, -32, 32, 32));
		
		Entity ship3 = new Entity();
		ship3.setParent(ship);
		ship3.setRelativePos(new Vector2f(60, 60));
		ship3.addComponent(new SpriteRenderer("ship2"));
		ship3.addComponent(new Hitbox(-32, -32, 32, 32));
		
		ToolBox.currentScene().addEntity(ship);
	}
	
	@Override
	public void loop()
	{
//		Text fps = new Text("", 10, 0, 0);
		
		while(!glfwWindowShouldClose(ToolBox.window().getRawWindow()))
		{
			ToolBox.window().clear();
			glfwPollEvents();
			
//			if (ToolBox.settings().getBoolean("debug", "showFPS"))
//			{
//				fps.setText(Float.toString(ToolBox.window().getFPS()));
//				ToolBox.window().render(fps);
//			}
			
			Script.getInstances().forEach(Script::update);
			
			ToolBox.window().render();
			
			ToolBox.window().swap();
		}
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}
}