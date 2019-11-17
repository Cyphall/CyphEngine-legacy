package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.Script;
import fr.cyphall.cyphengine.component.SpriteRenderer;
import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class PlayerScript extends Script
{
	private int fireCD = 0;
	private Vector2f pos;
	
	@Override
	public void init()
	{
		pos = new Vector2f(getEntity().getAbsolutePos());
	}
	
	@Override
	public void update()
	{
		if (fireCD > 0) fireCD--;
		
		float speed = 1f;
		if (ToolBox.inputs().isActionPerformed("up")) pos.y -= speed;
		if (ToolBox.inputs().isActionPerformed("down")) pos.y += speed;
		if (ToolBox.inputs().isActionPerformed("left")) pos.x -= speed;
		if (ToolBox.inputs().isActionPerformed("right")) pos.x += speed;
		
		getEntity().setRelativePos(new Vector2i((int)pos.x, (int)pos.y));
		
		if (ToolBox.inputs().isActionPerformed("fire") && fireCD == 0)
		{
			fire(getEntity());
			getEntity().getChilds().forEach(this::fire);
			fireCD = 15;
		}
	}
	
	private void fire(Entity entity)
	{
		Entity bullet = new Entity("allyBullet", "bullet");
		bullet.setRelativePos(new Vector2i(entity.getAbsolutePos()).add(new Vector2i(0, -1)));
		bullet.addComponent(new SpriteRenderer("bullet", 2));
		bullet.addComponent(new Hitbox(0, -5, 1, 5));
		bullet.addComponent(new BulletScript());
		getEntity().getScene().addEntity(bullet);
	}
	
	@Override
	public void onCollision(Hitbox yours, Hitbox other)
	{
		if (other.getEntity().getType().equals("enemy"))
		{
			getEntity().destroy();
		}
	}
}
