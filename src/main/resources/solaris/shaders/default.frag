#version 330 core

in vec2 passTexCoord;
out vec4 fragColor;

uniform sampler2D tex;
uniform vec3 lightColor;

void main() {
    fragColor = texture(tex, passTexCoord);
}