package net.kallen.solaris.graphics.shader;

import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector3;

import java.util.List;

public class TerrainShader extends Shader {

    private static final int MAX_LIGHTS = 8;

    public TerrainShader() {
        super("terrain");
    }

    public TerrainShader(String vPath, String fPath) {
        super(vPath, fPath);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textureCoordinates");
        bindAttribute(2, "normal");
    }

    public void loadProjectionMatrix(Matrix4 matrix) {
        setUniform("projection", matrix);
    }

    public void loadModelMatrix(Matrix4 matrix) {
        setUniform("model", matrix);
    }

    public void loadViewMatrix(Matrix4 matrix) {
        setUniform("view", matrix);
    }

    public void loadTexture(int textureUnit) {
        setUniform("tex", textureUnit);
    }

    public void loadUseFakeLighting(boolean use) {
        setUniform("useFakeLighting", use);
    }

    public void loadLights(List<Light> lights) {
        for(int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                super.setUniform("lightPosition[" + i + "]", lights.get(i).getPosition());
                super.setUniform("lightColor[" + i + "]", lights.get(i).getColor());
                super.setUniform("attenuation[" + i + "]", lights.get(i).getAttenuation());
            } else {
                super.setUniform("lightPosition[" + i + "]", Vector3.ZERO);
                super.setUniform("lightColor[" + i + "]", Vector3.ZERO);
                super.setUniform("attenuation[" + i + "]", Vector3.UNIT_X);
            }
        }
    }

    public void loadAmbientLightStrength(float strength) {
        super.setUniform("ambientStrength", strength);
    }

    public void loadShine(float damper, float reflectivity) {
        super.setUniform("shine", damper);
        super.setUniform("reflectivity", reflectivity);
    }

    public void loadFogColor(Vector3 color) {
        super.setUniform("fogColor", color);
    }

}