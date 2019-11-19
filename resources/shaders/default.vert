#version 330 core

layout(location = 0) in uvec3 in_Vertex;
layout(location = 1) in vec2 in_UV;
uniform mat4 modelView;

out vec2 frag_UV;

void main()
{
	gl_Position = modelView * vec4(in_Vertex, 1);
}