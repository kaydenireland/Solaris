package main.java.net.kallen.solaris.graphics;

import main.java.net.kallen.solaris.math.Vector2;
import main.java.net.kallen.solaris.math.Vector3;

public class Shapes {

    // Pre-built meshes
    public static Mesh TRIANGLE = new Mesh(Positions.TRIANGLE, Faces.TRIANGLE);
    public static Mesh SQUARE = new Mesh(Positions.SQUARE, Faces.RECTANGLE);
    public static Mesh CUBE = new Mesh(Positions.CUBE, Faces.CUBE);
    public static Mesh TRIANGLE_PYRAMID = new Mesh(Positions.TRIANGLE_PYRAMID, Faces.TRIANGLE_PYRAMID);
    public static Mesh SQUARE_PYRAMID = new Mesh(Positions.SQUARE_PYRAMID,   Faces.SQUARE_PYRAMID);


    // Transformations — return new Mesh with modified vertices

    /**
     * Shifts all vertices by the given offset.
     * Use this to reposition a shape's geometry itself rather than its transform matrix.
     *
     * Example: Shapes.offset(Shapes.CUBE, new Vector3(1, 0, 0))
     */
    public static Mesh offset(Mesh mesh, Vector3 offset) {
        Vertex[] original = mesh.getVertices();
        Vertex[] shifted  = new Vertex[original.length];

        for (int i = 0; i < original.length; i++) {
            Vector3 pos = original[i].getPosition();
            shifted[i] = new Vertex(
                    new Vector3(
                            pos.getX() + offset.getX(),
                            pos.getY() + offset.getY(),
                            pos.getZ() + offset.getZ()
                    ),
                    original[i].getTexturePos()
            );
        }

        return new Mesh(shifted, mesh.getIndices(), mesh.getTexture());
    }

    /**
     * Shifts all vertices by the given scalar offset.
     * Use this to reposition a shape's geometry itself rather than its transform matrix.
     *
     * Example: Shapes.offset(Shapes.CUBE, 1f)
     */
    public static Mesh offset(Mesh mesh, float offset) {
        return offset(mesh, new Vector3(offset, offset, offset));
    }

    /**
     * Scales all vertices by the given scalar vector.
     * Use this to bake a size into the geometry itself rather than the transform matrix.
     *
     * Example: Shapes.scale(Shapes.CUBE, new Vector3(2, 1, 2)) — wide flat cube
     */
    public static Mesh scale(Mesh mesh, Vector3 scalar) {
        Vertex[] original = mesh.getVertices();
        Vertex[] scaled   = new Vertex[original.length];

        for (int i = 0; i < original.length; i++) {
            Vector3 pos = original[i].getPosition();
            scaled[i] = new Vertex(
                    new Vector3(
                            pos.getX() * scalar.getX(),
                            pos.getY() * scalar.getY(),
                            pos.getZ() * scalar.getZ()
                    ),
                    original[i].getTexturePos()
            );
        }

        return new Mesh(scaled, mesh.getIndices(), mesh.getTexture());
    }

    /**
     * Scales all vertices uniformly by a single float.
     *
     * Example: Shapes.scale(Shapes.CUBE, 0.5f) — half size cube
     */
    public static Mesh scale(Mesh mesh, float scalar) {
        return scale(mesh, new Vector3(scalar, scalar, scalar));
    }

    /**
     * Applies both offset and scale in one call.
     * Scale is applied first, then offset.
     *
     * Example: Shapes.transform(Shapes.CUBE, new Vector3(1, 0, 0), new Vector3(2, 2, 2))
     */
    public static Mesh transform(Mesh mesh, Vector3 offset, Vector3 scalar) {
        return offset(scale(mesh, scalar), offset);
    }

    /**
     * Applies a texture to a shape, returning a new Mesh with that texture assigned.
     *
     * Example: Shapes.withTexture(Shapes.CUBE, grassTexture)
     */
    public static Mesh withTexture(Mesh mesh, Texture texture) {
        return new Mesh(mesh.getVertices(), mesh.getIndices(), texture);
    }
}