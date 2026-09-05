package net.kallen.solaris.graphics.scene;

import net.kallen.solaris.math.vector.Vector3;

public class Light {
    private Vector3 position;
    private Vector3 color;

    public Light(Vector3 position, Vector3 color) {
        this.position = position;
        this.color = color;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setColor(Vector3 color) {
        this.color = color;
    }

    public Vector3 getPosition() {
        return this.position;
    }

    public Vector3 getColor() {
        return color;
    }
}