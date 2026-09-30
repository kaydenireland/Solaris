#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoordinates;
layout (location = 2) in vec3 normal;

out vec2 passTextureCoordinates;
out vec3 surfaceNormal;
out vec3 toLightVector[8];
out vec3 toCameraVector;
out float visibility;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 lightPosition[8];
uniform float useFakeLighting;

const float density = 0.007;
const float gradient = 1.5;

void main() {
    vec4 worldPosition = model * vec4(position, 1.0);
    vec4 positionRelativeToCamera = view * worldPosition;

    gl_Position = projection * positionRelativeToCamera;
    passTextureCoordinates = textureCoordinates;

    vec3 actualNormal = normal;
    if (useFakeLighting > 0.5) {
        actualNormal = vec3(0.0, 1.0, 0.0);
    }

    surfaceNormal = (model * vec4(actualNormal, 0.0)).xyz;
    for(int i = 0; i < 8; i++) {
        toLightVector[i]  = lightPosition[i] - worldPosition.xyz;
    }
    toCameraVector = (inverse(view) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

    float distance = length(positionRelativeToCamera.xyz);
    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}