package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.Script;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2i;

public class BulletScript extends Script
{
	@Override
	public void update()
	{
		getEntity().move(new Vector2i(0, -3));
		
		if (getEntity().getAbsolutePos().y < -10)
			getEntity().destroy();
	}
	
	@Override
	public void onCollision(Hitbox yours, Hitbox other)
	{
		if (other.getEntity().getType().equals("enemy"))
		{
			other.getEntity().destroy();
			getEntity().destroy();
		}
	}
}
