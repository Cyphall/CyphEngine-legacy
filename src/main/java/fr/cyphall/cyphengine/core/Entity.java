package fr.cyphall.cyphengine.core;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Entity
{
	private Vector2f pos;
	private String type = "";
	private String id = "";
	
	private ArrayList<Component> components = new ArrayList<>();
	
	private ArrayList<Entity> childs = new ArrayList<>();
	private Entity parent;
	private Scene scene;
	
	public Entity(Vector2f pos)
	{
		this.pos = pos;
	}
	
	public Entity()
	{
		this(new Vector2f(0));
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
	public <T extends Component> T addComponent(T component)
	{
		components.add(component);
		component.setEntity(this);
		
		if (scene != null)
			scene.addComponent(component);
		
		return component;
	}
	
	void setScene(Scene scene)
	{
		this.scene = scene;
		
		if (scene == null) return;
		
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
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getID()
	{
		return id;
	}
	public void setID(String id)
	{
		this.id = id;
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
	
	public Entity instantiate(Entity entity, Entity parent)
	{
		if (!exists()) throw new IllegalStateException("Cannot instantiate an entity in a null scene");
		return scene.instantiate(entity, parent);
	}
	
	public Entity instantiate(Entity entity)
	{
		if (!exists()) throw new IllegalStateException("Cannot instantiate an entity in a null scene");
		return scene.instantiate(entity);
	}
}
