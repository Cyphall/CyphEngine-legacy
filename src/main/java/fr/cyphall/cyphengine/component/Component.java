package fr.cyphall.cyphengine.component;

import fr.cyphall.cyphengine.entity.Entity;

public abstract class Component
{
	private Entity entity;
	private boolean enabled = true;
	
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public void enable()
	{
		enabled = true;
	}
	
	public void disable()
	{
		enabled = false;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
}
