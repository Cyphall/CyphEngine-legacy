package fr.cyphall.bullethell.entity;

import fr.cyphall.bullethell.display.Texture;
import fr.cyphall.bullethell.interfaces.Collidable;
import fr.cyphall.bullethell.interfaces.Drawable;
import fr.cyphall.bullethell.physics.Hitbox;

import java.util.ArrayList;

public class Player extends Entity implements Drawable, Collidable
{
	private ArrayList<Texture> textures = new ArrayList<>();
	private ArrayList<Hitbox> hitboxes = new ArrayList<>();
	
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
