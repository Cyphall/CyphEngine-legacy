package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.Script;
import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class EnemyScript extends Script
{
	private int fireCD = 0;
	private Vector2f pos;
	private float speed = 1.5f;
	
	@Override
	public void init()
	{
		getEntity().setRelativePos(new Vector2i(150, 50));
		getEntity().addComponent(new SpriteRenderer("enemy1"));
		getEntity().addComponent(new Hitbox(-12, -10, 12, 10));
		
		pos = new Vector2f(getEntity().getAbsolutePos());
		((SpriteRenderer)getEntity().getComponent(SpriteRenderer.class)).setDepth(3);
	}
	
	@Override
	public void update()
	{
		pos.x += speed;
		
		if (pos.x < 50 || pos.x > 250) speed *= -1;
		
		if (fireCD == 0)
		{
			fire(getEntity());
			fireCD = 60;
		}
		fireCD--;
		
		getEntity().setRelativePos(new Vector2i((int)pos.x, (int)pos.y));
	}
	
	private void fire(Entity entity)
	{
		Entity bullet = new Entity("enemyBullet");
		bullet.setRelativePos(new Vector2i(entity.getAbsolutePos()).add(new Vector2i(0, 15)));
		bullet.addComponent(new SpriteRenderer("bullet2", 2));
		bullet.addComponent(new Hitbox(-1, -7, 1, 7));
		
		BulletScript script = new BulletScript();
		script.setDirection(new Vector2f(0, 1), 2);
		script.setTarget("ally");
		bullet.addComponent(script);
		
		getEntity().getScene().addEntity(bullet);
	}
}