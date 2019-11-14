package fr.cyphall.bullethell;

import fr.cyphall.cyphallengine2d.component.Script;
import org.joml.Vector2f;

public class ShipScript extends Script
{
	@Override
	public void update()
	{
		getEntity().move(new Vector2f(0, -0.5f));
	}
}
