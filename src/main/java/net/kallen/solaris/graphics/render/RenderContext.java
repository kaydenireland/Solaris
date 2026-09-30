package net.kallen.solaris.graphics.render;

import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector3;

public class RenderContext {
    private final Matrix4 projectionMatrix;
    private final Matrix4 viewMatrix;
    private final Vector3 cameraPosition;
    private final Vector3 cameraRotation;

    public RenderContext(Matrix4 projectionMatrix, Vector3 cameraPosition, Vector3 cameraRotation) {
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = Matrix4.view(cameraPosition, cameraRotation);
        this.cameraPosition = cameraPosition;
        this.cameraRotation = cameraRotation;
    }

    public Matrix4 getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4 getViewMatrix() {
        return viewMatrix;
    }

    public Vector3 getCameraPosition() {
        return cameraPosition;
    }

    public Vector3 getCameraRotation() {
        return cameraRotation;
    }
}