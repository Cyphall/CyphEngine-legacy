package fr.cyphall.cyphengine.core;

import fr.cyphall.cyphengine.component.Component;
import fr.cyphall.cyphengine.component.Hitbox;
import fr.cyphall.cyphengine.component.Script;
import fr.cyphall.cyphengine.entity.Entity;
import org.joml.Vector2i;

import java.util.*;

public class Scene
{
	private ArrayList<Entity> entities = new ArrayList<>();
	private HashMap<Class<? extends Component>, ArrayList<Component>> components = new HashMap<>();
	
	private ArrayList<Entity> destroyedEntitiesBuffer = new ArrayList<>();
	private ArrayList<Entity> newEntitiesBuffer = new ArrayList<>();
	private ArrayList<Script> newScriptsBuffer = new ArrayList<>();
	private HashMap<Hitbox, Hitbox> collisions = new HashMap<>();
	
	private Vector2i size;
	
	public Scene(Vector2i size)
	{
		this.size = size;
	}
	
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}
	
	public void addEntity(Entity entity)
	{
		newEntitiesBuffer.add(entity);
	}
	
	public void destroyEntity(Entity entity)
	{
		destroyedEntitiesBuffer.add(entity);
	}
	
	public void addComponent(Component component)
	{
		Class<? extends Component> clazz;
		
		if (component instanceof Script)
			clazz = Script.class;
		else
			clazz = component.getClass();
		
		if (!components.containsKey(clazz))
			components.put(clazz, new ArrayList<>());
		
		components.get(clazz).add(component);
	}
	
	public void update()
	{
		initPendingScripts();
		
		
		ArrayList<Component> hitboxes = getComponentsByClass(Hitbox.class);
		for (int i = 0; i < hitboxes.size()-1; i++)
		{
			for (int j = i+1; j < hitboxes.size(); j++)
			{
				Hitbox hitbox1 = (Hitbox)hitboxes.get(i);
				Hitbox hitbox2 = (Hitbox)hitboxes.get(j);
				if (hitbox1.isEnabled() && hitbox2.isEnabled() && hitbox1.collidesWith(hitbox2))
				{
					collisions.put(hitbox1, hitbox2);
				}
			}
		}
		
		for (Map.Entry<Hitbox, Hitbox> entry : collisions.entrySet())
		{
			entry.getKey().getEntity().getComponents(Script.class).forEach(s -> ((Script)s).onCollision(entry.getKey(), entry.getValue()));
			entry.getValue().getEntity().getComponents(Script.class).forEach(s -> ((Script)s).onCollision(entry.getValue(), entry.getKey()));
		}
		collisions.clear();
		
		for (Component script : getComponentsByClass(Script.class))
		{
			((Script)script).update();
		}
		
		ToolBox.window().render();
		
		
		destroyPendingEntities();
		addPendingEntities();
	}
	
	private void destroyPendingEntities()
	{
		destroyedEntitiesBuffer.forEach(e -> {
			entities.remove(e);
			e.getComponents().forEach(c -> {
				if (c instanceof Script)
				{
					getComponentsByClass(Script.class).remove(c);
				}
				else
				{
					getComponentsByClass(c.getClass()).remove(c);
				}
			});
		});
	}
	
	public ArrayList<Component> getComponentsByClass(Class<? extends Component> clazz)
	{
		return components.containsKey(clazz) ? components.get(clazz) : new ArrayList<>();
	}
	
	private void addPendingEntities()
	{
		for (Entity entity : newEntitiesBuffer)
		{
			entities.add(entity);
			entity.setScene(this);
			entity.getComponents().forEach(this::addComponent);
			
			for (Component component : entity.getComponents())
			{
				if (component instanceof Script)
				{
					newScriptsBuffer.add((Script)component);
				}
			}
		}
		
		newEntitiesBuffer.clear();
	}
	
	private void initPendingScripts()
	{
		newScriptsBuffer.forEach(Script::init);
		newScriptsBuffer.clear();
	}
	
	public Vector2i getSize()
	{
		return size;
	}
}
