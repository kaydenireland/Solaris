package net.kallen.solaris.graphics;


import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;


public class Vertex {
    private Vector3 position;
    private Vector3 normal;
    private Vector2 texturePos;

    public Vertex(Vector3 position, Vector2 texturePos) {
        this.position = position;
        this.normal = new Vector3(0, 0, 1);
        this.texturePos = texturePos;
    }
    public Vertex(Vector3 position, Vector3 normal, Vector2 texturePos) {
        this.position = position;
        this.normal = normal;
        this.texturePos = texturePos;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public Vector2 getTexturePos() {
        return texturePos;
    }
}