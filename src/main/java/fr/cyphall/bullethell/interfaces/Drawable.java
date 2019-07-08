package fr.cyphall.bullethell.interfaces;

import fr.cyphall.bullethell.display.Texture;

import java.util.ArrayList;

public interface Drawable extends Positionable
{
	ArrayList<Texture> getTextures();
}
