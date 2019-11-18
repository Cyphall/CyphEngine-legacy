package fr.cyphall.cyphengine.component;

public class Hitbox extends Component
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
	
	public int getAbsoluteLeft()
	{
		return getEntity().getAbsolutePos().x + getLeft();
	}
	
	public int getAbsoluteTop()
	{
		return getEntity().getAbsolutePos().y + getTop();
	}
	
	public int getAbsoluteRight()
	{
		return getEntity().getAbsolutePos().x + getRight();
	}
	
	public int getAbsoluteBottom()
	{
		return getEntity().getAbsolutePos().y + getBottom();
	}
	
	public boolean collidesWith(Hitbox other)
	{
		if (this.getAbsoluteLeft() >= other.getAbsoluteRight()) return false;
		if (this.getAbsoluteRight() <= other.getAbsoluteLeft()) return false;
		if (this.getAbsoluteTop() >= other.getAbsoluteBottom()) return false;
		//noinspection RedundantIfStatement
		if (this.getAbsoluteBottom() <= other.getAbsoluteTop()) return false;
		
		return true;
	}
}