package net.kallen.solaris.graphics.scene;

import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.render.Renderer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Scene {
    private List<Entity> entities = new ArrayList<>();
    private Light light;

    float ambientLightStrength = 0.1f;

    public Scene() {

    }

    public Scene(Light light) {
        this.light = light;
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
        if (light != null) {
            renderer.loadLight(light, ambientLightStrength);
        }

        for (Entity entity : entities) {
            renderer.renderEntity(entity);
        }
    }

    public void setAmbientLightStrength(float strength) {
        this.ambientLightStrength = strength;
    }

}
