package fr.cyphall.cyphallengine2d.component;

import fr.cyphall.cyphallengine2d.entity.Entity;

import java.util.ArrayList;
import java.util.List;

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
