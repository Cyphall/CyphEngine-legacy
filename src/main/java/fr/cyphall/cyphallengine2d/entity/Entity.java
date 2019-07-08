package fr.cyphall.cyphallengine2d.entity;

import fr.cyphall.cyphallengine2d.interfaces.Positionable;

public abstract class Entity implements Positionable
{
	private float posX = 0.0f;
	private float posY = 0.0f;
	
	public void setPos(float x, float y)
	{
		posX = x;
		posY = y;
	}
	
	public void setPosX(float x)
	{
		posX = x;
	}
	
	public void setPosY(float y)
	{
		posY = y;
	}
	
	@Override
	public float getPosX()
	{
		return posX;
	}
	
	@Override
	public float getPosY()
	{
		return posY;
	}
	
	public void move(float x, float y)
	{
		posX += x;
		posY += y;
	}
}
