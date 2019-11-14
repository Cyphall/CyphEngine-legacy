package fr.cyphall.cyphallengine2d.entity;

import fr.cyphall.cyphallengine2d.component.Component;
import org.joml.Vector2f;

import java.util.ArrayList;

public class Entity
{
	private Vector2f pos = new Vector2f(0);
	
	private ArrayList<Component> components = new ArrayList<>();
	
	private ArrayList<Entity> childs = new ArrayList<>();
	private Entity parent;
	
	public void setRelativePos(Vector2f pos)
	{
		this.pos = pos;
	}
	
	public Vector2f getRelativePos()
	{
		return this.pos;
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
	
	public void setParent(Entity parent)
	{
		this.parent = parent;
	}
	
	public Entity getParent()
	{
		return parent;
	}
	
	public void addComponent(Component component)
	{
		components.add(component);
		component.setEntity(this);
	}
}
