package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.Script;
import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.core.Game;
import fr.cyphall.cyphengine.core.Scene;
import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

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
		
		while(!glfwWindowShouldClose(ToolBox.window().getHandler()))
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