package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.core.Scene;
import fr.cyphall.cyphengine.display.Camera;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2i;

public class MainScene extends Scene
{
	public MainScene(Vector2i size, Camera camera)
	{
		super(size, camera);
	}
	
	@Override
	protected void init()
	{
		Entity player = new Entity("ally", "player");
		player.addComponent(new PlayerScript());
		
		Entity enemy = new Entity("enemy");
		enemy.addComponent(new EnemyScript());
		
		addEntity(player);
		addEntity(enemy);
	}
}
