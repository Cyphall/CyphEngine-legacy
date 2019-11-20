package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.Script;
import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2f;

public class EnemyScript extends Script
{
	private int fireCD = 0;
	private float speed = 1.5f;
	
	@Override
	public void init()
	{
		getEntity().setRelativePos(new Vector2f(150, 50));
		getEntity().addComponent(new SpriteRenderer("enemy1", 20));
		getEntity().addComponent(new Hitbox(-12, -10, 12, 10));
	}
	
	@Override
	public void update()
	{
		if (fireCD > 0) fireCD--;
		
		Vector2f pos = getEntity().getAbsolutePos();
		
		pos.x += speed;
		
		getEntity().setRelativePos(pos);
		
		if (pos.x < 50 || pos.x > 250) speed *= -1;
		
		fire(getEntity());
	}
	
	private void fire(Entity entity)
	{
		if (!entity.exists()) return;
		
		if (fireCD > 0) return;
		fireCD = 60;
		
		Entity bullet = new Entity("enemyBullet");
		bullet.setRelativePos(entity.getAbsolutePos().add(0, 15));
		bullet.addComponent(new SpriteRenderer("bullet2", 10));
		bullet.addComponent(new Hitbox(-1, -7, 1, 7));
		
		BulletScript script = new BulletScript();
		script.setDirection(new Vector2f(0, 1), 2);
		script.setTarget("ally");
		bullet.addComponent(script);
		
		getEntity().getScene().addEntity(bullet);
	}
}
