package net.kallen.solaris.graphics.shader;

import net.kallen.solaris.graphics.Light;
import net.kallen.solaris.math.vector.Matrix4;

public class StaticShader extends Shader {

    float ambientStrength = 0.2f;

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

    public void loadLight(Light light) {
        super.setUniform("lightPosition", light.getPosition());
        super.setUniform("lightColor", light.getColor());
        super.setUniform("ambientStrength", ambientStrength);
    }

}