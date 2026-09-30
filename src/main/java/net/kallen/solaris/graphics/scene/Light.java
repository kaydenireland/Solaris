package net.kallen.solaris.graphics.scene;

import net.kallen.solaris.math.vector.Vector3;

public class Light {
    private Vector3 position;
    private Vector3 color;
    private Vector3 attenuation = new Vector3(1.0f, 0.0f, 0.0f);

    public Light(Vector3 position, Vector3 color) {
        this.position = position;
        this.color = color;
    }

    public Light(Vector3 position, Vector3 color, Vector3 attenuation) {
        this.position = position;
        this.color = color;
        this.attenuation = attenuation;
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

    public Vector3 getAttenuation() {
        return attenuation;
    }
}