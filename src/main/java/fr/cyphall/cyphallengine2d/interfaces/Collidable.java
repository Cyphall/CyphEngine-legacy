package fr.cyphall.cyphallengine2d.interfaces;

import fr.cyphall.cyphallengine2d.physics.Hitbox;

import java.util.ArrayList;

public interface Collidable extends Positionable
{
	ArrayList<Hitbox> getHitboxes();
}
