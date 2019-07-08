package fr.cyphall.bullethell.interfaces;

import fr.cyphall.bullethell.physics.Hitbox;

import java.util.ArrayList;

public interface Collidable extends Positionable
{
	ArrayList<Hitbox> getHitboxes();
}
