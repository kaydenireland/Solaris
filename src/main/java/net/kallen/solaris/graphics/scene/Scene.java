package net.kallen.solaris.graphics.scene;

import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.render.Renderer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Scene {
    private List<Entity> entities = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();

    public Scene() {

    }

    public Scene(List<Light> lights) {
        this.lights = lights;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void create() {
        Set<Mesh> createdMeshes = new LinkedHashSet<>();
        for (Entity entity : entities) {
            if (createdMeshes.add(entity.getMesh())) {
                entity.getMesh().create();
            }
        }
    }

    public void destroy() {
        Set<Mesh> destroyedMeshes = new LinkedHashSet<>();
        for (Entity entity : entities) {
            if (destroyedMeshes.add(entity.getMesh())) {
                entity.getMesh().destroy();
            }
        }
    }

    public void render(Renderer renderer) {
        if (!lights.isEmpty()) {
            renderer.loadLights(lights);
        }

        for (Entity entity : entities) {
            if (!entity.getMesh().getTexture().hasTransparency()) {
                renderer.renderEntity(entity);
            }
        }

        renderer.beginTransparentPass();
        for (Entity entity : entities) {
            if (entity.getMesh().getTexture().hasTransparency()) {
                renderer.renderEntity(entity);
            }
        }
        renderer.endTransparentPass();

    }

    public void setAmbientLightStrength(Renderer renderer, float strength) {
        renderer.setAmbientStrength(strength);
    }

}
