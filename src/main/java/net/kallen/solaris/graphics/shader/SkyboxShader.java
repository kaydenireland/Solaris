package net.kallen.solaris.graphics.shader;

import net.kallen.solaris.math.vector.Matrix4;

public class SkyboxShader extends Shader {

    public SkyboxShader() {
        super("skybox");
    }

    public SkyboxShader(String vPath, String fPath) {
        super(vPath, fPath);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    public void loadProjectionMatrix(Matrix4 matrix) {
        setUniform("projection", matrix);
    }

    public void loadViewMatrix(Matrix4 matrix) {
        Matrix4 skyboxView = Matrix4.copy(matrix);

        skyboxView.set(3, 0, 0f);
        skyboxView.set(3, 1, 0f);
        skyboxView.set(3, 2, 0f);

        setUniform("view", skyboxView);
    }
}
