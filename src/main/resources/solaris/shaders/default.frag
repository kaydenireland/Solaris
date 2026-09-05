#version 330 core

in vec2 passTextureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 fragColor;

uniform sampler2D tex;
uniform vec3 lightColor;
uniform float ambientStrength;

uniform float shine;
uniform float reflectivity;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.0);
    vec3 ambient = ambientStrength * lightColor;
    vec3 diffuse = brightness * lightColor;

    vec3 unitVectorToCamera = normalize(toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shine);
    vec3 finalSpecular = dampedFactor * reflectivity * lightColor;

    vec3 lighting = ambient + diffuse + finalSpecular;

    fragColor = vec4(lighting, 1.0) * texture(tex, passTextureCoordinates);
}