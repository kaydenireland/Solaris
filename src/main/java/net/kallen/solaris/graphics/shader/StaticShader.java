package net.kallen.solaris.graphics.shader;

import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector3;

import java.util.List;

public class StaticShader extends Shader {

    private static final int MAX_LIGHTS = 8;

    public StaticShader(String vPath, String fPath) {
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

    public void loadLights(List<Light> lights, float ambientStrength) {
        for(int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                super.setUniform("lightPosition[" + i + "]", lights.get(i).getPosition());
                super.setUniform("lightColor[" + i + "]", lights.get(i).getColor());
            } else {
                super.setUniform("lightPosition[" + i + "]", new Vector3(0f, 0f, 0f));
                super.setUniform("lightColor[" + i + "]", new Vector3(0f, 0f, 0f));
            }
        }
        super.setUniform("ambientStrength", ambientStrength);
    }

    public void loadShine(float damper, float reflectivity) {
        super.setUniform("shine", damper);
        super.setUniform("reflectivity", reflectivity);
    }

    public void loadFogColor(Vector3 color) {
        super.setUniform("fogColor", color);
    }

}