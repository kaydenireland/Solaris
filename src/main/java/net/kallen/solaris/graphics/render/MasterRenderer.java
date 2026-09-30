package net.kallen.solaris.graphics.render;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.scene.Entity;
import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.graphics.scene.Scene;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Vector3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class MasterRenderer {

    private final Window window;
    private final Camera camera;

    private final EntityRenderer entityRenderer;

    private Vector3 skyColor = new Vector3(0.4f, 0.7f, 0.9f);
    private Vector3 fogColor = new Vector3(0.4f, 0.7f, 0.9f);

    public MasterRenderer(Window window, Camera camera, StaticShader entityShader) {
        this.window = window;
        this.camera = camera;

        this.entityRenderer = new EntityRenderer(window, camera, entityShader);
    }

    public void beginFrame() {

        // Global depth state
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glDepthMask(true);

        // Global blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Background
        GL11.glClearColor(skyColor.x, skyColor.y, skyColor.z, 1f);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void renderEntities(List<Entity> entities) {
        entityRenderer.render(entities, new ArrayList<>(), fogColor);
    }

    public void beginTransparentPass() {
        GL11.glDepthMask(false);
    }

    public void endTransparentPass() {
        GL11.glDepthMask(true);
    }

    public void endFrame() {
        window.swapBuffers();
    }

    public void setBgColor(Vector3 color) {
        this.skyColor = color;
        this.fogColor = color;
    }

    public void setAmbientStrength(float strength) {
        entityRenderer.setAmbientStrength(strength);
    }

    public EntityRenderer getEntityRenderer() {
        return entityRenderer;
    }

    public void render(Scene scene) {
        entityRenderer.render(scene.getEntities(), scene.getLights(), fogColor);
    }
}