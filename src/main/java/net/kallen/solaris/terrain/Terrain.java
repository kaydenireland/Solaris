package net.kallen.solaris.terrain;

import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.mesh.Texture;
import net.kallen.solaris.graphics.mesh.Vertex;
import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;

public class Terrain {
    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;

    private float x;
    private float z;
    private Mesh mesh;

    public Terrain(int gridX, int gridZ, String texturePath) {
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.mesh = generateTerrain(texturePath);
    }

    private Mesh generateTerrain(String texturePath) {
        int count = VERTEX_COUNT * VERTEX_COUNT;

        Vertex[] vertices = new Vertex[count];

        int vertexPointer = 0;

        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {

                float x = -(float) j / (VERTEX_COUNT - 1) * SIZE;
                float y = 0;
                float z = -(float) i / (VERTEX_COUNT - 1) * SIZE;

                Vector3 position = new Vector3(x, y, z);
                Vector3 normal = new Vector3(0, 1, 0);

                Vector2 textureCoords = new Vector2(
                        (float) j / (VERTEX_COUNT - 1),
                        (float) i / (VERTEX_COUNT - 1)
                );

                vertices[vertexPointer++] =
                        new Vertex(position, normal, textureCoords);
            }
        }

        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];

        int pointer = 0;

        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {

                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;

                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;

                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;

                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }

        Texture texture = new Texture(texturePath, true);

        return new Mesh(vertices, indices, texture);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
