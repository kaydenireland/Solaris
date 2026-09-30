package net.kallen.solaris.demo;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.camera.FreeCamera;
import net.kallen.solaris.graphics.render.MasterRenderer;
import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.scene.Entity;
import net.kallen.solaris.graphics.scene.Scene;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.io.GameLoop;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.util.file.ModelLoader;
import net.kallen.solaris.util.file.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DragonDemo {

    public static void main(String[] args) {
        Window window = new Window(1280, 780, "Solaris Test");
        Camera camera = new FreeCamera(new Vector3(0, 4, 0), new Vector3(0,0,0));

        MasterRenderer renderer = new MasterRenderer(window, camera);

        List<Light> lights = new ArrayList<>();


        lights.add(new Light(   // Sun
                new Vector3(0, 1000, -5000),
                new Vector3(0.6f, 0.6f, 0.6f)
        ));

        lights.add(new Light(
                new Vector3(20, 5, 0),
                new Vector3(1.0f, 0.0f, 0.0f),
                new Vector3(1f, 0.01f, 0.002f)
        ));

        lights.add(new Light(
                new Vector3(-20, 5, 0),
                new Vector3(0.0f, 0.0f, 1.0f),
                new Vector3(1f, 0.01f, 0.002f)
        ));

        lights.add(new Light(
                new Vector3(0, 4, -32),
                new Vector3(0.0f, 1.0f, 0.0f),
                new Vector3(1f, 0.01f, 0.002f)
        ));

        Mesh mesh = ModelLoader.loadModel(
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.MODELS, "dragon").toSystemFilePath(".obj")
        );
        mesh.getTexture().setShineDamper(8);
        mesh.getTexture().setReflectivity(3);
        Entity dragon = new Entity(
                new Vector3(0f, 0f, -12f),
                Vector3.ZERO,
                Vector3.ONE,
                mesh
        );

        Scene scene = new Scene(lights);
        scene.addEntity(dragon);


        new GameLoop(window){

            @Override
            public void create() {
                renderer.create();
                scene.create();
                window.lockCursor(true);
            }

            @Override
            public void update() {
                camera.update();
                dragon.increaseRotation(new Vector3(0f, 0.05f, 0f));
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


