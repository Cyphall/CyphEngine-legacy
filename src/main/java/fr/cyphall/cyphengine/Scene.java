package fr.cyphall.cyphengine;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL46.*;

public abstract class Scene
{
	private Camera camera;
	
	private ArrayList<Entity> entities = new ArrayList<>();
	private HashMap<Class<? extends Component>, ArrayList<Component>> components = new HashMap<>();
	
	private ArrayList<Entity> destroyedEntitiesBuffer = new ArrayList<>();
	private ArrayList<NewEntityData> newEntitiesBuffer = new ArrayList<>();
	private ArrayList<Component> newComponentsBuffer = new ArrayList<>();
	private ArrayList<Script> newScriptsBuffer = new ArrayList<>();
	private HashMap<Hitbox, Hitbox> collisions = new HashMap<>();
	
	private Vector2i size;
	
	public Scene(Vector2i size)
	{
		this(size, new Camera(
				new Vector2f(size),
				new Vector2f(size).mul(0.5f)
		));
	}
	
	public Scene(Vector2i size, Camera camera)
	{
		this.size = size;
		this.camera = camera;
		init();
	}
	
	protected abstract void init();
	
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}
	
	void destroyEntity(Entity entity)
	{
		destroyedEntitiesBuffer.add(entity);
	}
	
	void addComponent(Component component)
	{
		newComponentsBuffer.add(component);
	}
	
	void update()
	{
		destroyPendingEntities();
		addPendingEntities();
		addPendingComponents();
		initPendingScripts();
		
		
		ArrayList<Component> hitboxes = getComponentsByClass(Hitbox.class);
		for (int i = 0; i < hitboxes.size()-1; i++)
		{
			for (int j = i+1; j < hitboxes.size(); j++)
			{
				Hitbox hitbox1 = (Hitbox)hitboxes.get(i);
				Hitbox hitbox2 = (Hitbox)hitboxes.get(j);
				if (hitbox1.isEnabled() && hitbox2.isEnabled() && hitbox1.collidesWith(hitbox2))
				{
					collisions.put(hitbox1, hitbox2);
				}
			}
		}
		
		for (Map.Entry<Hitbox, Hitbox> entry : collisions.entrySet())
		{
			entry.getKey().getEntity().getComponents(Script.class).forEach(s -> {
				if (s.isEnabled()) ((Script)s).onCollision(entry.getKey(), entry.getValue());
			});
			entry.getValue().getEntity().getComponents(Script.class).forEach(s -> {
				if (s.isEnabled()) ((Script)s).onCollision(entry.getValue(), entry.getKey());
			});
		}
		collisions.clear();
		
		for (Component script : getComponentsByClass(Script.class))
		{
			if (script.isEnabled()) ((Script)script).internal_update();
		}
		
		render();
	}
	
	private void destroyPendingEntities()
	{
		destroyedEntitiesBuffer.forEach(e -> {
			entities.remove(e);
			e.setScene(null);
			e.getComponents().forEach(c -> {
				if (c instanceof Script)
				{
					getComponentsByClass(Script.class).remove(c);
				}
				else
				{
					getComponentsByClass(c.getClass()).remove(c);
				}
			});
		});
	}
	
	public ArrayList<Component> getComponentsByClass(Class<? extends Component> clazz)
	{
		return components.containsKey(clazz) ? components.get(clazz) : new ArrayList<>();
	}
	
	private void addPendingEntities()
	{
		for (int i = 0; i < newEntitiesBuffer.size(); i++)
		{
			NewEntityData data = newEntitiesBuffer.get(i);
			
			if (data.parent != null) data.entity.setParent(data.parent);
			data.entity.setScene(this);
			
			entities.add(data.entity);
			
			data.entity.setRelativePos(data.pos);
		}
		
		newEntitiesBuffer.clear();
	}
	
	private void addPendingComponents()
	{
		for (Component component : newComponentsBuffer)
		{
			Class<? extends Component> clazz;
			
			if (component instanceof Script)
				clazz = Script.class;
			else
				clazz = component.getClass();
			
			if (!components.containsKey(clazz))
				components.put(clazz, new ArrayList<>());
			
			ArrayList<Component> list = components.get(clazz);
			if (component instanceof Script)
			{
				newScriptsBuffer.add((Script)component);
				list.add(component);
			}
			else if (component instanceof SpriteRenderer)
			{
				int i;
				for (i = 0; i < list.size(); i++)
				{
					if (((SpriteRenderer)component).getDepth() >= ((SpriteRenderer)list.get(i)).getDepth()) break;
				}
				list.add(i, component);
			}
			else
			{
				list.add(component);
			}
		}
		
		newComponentsBuffer.clear();
	}
	
	private void initPendingScripts()
	{
		newScriptsBuffer.forEach(Script::init);
		newScriptsBuffer.clear();
	}
	
	public Vector2i getSize()
	{
		return new Vector2i(size);
	}
	
	public Entity instantiate(Entity entity, Vector2f pos, Entity parent)
	{
		newEntitiesBuffer.add(new NewEntityData(entity, parent, pos));
		return entity;
	}
	
	public Entity instantiate(Entity entity, Vector2f pos)
	{
		return instantiate(entity, pos, null);
	}
	
	private void render()
	{
		glEnable(GL_TEXTURE_2D);
		
		glColor4f(1, 1, 1, 1);
		getComponentsByClass(SpriteRenderer.class).forEach(c -> ((SpriteRenderer)c).render(camera.getPerspectiveMatrix()));
		
		glDisable(GL_TEXTURE_2D);
		
		if (ToolBox.settings().getBoolean("debug", "showPosition"))
		{
			for (Entity entity : getEntities())
			{
//				glBegin(GL_QUADS);
//				glColor4f(1, 0, 0, 1);
//					glVertex3f((entity.getAbsolutePos().x - 1), (entity.getAbsolutePos().y - 1), -0.7f);
//					glVertex3f((entity.getAbsolutePos().x + 2), (entity.getAbsolutePos().y - 1), -0.7f);
//					glVertex3f((entity.getAbsolutePos().x + 2), (entity.getAbsolutePos().y + 2), -0.7f);
//					glVertex3f((entity.getAbsolutePos().x - 1), (entity.getAbsolutePos().y + 2), -0.7f);
//				glEnd();
			}
		}

		if (ToolBox.settings().getBoolean("debug", "showHitbox"))
		{
			for (Component component : getComponentsByClass(Hitbox.class))
			{
				Hitbox hitbox = (Hitbox) component;
//				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
//				glBegin(GL_QUADS);
//					glColor4f(0, 1, 0, 1);
//					glVertex3f(hitbox.getAbsoluteLeft() * pixelScale + 0.01f, hitbox.getAbsoluteTop() * pixelScale + 0.01f, -0.6f);
//					glVertex3f(hitbox.getAbsoluteRight() * pixelScale - 0.01f, hitbox.getAbsoluteTop() * pixelScale + 0.01f, -0.6f);
//					glVertex3f(hitbox.getAbsoluteRight() * pixelScale - 0.01f, hitbox.getAbsoluteBottom() * pixelScale - 0.01f, -0.6f);
//					glVertex3f(hitbox.getAbsoluteLeft() * pixelScale + 0.01f, hitbox.getAbsoluteBottom() * pixelScale - 0.01f, -0.6f);
//				glEnd();
//				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			}
		}
	}
	
	class NewEntityData
	{
		public Entity entity;
		public Entity parent;
		public Vector2f pos;
		
		public NewEntityData(Entity entity, Entity parent, Vector2f pos)
		{
			this.entity = entity;
			this.parent = parent;
			this.pos = pos;
		}
	}
}
