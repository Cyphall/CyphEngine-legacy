package fr.cyphall.cyphengine.display;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera
{
	private Matrix4f perspectiveMatrix;
	private Vector2f pos;
	private Vector2f size;
	
	public Camera(Vector2f size, Vector2f pos)
	{
		this.pos = pos;
		this.size = size;
		rebuildPerspectiveMatrix();
	}
	
	public void setSize(Vector2f size)
	{
		this.size = size;
		rebuildPerspectiveMatrix();
	}
	
	public void setPos(Vector2f pos)
	{
		this.pos = pos;
		rebuildPerspectiveMatrix();
	}
	
	private void rebuildPerspectiveMatrix()
	{
		perspectiveMatrix = new Matrix4f().ortho(pos.x - size.x / 2, pos.x + size.x / 2, pos.y + size.y / 2, pos.y - size.y / 2, 0.5f, 1000f);
	}
	
	public Matrix4f getPerspectiveMatrix()
	{
		return new Matrix4f(perspectiveMatrix);
	}
}
