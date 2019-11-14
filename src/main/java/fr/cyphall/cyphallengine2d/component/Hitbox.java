package fr.cyphall.cyphallengine2d.component;

import fr.cyphall.cyphallengine2d.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Hitbox extends Component
{
	private static ArrayList<Hitbox> instances = new ArrayList<>();
	
	private final int left;
	private final int top;
	private final int right;
	private final int bottom;
	
	public Hitbox(int left, int top, int right, int bottom)
	{
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
		instances.add(this);
	}
	
	public static ArrayList<Hitbox> getInstances()
	{
		return instances;
	}
	
	public int getLeft()
	{
		return left;
	}
	
	public int getTop()
	{
		return top;
	}
	
	public int getRight()
	{
		return right;
	}
	
	public int getBottom()
	{
		return bottom;
	}
	
	public boolean collidesWith(Hitbox other)
	{
		if (this.getLeft() > other.getRight()) return false;
		if (this.getRight() < other.getLeft()) return false;
		if (this.getTop() > other.getBottom()) return false;
		//noinspection RedundantIfStatement
		if (this.getBottom() < other.getTop()) return false;
		
		return true;
	}
}