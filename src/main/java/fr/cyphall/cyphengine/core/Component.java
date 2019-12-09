package fr.cyphall.cyphengine.core;

import org.joml.Vector2f;

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
	
	public Entity instantiate(Entity entity, Vector2f pos, Entity parent)
	{
		return this.entity.instantiate(entity, pos, parent);
	}
	
	public Entity instantiate(Entity entity, Vector2f pos)
	{
		return this.entity.instantiate(entity, pos);
	}
}
