package fr.cyphall.cyphengine.core;

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
	
	public Entity instantiate(Entity entity, Entity parent)
	{
		return this.entity.instantiate(entity, parent);
	}
	
	public Entity instantiate(Entity entity)
	{
		return this.entity.instantiate(entity);
	}
}
