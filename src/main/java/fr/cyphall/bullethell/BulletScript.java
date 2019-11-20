package fr.cyphall.bullethell;

import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.Script;
import org.joml.Vector2f;

public class BulletScript extends Script
{
	private Vector2f direction;
	String target;
	
	@Override
	public void update()
	{
		Vector2f pos = getEntity().getAbsolutePos();
		
		pos.add(direction);
		
		getEntity().setRelativePos(pos);
		
		if (getEntity().getAbsolutePos().x < 0 || getEntity().getAbsolutePos().x > getEntity().getScene().getSize().x || getEntity().getAbsolutePos().y < 0 || getEntity().getAbsolutePos().y > getEntity().getScene().getSize().y)
			getEntity().destroy();
	}
	
	public void setDirection(Vector2f direction, float speed)
	{
		this.direction = direction;
		direction.normalize(speed);
	}
	
	public void setTarget(String target)
	{
		this.target = target;
	}
	
	@Override
	public void onCollision(Hitbox yours, Hitbox other)
	{
		if (other.getEntity().getType().equals(target))
		{
			other.getEntity().destroy();
			getEntity().destroy();
		}
	}
}
