package fr.cyphall.cyphallengine2d.interfaces;

import fr.cyphall.cyphallengine2d.display.Texture;

import java.util.ArrayList;

public interface Drawable extends Positionable
{
	ArrayList<Texture> getTextures();
}
