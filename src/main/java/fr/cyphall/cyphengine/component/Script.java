package fr.cyphall.cyphengine.component;

import java.util.ArrayList;

public abstract class Script extends Component
{
	private static ArrayList<Script> instances = new ArrayList<>();
	
	public Script()
	{
		instances.add(this);
		init();
	}
	
	public static ArrayList<Script> getInstances()
	{
		return instances;
	}
	
	public void init(){}
	public void update(){}
}
