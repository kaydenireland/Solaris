package main.java.net.kallen.solaris.graphics;

import main.java.net.kallen.solaris.io.Window;
import main.java.net.kallen.solaris.math.vector.Matrix4;
import main.java.net.kallen.solaris.math.vector.Vector3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {
    private final Window window;
    private final Shader shader;

    public Renderer(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;
    }

    public void beginFrame() {

        // Global State
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glDepthMask(true);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glFrontFace(GL11.GL_CCW);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        shader.bind();
        shader.setUniform("projection", window.getProjectionMatrix());
    }

    public void endFrame() {
        shader.unbind();
        window.swapBuffers();
    }

    public void renderMesh(Mesh mesh, Vector3 position) {
        GL30.glBindVertexArray(mesh.getVAO());

        GL30.glEnableVertexAttribArray(0);
        // GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);

        // Object uniform
        shader.setUniform("model", Matrix4.translate(new Vector3(0, 0, -2)));
        shader.setUniform("view", Matrix4.identity());

        // Bind mesh
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getTextureID());
        shader.setUniform("tex", 0);

        // Draw
        GL11.glDrawElements(
                GL11.GL_TRIANGLES,
                mesh.getIndices().length,
                GL11.GL_UNSIGNED_INT,
                0
        );

        // Unbind
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        // GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void renderMeshWithTransparency(Mesh mesh, Vector3 position) {
        GL11.glDepthMask(false);
        renderMesh(mesh, position);
        GL11.glDepthMask(true);
    }

    public void renderMesh(Mesh mesh) {
        renderMesh(mesh, Vector3.ZERO);
    }

}
