package fr.cyphall.cyphallengine2d.entity;

import fr.cyphall.cyphallengine2d.display.Texture;
import fr.cyphall.cyphallengine2d.interfaces.Collidable;
import fr.cyphall.cyphallengine2d.interfaces.Drawable;
import fr.cyphall.cyphallengine2d.physics.Hitbox;

import java.util.ArrayList;

public class Player extends Entity implements Drawable, Collidable
{
	private final ArrayList<Texture> textures = new ArrayList<>();
	private final ArrayList<Hitbox> hitboxes = new ArrayList<>();
	
	public Player()
	{
		super();
		
		textures.add(new Texture("ship"));
		textures.get(0).centerOffset(Texture.BOTH);
		
		hitboxes.add(new Hitbox(-5, -5, 5, 5));
	}
	
	@Override
	public ArrayList<Texture> getTextures()
	{
		return textures;
	}
	
	@Override
	public ArrayList<Hitbox> getHitboxes()
	{
		return hitboxes;
	}
}
