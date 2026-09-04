package net.kallen.solaris.graphics.mesh;

import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;

public final class Positions {

    public static Vertex[] TRIANGLE = {
            new Vertex(new Vector3(0f,  0.5f, 0), new Vector3(0, 0, 1), new Vector2(0.5f, 1f)), // top
            new Vertex(new Vector3(-0.5f, -0.5f, 0), new Vector3(0, 0, 1), new Vector2(0f, 0f)), // bottom left
            new Vertex(new Vector3( 0.5f, -0.5f, 0), new Vector3(0, 0, 1), new Vector2(1f, 0f)), // bottom right
    };

    public static Vertex[] SQUARE = {
            new Vertex(new Vector3(-1.0f,  1.0f, 0), new Vector3(0, 0, 1), new Vector2(0, 1)), // top left
            new Vertex(new Vector3(-1.0f, -1.0f, 0), new Vector3(0, 0, 1), new Vector2(0, 0)), // bottom left
            new Vertex(new Vector3( 1.0f, -1.0f, 0), new Vector3(0, 0, 1), new Vector2(1, 0)), // bottom right
            new Vertex(new Vector3( 1.0f,  1.0f, 0), new Vector3(0, 0, 1), new Vector2(1, 1))  // top right
    };

    // Slanted face normals below are the actual cross-product-derived outward
    // normals for each triangular face (not axis-aligned guesses), so lighting
    // on these faces will be correct once the renderer starts using normals.
    public static Vertex[] TRIANGLE_PYRAMID = {
            // Bottom face (flat on XZ plane)
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(0f, -1f, 0f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0f, -1f, 0f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f, -1.0f, -1.0f), new Vector3(0f, -1f, 0f), new Vector2(0.5f, 1.0f)),

            // Front face
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(0f, 0.447214f, 0.894427f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0f, 0.447214f, 0.894427f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f,  1.0f,  0.0f), new Vector3(0f, 0.447214f, 0.894427f), new Vector2(0.5f, 1.0f)),

            // Right face
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0.872872f, 0.218218f, -0.436436f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f, -1.0f, -1.0f), new Vector3(0.872872f, 0.218218f, -0.436436f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f,  1.0f,  0.0f), new Vector3(0.872872f, 0.218218f, -0.436436f), new Vector2(0.5f, 1.0f)),

            // Left face
            new Vertex(new Vector3( 0.0f, -1.0f, -1.0f), new Vector3(-0.872872f, 0.218218f, -0.436436f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(-0.872872f, 0.218218f, -0.436436f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f,  1.0f,  0.0f), new Vector3(-0.872872f, 0.218218f, -0.436436f), new Vector2(0.5f, 1.0f))
    };

    public static Vertex[] SQUARE_PYRAMID = {
            // Bottom face
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(0f, -1f, 0f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0f, -1f, 0f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f, -1.0f), new Vector3(0f, -1f, 0f), new Vector2(1.0f, 1.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(0f, -1f, 0f), new Vector2(0.0f, 1.0f)),

            // Front face
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(0f, 0.447214f, 0.894427f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0f, 0.447214f, 0.894427f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f,  1.0f,  0.0f), new Vector3(0f, 0.447214f, 0.894427f), new Vector2(0.5f, 1.0f)),

            // Back face
            new Vertex(new Vector3( 1.0f, -1.0f, -1.0f), new Vector3(0f, 0.447214f, -0.894427f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(0f, 0.447214f, -0.894427f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f,  1.0f,  0.0f), new Vector3(0f, 0.447214f, -0.894427f), new Vector2(0.5f, 1.0f)),

            // Right face
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0.894427f, 0.447214f, 0f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f, -1.0f), new Vector3(0.894427f, 0.447214f, 0f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f,  1.0f,  0.0f), new Vector3(0.894427f, 0.447214f, 0f), new Vector2(0.5f, 1.0f)),

            // Left face
            new Vertex(new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(-0.894427f, 0.447214f, 0f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(-0.894427f, 0.447214f, 0f), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 0.0f,  1.0f,  0.0f), new Vector3(-0.894427f, 0.447214f, 0f), new Vector2(0.5f, 1.0f))
    };

    public static Vertex[] CUBE = {
            // Back face
            new Vertex(new Vector3( 1.0f,  1.0f, -1.0f), new Vector3(0, 0, -1), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f, -1.0f), new Vector3(0, 0, -1), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(0, 0, -1), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3(-1.0f,  1.0f, -1.0f), new Vector3(0, 0, -1), new Vector2(1.0f, 1.0f)),

            // Front face
            new Vertex(new Vector3(-1.0f,  1.0f,  1.0f), new Vector3(0, 0, 1), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(0, 0, 1), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0, 0, 1), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f,  1.0f,  1.0f), new Vector3(0, 0, 1), new Vector2(1.0f, 1.0f)),

            // Right face
            new Vertex(new Vector3( 1.0f,  1.0f,  1.0f), new Vector3(1, 0, 0), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(1, 0, 0), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f, -1.0f), new Vector3(1, 0, 0), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f,  1.0f, -1.0f), new Vector3(1, 0, 0), new Vector2(1.0f, 1.0f)),

            // Left face
            new Vertex(new Vector3(-1.0f,  1.0f, -1.0f), new Vector3(-1, 0, 0), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(-1, 0, 0), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(-1, 0, 0), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3(-1.0f,  1.0f,  1.0f), new Vector3(-1, 0, 0), new Vector2(1.0f, 1.0f)),

            // Top face
            new Vertex(new Vector3(-1.0f,  1.0f, -1.0f), new Vector3(0, 1, 0), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3(-1.0f,  1.0f,  1.0f), new Vector3(0, 1, 0), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f,  1.0f,  1.0f), new Vector3(0, 1, 0), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f,  1.0f, -1.0f), new Vector3(0, 1, 0), new Vector2(1.0f, 1.0f)),

            // Bottom face
            new Vertex(new Vector3(-1.0f, -1.0f,  1.0f), new Vector3(0, -1, 0), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(0, -1, 0), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f, -1.0f), new Vector3(0, -1, 0), new Vector2(1.0f, 0.0f)),
            new Vertex(new Vector3( 1.0f, -1.0f,  1.0f), new Vector3(0, -1, 0), new Vector2(1.0f, 1.0f))
    };

}