package net.kallen.solaris.graphics.scene;

import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.render.MasterRenderer;
import net.kallen.solaris.terrain.Terrain;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Scene {
    private List<Entity> entities = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();
    private List<Terrain> terrains = new ArrayList<>();

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

    public void addLight(Light light) {
        lights.add(light);
    }

    public void removeLight(Light light) {
        lights.remove(light);
    }

    public List<Light> getLights() {
        return lights;
    }

    public void addTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void removeTerrain(Terrain terrain) {
        terrains.remove(terrain);
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void create() {
        Set<Mesh> createdMeshes = new LinkedHashSet<>();
        for (Entity entity : entities) {
            if (createdMeshes.add(entity.getMesh())) {
                entity.getMesh().create();
            }
        }

        for (Terrain terrain : terrains) {
            if (createdMeshes.add(terrain.getMesh())) {
                terrain.getMesh().create();
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

        for (Terrain terrain : terrains) {
            if (destroyedMeshes.add(terrain.getMesh())) {
                terrain.getMesh().destroy();
            }
        }
    }

}
