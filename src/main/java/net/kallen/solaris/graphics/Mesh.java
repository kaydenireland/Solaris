package main.java.net.kallen.solaris.graphics;

import main.java.net.kallen.solaris.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    private Vertex[] vertices;
    private int[] indices;
    private int vao, vbo, ibo, tbo;
    private Texture texture;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        this.texture = new Texture(ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "default").toImagePath());
    }

    public Mesh(Vertex[] vertices, int[] indices, Texture texture) {
        this.vertices = vertices;
        this.indices = indices;
        this.texture = texture;
    }

    public void create() {

        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 3] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }
        positionBuffer.put(positionData).flip();
        vbo = storeData(positionBuffer, 0, 3);
        MemoryUtil.memFree(positionBuffer);

        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            textureData[i * 2] = vertices[i].getTexturePos().getX();
            textureData[i * 2 + 1] = vertices[i].getTexturePos().getY();
        }
        textureBuffer.put(textureData).flip();
        tbo = storeData(textureBuffer, 2, 2);
        MemoryUtil.memFree(textureBuffer);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        MemoryUtil.memFree(indicesBuffer);

        GL30.glBindVertexArray(0); // Unbind VAO

        texture.create();
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(index);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public void destroy() {
        GL15.glDeleteBuffers(vbo);
        GL15.glDeleteBuffers(ibo);
        GL15.glDeleteBuffers(tbo);

        GL30.glDeleteVertexArrays(vao);
        texture.destroy();
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return vao;
    }

    public int getVBO() {
        return vbo;
    }


    public int getTBO() {
        return tbo;
    }

    public int getIBO() {
        return ibo;
    }

    public Texture getTexture() {
        return texture;
    }

}