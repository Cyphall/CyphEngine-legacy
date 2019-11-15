package fr.cyphall.cyphengine.component;

import fr.cyphall.cyphengine.entity.Entity;

public abstract class Component
{
	private Entity entity;
	
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
}
