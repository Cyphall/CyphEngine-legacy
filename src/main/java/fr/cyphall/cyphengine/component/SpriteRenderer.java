package fr.cyphall.cyphengine.component;

import fr.cyphall.cyphengine.core.ToolBox;
import fr.cyphall.cyphengine.display.Shader;
import fr.cyphall.cyphengine.display.TextureData;
import org.joml.Matrix4f;
import org.joml.Vector2i;

import static org.lwjgl.opengl.GL46.*;

public class SpriteRenderer extends Component
{
	private TextureData data;
	private static Shader shader;
	
	private int VAO;
	
	private int verticesBufferID;
	private int uvsBufferID;
	
	private Vector2i size;
	private int depth = 1;
	
	public SpriteRenderer(String textureName)
	{
		this.data = ToolBox.tdm().get(textureName);
		
		size = data.getSize();
		
		if (shader == null)
			shader = new Shader("default");
		
		float[] uvs = new float[]{
				0, 0,
				1, 1,
				1, 0,
				0, 0,
				0, 1,
				1, 1,
		};
		
		verticesBufferID = glGenBuffers();
		uvsBufferID = glGenBuffers();
		
		VAO = glGenVertexArrays();
		
		glBindVertexArray(VAO);
		
			glBindBuffer(GL_ARRAY_BUFFER, verticesBufferID);
				glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
				glEnableVertexAttribArray(0);
			
			glBindBuffer(GL_ARRAY_BUFFER, uvsBufferID);
				glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
				glEnableVertexAttribArray(1);
		
		glBindVertexArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, uvsBufferID);
			glBufferData(GL_ARRAY_BUFFER, uvs, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public SpriteRenderer(String textureName, int depth)
	{
		this(textureName);
		
		this.depth = Math.max(Math.min(depth, 999), 1);
	}
	
	public Vector2i getSize()
	{
		return size;
	}
	
	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	public void render(Matrix4f perspective)
	{
		if (!isEnabled()) return;
		
		Matrix4f matrix = new Matrix4f(perspective).mul(getModelMatrix());
		
		float[] vertices = new float[]{
				-getSize().x / 2.0f, -getSize().y / 2.0f, -depth,
				getSize().x / 2.0f, getSize().y / 2.0f, -depth,
				getSize().x / 2.0f, -getSize().y / 2.0f, -depth,
				-getSize().x / 2.0f, -getSize().y / 2.0f, -depth,
				-getSize().x / 2.0f, getSize().y / 2.0f, -depth,
				getSize().x / 2.0f, getSize().y / 2.0f, -depth
		};
		
		glBindBuffer(GL_ARRAY_BUFFER, verticesBufferID);
			glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		if (shader.bind())
		{
			if (data.bind())
			{
				glUniformMatrix4fv(2, false, matrix.get(new float[16]));
				
				glBindVertexArray(VAO);
					glDrawArrays(GL_TRIANGLES, 0, 6);
				glBindVertexArray(0);
			}
		}
	}
	
	private Matrix4f getModelMatrix()
	{
		Matrix4f model = new Matrix4f().identity();
		model.m30(getEntity().getAbsolutePos().x);
		model.m31(getEntity().getAbsolutePos().y);
		return model;
	}
}
