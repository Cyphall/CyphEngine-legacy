#version 460 core

in vec2 frag_UV;
uniform sampler2D tex;

out vec4 out_Color;

void main()
{
	vec4 color = texture(tex, frag_UV);

	if (color.a == 0)
		discard;

	out_Color = color;
}