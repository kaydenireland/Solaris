package net.kallen.solaris.demo;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.camera.FreeCamera;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.render.MasterRenderer;
import net.kallen.solaris.graphics.scene.Entity;
import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.graphics.scene.Scene;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.graphics.shader.TerrainShader;
import net.kallen.solaris.io.GameLoop;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.terrain.Terrain;
import net.kallen.solaris.util.file.ModelLoader;
import net.kallen.solaris.util.file.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TerrainDemo {

    public static void main(String[] args) {
        Window window = new Window(1280, 780, "Solaris Test");
        Camera camera = new FreeCamera(new Vector3(0, 4, 0), new Vector3(0,0,0));

        MasterRenderer renderer = new MasterRenderer(window, camera);

        List<Light> lights = new ArrayList<>();


        lights.add(new Light(   // Sun
                new Vector3(0, 1000, -5000),
                new Vector3(0.9f, 0.9f, 0.9f)
        ));

        lights.add(new Light(
                new Vector3(0, 5, 20),
                new Vector3(0.75f, 0.35f, 0.45f),
                new Vector3(1f, 0.01f, 0.002f)
        ));

        Mesh mesh = ModelLoader.loadModel(
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.MODELS, "bunny").toSystemFilePath(".obj")
        );
        mesh.getTexture().setShineDamper(8);
        mesh.getTexture().setReflectivity(3);
        Entity bunny = new Entity(
                new Vector3(0f, 0f, -12f),
                Vector3.ZERO,
                Vector3.ONE,
                mesh
        );

        Light light = new Light(
                Vector3.ZERO,
                new Vector3(0.7f, 0.7f, 0.0f),
                new Vector3(1f, 0.01f, 0.002f)
        );

        Terrain terrain = new Terrain(
                0, 0,
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "grass").toImagePath()
        );

        Terrain terrain2 = new Terrain(
                1, 0,
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "grass").toImagePath()
        );

        Scene scene = new Scene(lights);
        scene.addEntity(bunny);
        scene.addLight(light);
        scene.addTerrain(terrain);
        scene.addTerrain(terrain2);


        for (int i = 0; i < 800; i++) {
            Mesh treeMesh = ModelLoader.loadModel(
                    ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.MODELS, "tree").toSystemFilePath(".obj"),
                    ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "tree").toImagePath()
            );
            treeMesh.getTexture().setShineDamper(8f);
            treeMesh.getTexture().setReflectivity(0.5f);
            float scale = 1 + (float) (3 * Math.random());
            Entity tree = new Entity(
                    new Vector3((400 * (float) Math.random()) - 200, 0f, -200 * (float) Math.random()),
                    Vector3.ZERO,
                    new Vector3(scale, scale, scale),
                    treeMesh
            );
            scene.addEntity(tree);
        }


        new GameLoop(window){

            @Override
            public void create() {
                renderer.create();
                scene.create();
                window.lockCursor(true);
                renderer.setAmbientStrength(1f);
            }

            @Override
            public void update() {
                camera.update();
            }

            @Override
            public void render() {
                renderer.beginFrame();
                renderer.render(scene);
                renderer.endFrame();
            }

            @Override
            public void close() {
                scene.destroy();
                renderer.destroy();
            }

        }.start();
    }
}


