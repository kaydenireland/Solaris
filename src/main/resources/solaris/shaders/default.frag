#version 330 core

in vec2 passTextureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 fragColor;

uniform sampler2D tex;
uniform vec3 lightColor;
uniform float ambientStrength;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.0);
    vec3 ambient = ambientStrength * lightColor;
    vec3 diffuse = brightness * lightColor;

    vec3 lighting = ambient + diffuse;

    fragColor = vec4(lighting, 1.0) * texture(tex, passTextureCoordinates);
}