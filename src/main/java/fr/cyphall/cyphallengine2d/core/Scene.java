package fr.cyphall.cyphallengine2d.core;

import fr.cyphall.cyphallengine2d.component.Component;
import fr.cyphall.cyphallengine2d.entity.Entity;

import java.util.ArrayList;

public class Scene
{
	private static ArrayList<Entity> entities = new ArrayList<>();
	
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}
	
	public void addEntity(Entity entity)
	{
		entities.add(entity);
	}
}
