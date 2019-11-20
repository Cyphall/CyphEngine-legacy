package fr.cyphall.cyphengine.entity;

import fr.cyphall.cyphengine.component.Component;
import fr.cyphall.cyphengine.core.Scene;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Entity
{
	private Vector2f pos = new Vector2f(0);
	private String type;
	private String id;
	
	private ArrayList<Component> components = new ArrayList<>();
	
	private ArrayList<Entity> childs = new ArrayList<>();
	private Entity parent;
	private Scene scene;
	private boolean exists = true;
	
	public Entity(String type, String id)
	{
		this.type = type;
		this.id = id;
	}
	
	public Entity(String type)
	{
		this(type, "");
	}
	
	public Entity()
	{
		this("default");
	}
	
	public void setRelativePos(Vector2f pos)
	{
		this.pos = pos;
	}
	
	public Vector2f getRelativePos()
	{
		return new Vector2f(pos);
	}
	
	public Vector2f getAbsolutePos()
	{
		if (parent == null) return pos;
		return new Vector2f(pos).add(parent.getAbsolutePos());
	}
	
	public void move(Vector2f offset)
	{
		pos.add(offset);
	}
	
	public ArrayList<Entity> getChilds()
	{
		return childs;
	}
	
	public void addChild(Entity child)
	{
		childs.add(child);
		child.setParent(this);
	}
	
	public void removeChild(Entity child)
	{
		childs.remove(child);
	}
	
	private void setParent(Entity parent)
	{
		this.parent = parent;
	}
	
	public Entity getParent()
	{
		return parent;
	}
	
	public ArrayList<Component> getComponents()
	{
		return components;
	}
	public ArrayList<Component> getComponents(Class<? extends Component> clazz)
	{
		return (ArrayList<Component>)components.stream().filter(clazz::isInstance).collect(Collectors.toList());
	}
	
	public void addComponent(Component component)
	{
		components.add(component);
		component.setEntity(this);
		
		if (scene != null)
			scene.addComponent(component);
	}
	
	public void setScene(Scene scene)
	{
		this.scene = scene;
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getID()
	{
		return id;
	}
	
	public void destroy()
	{
		unsafeDestroy();
		
		if (parent != null)
			parent.getChilds().remove(this);
	}
	
	private void unsafeDestroy()
	{
		scene.destroyEntity(this);
		
		exists = false;
		
		childs.forEach(Entity::unsafeDestroy);
	}
	
	public boolean exists()
	{
		return exists;
	}
	
	public Component getComponent(Class<? extends Component> clazz)
	{
		return components.stream().filter(clazz::isInstance).findFirst().orElse(null);
	}
}
