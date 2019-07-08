package fr.cyphall.cyphallengine2d.physics;

public class Hitbox
{
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
	
	public boolean collidesWith(Hitbox hb)
	{
		if (this.getLeft() > hb.getRight()) return false;
		if (this.getRight() < hb.getLeft()) return false;
		if (this.getTop() > hb.getBottom()) return false;
		//noinspection RedundantIfStatement
		if (this.getBottom() < hb.getTop()) return false;
		
		return true;
	}
}