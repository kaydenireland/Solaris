package net.kallen.solaris.camera;

import net.kallen.solaris.math.vector.Vector3;

public class Camera {
    protected Vector3 position;
    protected Vector3 rotation;

    public Camera(Vector3 position, Vector3 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {

    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setPosition(Vector3 position) {
        this.position.set(position.x, position.y, position.z);
    }

    public void setRotation(Vector3 rotation) {
        this.rotation.set(rotation.x, rotation.y, rotation.z);
    }
}
