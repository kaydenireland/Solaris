package main.java.net.kallen.solaris.graphics;


import main.java.net.kallen.solaris.math.Vector2;
import main.java.net.kallen.solaris.math.Vector3;


public class Vertex {
    private Vector3 position;
    private Vector2 texturePos;
    private Vector3 normal;

    public Vertex(Vector3 position, Vector2 texturePos) {
        this.position = position;
        this.texturePos = texturePos;
        this.normal = new Vector3(0, 0, 1);
    }

    public Vector3 getPosition() {
        return position;
    }


    public Vector2 getTexturePos() {
        return texturePos;
    }
}