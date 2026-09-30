package net.kallen.solaris.graphics.render;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.mesh.Texture;
import net.kallen.solaris.graphics.scene.Entity;
import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class EntityRenderer {

    private final StaticShader shader;

    public EntityRenderer(StaticShader shader) {
        this.shader = shader;
    }

    public void create() {
        shader.create();
    }

    public void destroy() {
        shader.destroy();
    }

    public void render(RenderContext context, List<Entity> entities, List<Light> lights, Vector3 fogColor, float ambientStrength) {
        shader.bind();

        shader.loadProjectionMatrix(context.getProjectionMatrix());
        shader.loadViewMatrix(context.getViewMatrix());
        shader.loadFogColor(fogColor);
        shader.loadAmbientLightStrength(ambientStrength);

        if (!lights.isEmpty()) {
            shader.loadLights(lights);
        }

        for (Entity entity : entities) {
            renderEntity(entity);
        }

        shader.unbind();
    }


    public void renderEntity(Entity entity) {

        Matrix4 model = Matrix4.transform(
                entity.getPosition(),
                entity.getRotation(),
                entity.getScale()
        );

        renderMesh(entity.getMesh(), model);
    }

    public void renderMesh(Mesh mesh, Vector3 position) {
        renderMesh(mesh, Matrix4.translate(position));
    }

    private void renderMesh(Mesh mesh, Matrix4 model) {
        GL30.glBindVertexArray(mesh.getVAO());

        Texture texture = mesh.getTexture();

        shader.loadModelMatrix(model);
        shader.loadUseFakeLighting(texture.shouldUseFakeLighting());
        shader.loadTexture(0);
        shader.loadShine(texture.getShineDamper(), texture.getReflectivity());

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void loadLights(List<Light> lights) {
        shader.loadLights(lights);
    }

    public void setAmbientStrength(float strength) {
        shader.loadAmbientLightStrength(strength);
    }
}