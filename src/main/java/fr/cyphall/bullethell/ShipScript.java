package fr.cyphall.bullethell;

import fr.cyphall.cyphallengine2d.component.Script;
import fr.cyphall.cyphallengine2d.core.ToolBox;
import org.joml.Vector2f;

public class ShipScript extends Script
{
	private float speed = 2.5f;
	
	@Override
	public void update()
	{
		Vector2f moveOffset = new Vector2f();
		
		if (ToolBox.inputs().isActionPerformed("up")) moveOffset.y -= speed;
		if (ToolBox.inputs().isActionPerformed("down")) moveOffset.y += speed;
		if (ToolBox.inputs().isActionPerformed("left")) moveOffset.x -= speed;
		if (ToolBox.inputs().isActionPerformed("right")) moveOffset.x += speed;
		
		getEntity().move(moveOffset);
	}
}
