#version 330 core

in vec2 passTextureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector[8];
in vec3 toCameraVector;
in float visibility;

out vec4 fragColor;

uniform sampler2D tex;
uniform vec3 lightColor[8];
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
        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1, 0.0);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shine);

        totalDiffuse = totalDiffuse + brightness * lightColor[i];
        totalSpecular = totalSpecular + dampedFactor * reflectivity * lightColor[i];
    }
    totalDiffuse = max(totalDiffuse, ambientStrength);


    vec3 lighting = totalDiffuse + totalSpecular;

    fragColor = vec4(lighting, 1.0) * texture(tex, passTextureCoordinates);
    fragColor = mix(vec4(fogColor, 1.0), fragColor, visibility);
}