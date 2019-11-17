package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.core.Game;
import fr.cyphall.cyphengine.core.Scene;
import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2f;
import org.joml.Vector2i;

import static org.lwjgl.glfw.GLFW.*;

public class Main extends Game
{
	@Override
	public void init()
	{
		ToolBox.window().setSize(600, 800);
		ToolBox.window().setTitle("Bullet Hell");
		ToolBox.window().setVisible(true);
		
		ToolBox.setCurrentScene(new Scene(ToolBox.window().getLogicalSize()));
		
		Entity ship = new Entity("ally", "player");
		ship.setRelativePos(new Vector2i(75, 50));
		ship.addComponent(new SpriteRenderer("ship"));
		ship.addComponent(new PlayerScript());
		ship.addComponent(new Hitbox(-6, -1, 7, 7));
		ship.addComponent(new Hitbox(-2, -6, 3, -1));
		
		Entity ship2 = new Entity("ally");
		ship.addChild(ship2);
		ship2.setRelativePos(new Vector2i(-20, 20));
		ship2.addComponent(new SpriteRenderer("ship2"));
		ship2.addComponent(new Hitbox(-6, -6, 7, 6));
		
		Entity ship3 = new Entity("ally");
		ship.addChild(ship3);
		ship3.setRelativePos(new Vector2i(20, 20));
		ship3.addComponent(new SpriteRenderer("ship2"));
		ship3.addComponent(new Hitbox(-6, -6, 7, 6));
		
		Entity enemy1 = new Entity("enemy");
		enemy1.setRelativePos(new Vector2i(30, 50));
		enemy1.addComponent(new SpriteRenderer("enemy", 3));
		enemy1.addComponent(new Hitbox(-6, -6, 7, 6));
		
		ToolBox.currentScene().addEntity(ship);
		ToolBox.currentScene().addEntity(ship2);
		ToolBox.currentScene().addEntity(ship3);
		ToolBox.currentScene().addEntity(enemy1);
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
			
			ToolBox.currentScene().update();
			
			ToolBox.window().swap();
		}
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		main.run();
	}
}