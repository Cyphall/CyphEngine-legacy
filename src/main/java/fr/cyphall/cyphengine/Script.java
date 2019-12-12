package fr.cyphall.cyphengine;

public abstract class Script extends Component
{
	private boolean skipNextUpdate = true;
	
	public void init(){}
	void internal_update()
	{
		if(skipNextUpdate)
		{
			skipNextUpdate = false;
			return;
		}
		update();
	}
	public void update(){}
	public void onCollision(Hitbox yours, Hitbox other){}
}
