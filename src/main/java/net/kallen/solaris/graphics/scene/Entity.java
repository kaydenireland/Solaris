package net.kallen.solaris.graphics.scene;

import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.math.vector.Vector3;

public class Entity {
    private Vector3 position, rotation, scale;
    private final Mesh mesh;

    public Entity(Vector3 position, Vector3 rotation, Vector3 scale, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }

    public void increasePosition(Vector3 delta) {
        this.position = position.add(delta);
    }

    public void increaseRotation(Vector3 delta) {
        this.rotation = rotation.add(delta);
    }

    public void increaseScale(Vector3 delta) {
        this.scale = scale.add(delta);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void create() {
        mesh.create();
    }

    public void destroy() {
        mesh.destroy();
    }
}