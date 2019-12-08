package fr.cyphall.cyphengine.component;

import fr.cyphall.cyphengine.core.Component;

public abstract class Script extends Component
{
	public void init(){}
	public void update(){}
	public void onCollision(Hitbox yours, Hitbox other){}
}
