package fr.cyphall.cyphengine.core;

import fr.cyphall.cyphengine.entity.Entity;

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
