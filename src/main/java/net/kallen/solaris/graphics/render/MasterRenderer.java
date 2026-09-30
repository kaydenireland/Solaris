package net.kallen.solaris.graphics.render;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.scene.Entity;
import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.graphics.scene.Scene;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.graphics.shader.TerrainShader;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Matrix4;
import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.terrain.Terrain;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class MasterRenderer {

    private final Window window;
    private final Camera camera;
    private RenderContext context;

    private final EntityRenderer entityRenderer;
    private final TerrainRenderer terrainRenderer;

    private Vector3 skyColor = new Vector3(0.4f, 0.7f, 0.9f);
    private Vector3 fogColor = new Vector3(0.4f, 0.7f, 0.9f);
    private float ambiemtLightStrength = 0.2f;

    public MasterRenderer(Window window, Camera camera) {
        this(
                window,
                camera,
                new StaticShader(),
                new TerrainShader()
        );
    }

    public MasterRenderer(Window window, Camera camera, StaticShader entityShader, TerrainShader terrainShader) {
        this.window = window;
        this.camera = camera;
        updateContext();

        this.entityRenderer = new EntityRenderer(entityShader);
        this.terrainRenderer = new TerrainRenderer(terrainShader);
    }

    private void updateContext() {
        context = new RenderContext(window.getProjectionMatrix(), camera.getPosition(), camera.getRotation());
    }

    public void create() {
        entityRenderer.create();
        terrainRenderer.create();
    }

    public void destroy() {
        entityRenderer.destroy();
        terrainRenderer.destroy();
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
        updateContext();
        entityRenderer.render(context, entities, new ArrayList<>(), fogColor, ambiemtLightStrength);
    }

    public void renderTerrain(List<Terrain> terrains) {
        updateContext();
        terrainRenderer.render(context, terrains, new ArrayList<>(), fogColor, ambiemtLightStrength);
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
        updateContext();
        terrainRenderer.render(context, scene.getTerrains(), scene.getLights(), fogColor, ambiemtLightStrength);
        entityRenderer.render(context, scene.getEntities(), scene.getLights(), fogColor, ambiemtLightStrength);
    }

    public void setAmbientLightStrength(float strength) {
        this.ambiemtLightStrength = strength;
    }
}