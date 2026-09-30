#version 330 core

in vec2 passTextureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[8];
in vec3 toCameraVector;
in float visibility;

out vec4 fragColor;

uniform sampler2D tex;
uniform vec3 lightColor[8];
uniform vec3 attenuation[8];
uniform float ambientStrength;

uniform float shine;
uniform float reflectivity;
uniform vec3 fogColor;

void main() {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for(int i = 0; i < 8; i++) {
        float distance = length(toLightVector[i]);
        float attenuationFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);

        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1, 0.0);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shine);

        totalDiffuse += (brightness * lightColor[i]) / attenuationFactor;
        totalSpecular += (dampedFactor * reflectivity * lightColor[i]) / attenuationFactor;
    }
    totalDiffuse = max(totalDiffuse, ambientStrength);

    vec4 textureColor = texture(tex, passTextureCoordinates);

    if (textureColor.a < 0.1) {
        discard;
    }

    vec3 lighting = totalDiffuse + totalSpecular;
    vec3 color = lighting * textureColor.rgb;
    color = mix(fogColor, color, visibility);

    fragColor = vec4(color, textureColor.a);
}