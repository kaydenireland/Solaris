package net.kallen.solaris.graphics.render;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.mesh.Texture;
import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.graphics.shader.TerrainShader;
import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.terrain.Terrain;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class TerrainRenderer {
    private final Camera camera;
    private final TerrainShader shader;

    public TerrainRenderer(Camera camera, TerrainShader shader) {
        this.camera = camera;
        this.shader = shader;
    }

    public void create() {
        shader.create();
    }

    public void destroy() {
        shader.destroy();
    }

    public void render(List<Terrain> terrains, List<Light> lights, Matrix4 projection, Vector3 fogColor, float ambientStrength) {
        shader.bind();
        shader.loadProjectionMatrix(projection);
        shader.loadViewMatrix(Matrix4.view(camera.getPosition(), camera.getRotation()));

        shader.loadFogColor(fogColor);
        shader.loadAmbientLightStrength(ambientStrength);

        if (!lights.isEmpty()) {
            shader.loadLights(lights);
        }

        for (Terrain t : terrains) {
            Matrix4 model = Matrix4.transform(
                    new Vector3(t.getX(), 0, t.getZ()),
                    Vector3.ZERO,
                    Vector3.ONE
            );
            renderTerrain(t.getMesh(), model);
        }

        shader.unbind();
    }

    private void renderTerrain(Mesh mesh, Matrix4 model) {
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
}
