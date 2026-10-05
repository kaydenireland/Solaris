package net.kallen.solaris.util.file;

import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.mesh.Texture;
import net.kallen.solaris.graphics.mesh.Vertex;
import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;
import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

/**
 *  Returns a Mesh from a custom model file
 *
 *
 */
public class ModelLoader {
    public static Mesh loadModel(String modelPath, String texturePath) {
        AIScene scene = Assimp.aiImportFile(modelPath, Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate);

        if (scene == null) System.err.println("Could not load model at " + modelPath);

        // Vertices

        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        int vertexCount = mesh.mNumVertices();

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        Vertex[] vertexList = new Vertex[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            AIVector3D vertex = vertices.get(i);
            Vector3 meshVertex = new Vector3(vertex.x(), vertex.y(), vertex.z());

            AIVector3D normal = normals.get(i);
            Vector3 meshNormal = new Vector3(normal.x(), normal.y(), normal.z());

            Vector2 meshTextureCoords = new Vector2(0, 0);
            if (mesh.mNumUVComponents().get(0) != 0) {
                AIVector3D texture = mesh.mTextureCoords(0).get(i);
                meshTextureCoords.set(texture.x(), texture.y());
            }

            vertexList[i] = new Vertex(meshVertex, meshNormal, meshTextureCoords);
        }

        // Indices
        int faceCount = mesh.mNumFaces();
        AIFace.Buffer indices = mesh.mFaces();
        int[] indicesList = new int[faceCount * 3];
        for (int i = 0; i < faceCount; i++) {
            AIFace face = indices.get(i);
            indicesList[i * 3] = face.mIndices().get(0);
            indicesList[i * 3 + 1] = face.mIndices().get(1);
            indicesList[i * 3 + 2] = face.mIndices().get(2);
        }

        return new Mesh(vertexList, indicesList, new Texture(texturePath));
    }

    public static Mesh loadModel(String modelPath) {
        return loadModel(modelPath, ResourceLocation.DEFAULT_TEXTURE);
    }

    public static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    public static int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(
                index,
                size,
                GL11.GL_FLOAT,
                false,
                0,
                0
        );

        GL20.glEnableVertexAttribArray(index);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        return bufferID;
    }
}
