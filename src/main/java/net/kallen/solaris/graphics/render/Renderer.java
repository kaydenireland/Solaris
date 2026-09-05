package net.kallen.solaris.graphics.render;

import net.kallen.solaris.camera.Camera;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {
    private final Window window;
    private StaticShader staticShader;
    private Camera camera;

    public Renderer(Window window, StaticShader shader, Camera camera) {
        this.window = window;
        this.staticShader = shader;
        this.camera = camera;
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

        staticShader.bind();
        staticShader.loadProjectionMatrix(window.getProjectionMatrix());
    }

    public void endFrame() {
        staticShader.unbind();
        window.swapBuffers();
    }

    public void renderMesh(Mesh mesh, Vector3 position) {
        GL30.glBindVertexArray(mesh.getVAO());

        // Object uniform
        staticShader.loadModelMatrix(Matrix4.translate(position));
        staticShader.loadViewMatrix(Matrix4.view(camera.getPosition(), camera.getRotation()));

        // Bind mesh
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getTextureID());
        staticShader.loadTexture(0);

        // Draw
        GL11.glDrawElements(
                GL11.GL_TRIANGLES,
                mesh.getIndices().length,
                GL11.GL_UNSIGNED_INT,
                0
        );

        // Unbind
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
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
