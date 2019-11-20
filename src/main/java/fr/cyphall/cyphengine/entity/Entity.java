package fr.cyphall.cyphengine.entity;

import fr.cyphall.cyphengine.component.Component;
import fr.cyphall.cyphengine.core.Scene;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.stream.Collectors;
//TODO: better entity instantiation
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
