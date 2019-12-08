package fr.cyphall.cyphengine.core;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Entity
{
	private Vector2f pos = new Vector2f(0);
	private float rotation;
	private String type;
	private String id;
	
	private ArrayList<Component> components = new ArrayList<>();
	
	private ArrayList<Entity> childs = new ArrayList<>();
	private Entity parent;
	private Scene scene;
	
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
		if (parent == null) return getRelativePos();
		return parent.getAbsolutePos().add(pos);
	}
	public void move(Vector2f offset)
	{
		pos.add(offset);
	}
	
	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}
	
	public float getRotation()
	{
		return rotation;
	}
	
	public void rotate(float rotation)
	{
		this.rotation += rotation;
	}
	
	public ArrayList<Entity> getChilds()
	{
		return childs;
	}
	public void addChild(Entity child)
	{
		internal_addChild(child);
		child.internal_setParent(this);
	}
	private void internal_addChild(Entity child)
	{
		childs.add(child);
	}
	public void removeChild(Entity child)
	{
		childs.remove(child);
	}
	
	public void setParent(Entity parent)
	{
		internal_setParent(parent);
		parent.internal_addChild(this);
	}
	private void internal_setParent(Entity parent)
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
	
	void setScene(Scene scene)
	{
		this.scene = scene;
		components.forEach(scene::addComponent);
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
		internal_destroy();
		
		if (parent != null)
			parent.getChilds().remove(this);
	}
	private void internal_destroy()
	{
		scene.destroyEntity(this);
		
		scene = null;
		
		childs.forEach(Entity::internal_destroy);
	}
	
	public boolean exists()
	{
		return scene != null;
	}
	
	public Component getComponent(Class<? extends Component> clazz)
	{
		return components.stream().filter(clazz::isInstance).findFirst().orElse(null);
	}
}
