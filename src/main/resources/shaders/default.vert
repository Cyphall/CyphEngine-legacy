#version 460 core

layout(location = 0) in vec3 in_Vertex;
layout(location = 1) in vec2 in_UV;
layout(location = 2) uniform mat4 modelViewProjection;

out vec2 frag_UV;

void main()
{
	gl_Position = modelViewProjection * vec4(in_Vertex, 1);

	frag_UV = in_UV;
}