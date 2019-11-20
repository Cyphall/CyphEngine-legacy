package fr.cyphall.cyphengine.component;

public class Hitbox extends Component
{
	private final float left;
	private final float top;
	private final float right;
	private final float bottom;
	
	public Hitbox(float left, float top, float right, float bottom)
	{
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public float getLeft()
	{
		return left;
	}
	
	public float getTop()
	{
		return top;
	}
	
	public float getRight()
	{
		return right;
	}
	
	public float getBottom()
	{
		return bottom;
	}
	
	public float getAbsoluteLeft()
	{
		return getEntity().getAbsolutePos().x + getLeft();
	}
	
	public float getAbsoluteTop()
	{
		return getEntity().getAbsolutePos().y + getTop();
	}
	
	public float getAbsoluteRight()
	{
		return getEntity().getAbsolutePos().x + getRight();
	}
	
	public float getAbsoluteBottom()
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