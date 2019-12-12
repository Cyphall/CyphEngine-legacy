package fr.cyphall.cyphengine;

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
	
	public float getAbsoluteLeft()
	{
		return getEntity().getAbsolutePos().x + left;
	}
	
	public float getAbsoluteTop()
	{
		return getEntity().getAbsolutePos().y + top;
	}
	
	public float getAbsoluteRight()
	{
		return getEntity().getAbsolutePos().x + right;
	}
	
	public float getAbsoluteBottom()
	{
		return getEntity().getAbsolutePos().y + bottom;
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